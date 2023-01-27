package com.hyeju.study.myboard.config.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyeju.study.myboard.domain.member.Role;
import com.hyeju.study.myboard.domain.member.repository.MemberRepository;
import com.hyeju.study.myboard.dto.MemberDto;
import com.hyeju.study.myboard.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.properties")
public class KakaoOAuthService {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;

    /* 카카오 아이디 비밀번호 설정에 사용할 더미 패스워드 */
    @Value("${dummyPassword}")
    private String dummyPassword;

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
//        params.add("redirect_uri", "http://localhost:8080/oauth/kakao/callback");
        params.add("redirect_uri", "http://ec2-3-39-143-158.ap-northeast-2.compute.amazonaws.com:8080/oauth/kakao/callback");
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
//        ObjectMapper objectMapper = new ObjectMapper();
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
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


    /* 카카오 유저 회원가입 + 로그인 처리 진행하기 */
    @Transactional
    public void saveKakaoUser(KakaoUserInfo kakaoUserInfo) {
        /* 비밀번호는 카카오 유저 정보로 받아올 수 없어서 임의의 값 지정해줌 */
        // @Value로 dummyPassword 프로퍼티 파일에 지정해놓음! UUID 로는 자꾸 값이 바껴서 로그인 처리가 안 됨..
        // UUID => '-' 기호 포함한 랜덤 32자 숫자 & 영어소문자 36자리 생성
//        String dummyPassword = UUID.randomUUID().toString().replaceAll("-", "");
//        dummyPassword = dummyPassword.substring(0, 10); // - 기호는 없애고 10글자 짜리로 만듦

        MemberDto memberDto = MemberDto.builder()
                .name(kakaoUserInfo.getProperties().getNickname())
                .email("kakao_" + kakaoUserInfo.getKakao_account().getEmail())
                .password(dummyPassword)    //@Value
                .build();

        /* 가입하지 않은 회원이면 가입 먼저 시키고  로그인 처리 할 수 있도록 중복 체크 */
        boolean duplicateCheck = memberService.isDuplicateEmail(memberDto.getEmail());
        if (!duplicateCheck) {
            memberService.save(memberDto);
            System.out.println("회원 가입 완료");
        }

        /* 이미 가입한 회원이면 바로 로그인 처리할 수 있도록 */
        System.out.println("로그인 처리 시작");
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(memberDto.getEmail(), dummyPassword));    // 암호화 시킨 패스워드는 접근 못 해서 plain 패스워드로!!
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("로그인 처리 완료");
        } catch (DisabledException | LockedException | BadCredentialsException e) {
            String status;
            if (e.getClass().equals(BadCredentialsException.class)) {
                status = "invalid-password";
            } else if (e.getClass().equals(DisabledException.class)) {
                status = "locked";
            } else if (e.getClass().equals(LockedException.class)) {
                status = "disable";
            } else {
                status = "unknown";
            }
            System.out.println(status);
        }
    }
}
