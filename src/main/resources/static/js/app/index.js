var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-member-join').on('click', function () {
            _this.join();
        });

        // $('#btn-member-login').on('click', function () {
        //     _this.login();
        // });
    },

    save: function () {
        var data = {
            title: $('#title').val(),
            writer: $('#writer').val(),
            content: $('#content').val()
        };
        if (data.title == "" || data.writer == "" || data.content == "") {
            alert("정보를 모두 입력해주세요.");
            return;
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert("글이 등록되었습니다.");
            window.location.href = '/posts';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        if (data.title == "" || data.content == "") {
            alert("정보를 모두 입력해주세요.");
            return;
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert("글이 수정되었습니다.");
            window.location.href = '/posts/' + id;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    delete: function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert("글이 삭제되었습니다.");
            window.location.href = '/posts';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    join: function () {
        var userdata = {
            name: $('#name').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };

        if (userdata.name == "" || userdata.email == "" || userdata.password == "") {
            alert("정보를 모두 입력해주세요.");
            return;
        }
        ;

        $.ajax({
            type: 'POST',
            url: '/api/v1/members/new',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(userdata)
        }).done(function () {
            alert("회원 가입이 완료되었습니다.");
            window.location.href = '/login';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

    // },

    // login: function () {
    //     var userdata = {
    //         email: $('#email').val(),
    //         password: $('#password').val()
    //     };
    //
    //     if (userdata.email == "") {
    //         alert("이메일을 입력해주세요.");
    //         return;
    //     } else if (userdata.password == "") {
    //         alert("비밀번호를 입력해주세요.");
    //         return;
    //     };
    //
    //     $.ajax({
    //         type: 'POST',
    //         url: '/api/v1/members/login',
    //         dataType: 'json',
    //         contentType: 'application/json; charset=utf-8',
    //         data: JSON.stringify(userdata)
    //     }).done(function () {
    //         alert(userdata.email + "님 반갑습니다!");
    //         window.location.href = '/';
    //     }).fail(function (error) {
    //         alert(JSON.stringify(error));
    //     });
    // }

};

main.init();