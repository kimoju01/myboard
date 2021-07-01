package com.hyeju.study.myboard.config.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyeju.study.myboard.domain.member.repository.MemberRepository;
import com.hyeju.study.myboard.dto.MemberDto;
import com.hyeju.study.myboard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BCryptPasswordEncoder encoder;

    /* 카카오 액세스 토큰 받기 */
    /* 필수 파라미터 값들을 담아 POST로 요청 => 응답은 JSON 객체로 Redirect URI에 전달된다. */
    public String getAccessToken(String code) {
        // Post 방식으로 key=value 데이터를 카카오에게 요청 => RestTemplate 라이브러리 사용
        RestTemplate rt = new RestTemplate();
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        // 내가 지금 전송할 HTTP Body 데이터가 key=value 형태라고 알려준다
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성 후 Body 데이터를 담는다
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "192ae19481f623fcfb044a05f17176cb");
        params.add("redirect_uri", "http://localhost:8080/oauth/kakao/callback");
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담는다
        HttpEntity<MultiValueMap<String, String>> kakaoAccessTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청
        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoAccessTokenRequest,
                String.class
        );  // => JSON 형식으로 응답온다

        // JSON 형태로 온 응답을 자바 객체로 바꿔주기
        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoOAuthToken kakaoOAuthToken;
        KakaoOAuthToken kakaoOAuthToken = null;

        try {
            kakaoOAuthToken = objectMapper.readValue(responseEntity.getBody(), KakaoOAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoOAuthToken.getAccess_token();
    }


    /* 카카오 사용자 정보 가져와서 KakaoUserInfo 클래스에 저장하기 */
    public KakaoUserInfo getKakaoUserInfo(String access_token) {

        /* 카카오 사용자 정보 가져오기 */
        /* 사용자 액세스 토큰을 헤더에 담아 GET 또는 POST로 요청 => 응답 바디는 JSON 객체 */
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", " Bearer " + access_token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        /* 카카오 사용자 정보 저장하기*/
        /* JSON 형식으로 온 응답 Body를자바 객체로 변환해서 저장하기 */
        ObjectMapper objectMapper = new ObjectMapper();
//        KakaoUserInfo userInfo;
        KakaoUserInfo kakaoUserInfo = null;

        try {
            kakaoUserInfo = objectMapper.readValue(responseEntity.getBody(), KakaoUserInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoUserInfo;

    }


    /* 카카오 유저 회원가입 진행하기 */
    /* 비밀번호는 받아올 수 없어서 임의의 값 지정해줌 */
    @Transactional
    public void saveKakaoUser(KakaoUserInfo kakaoUserInfo) {
        // UUID => 랜덤 32자 숫자 & 영어소문자 36자리 생성
        String dummyPassword = UUID.randomUUID().toString();
        String encodePassword = encoder.encode(dummyPassword);

        MemberDto memberDto = MemberDto.builder()
                .name(kakaoUserInfo.getProperties().getNickname())
                .email("kakao_" + kakaoUserInfo.getKakao_account().getEmail())
                .password(encodePassword)
                .build();

        System.out.println(memberDto.getEmail());
        System.out.println(memberDto.getName());

        boolean duplicateCheck = memberRepository.existsMemberEntityByEmail(memberDto.getEmail());
        System.out.println(duplicateCheck);

        if (!duplicateCheck) {  // 가입하지 않은 회원이면 가입 먼저 시키고 -> 로그인 처리
            memberRepository.save(memberDto.toEntity());
            System.out.println("회원 가입 완료");
        }

        // 이미 가입한 회원이면 바로 로그인 처리할 수 있도록
        System.out.println("로그인 처리 시작");
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(memberDto.getEmail(), memberDto.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("로그인 처리 완료");

    }

}
