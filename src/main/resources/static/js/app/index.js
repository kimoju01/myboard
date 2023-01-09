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

        $('#btn-user-join').on('click', function () {
            _this.join();
        });

        $('#email').on('keyup', function () {
            _this.checkEmail();
        });

        $('#file').on('change', function () {
            _this.changeFile();
        })

    },

    save: function () {
        var data = {
                title: $('#title').val(),
                content: $('.summernote').summernote('code')
        };
        
        // file과 JSON을 같이 보내기 위해 FormData 안에 두 가지를 포함 시킴
        var file = $('#file')[0].files[0];
        var formData = new FormData();
        formData.append('file', file);
        formData.append("boardInfo", new Blob([JSON.stringify(data)],
            {type: "application/json"}));

        if (data.title == "" || data.content == "") {
            alert("정보를 모두 입력해주세요.");
            return;
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            // dataType: 'json',
            // contentType: 'application/json; charset=utf-8',
            contentType: false,
            processData: false,
            // data: JSON.stringify(data)
            data: formData,
            enctype: 'multipart/form-data'
        }).done(function () {
            alert("글이 등록되었습니다.");
            window.location.href = '/posts';
        }).fail(function (request, status, error) {
            if (request.status === 403 || request.status === 405) {
                alert("접근 권한이 없습니다.");
            } else {
                alert(JSON.stringify(error));
            }
        // }).fail(function (error) {
        //     alert(JSON.stringify(error));
        });
    },

    update: function () {
        var data = {
            title: $('#title').val(),
            email: $('#email').val(),
            content: $('.summernote').summernote('code')
        };
        if (data.title == "" || data.content == "") {
            alert("정보를 모두 입력해주세요.");
            return;
        };

        var oldThumbFileName = $('#oldThumbFileName').val();

        // file과 JSON을 같이 보내기 위해 FormData 안에 두 가지를 포함 시킴
        var file = $('#file')[0].files[0];
        var formData = new FormData();
        formData.append('file', file);
        formData.append('oldThumbFileName', oldThumbFileName);
        formData.append("boardInfo", new Blob([JSON.stringify(data)],
            {type: "application/json"}));

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            contentType: false,
            processData: false,
            data: formData,
            enctype: 'multipart/form-data'
        }).done(function () {
            alert("글이 수정되었습니다.");
            window.location.href = '/posts/' + id;
        }).fail(function (request, status, error) {
            if(request.status === 403 || request.status === 405) {
                alert("접근 권한이 없습니다.");
            } else {
                alert(JSON.stringify(error));
            }
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
        }).fail(function (request, status, error) {
            if (request.status === 403 || request.status === 405) {
                alert("접근 권한이 없습니다.");
            } else {
                alert(JSON.stringify(error));
            }
        });
    },

    join: function () {
        var data = {
            name: $('#name').val(),
            email: $('#email').val(),
            password: $('#password').val()
        };

        if (data.name == "" || data.email == "" ||  data.password == "") {
            alert("정보를 모두 입력해주세요.");
            return;
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/members',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert("회원 가입이 완료되었습니다.");
            window.location.href = '/loginPage';
        }).fail(function (request, status, error) {
            if (request.status === 403 || request.status === 405) {
                alert("접근 권한이 없습니다.");
            } else {
                alert(JSON.stringify(error));
            }
        });
    },

    checkEmail: function () {
        var email = $('#email').val();
        var emailExp = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
        var verifyEmail = emailExp.test(email);

        $.ajax({
            type: 'POST',
            url: '/api/v1/members/duplicate',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: email
        }).done(function (data) {
            if (data === true || !verifyEmail) {
                $('#email.form-control').css('border-color', 'red');
                $('#btn-user-join').attr('disabled', 'disabled');
            } else if (data === false && verifyEmail) {
                $('#email.form-control').css('border-color', 'green');
                $('#btn-user-join').removeAttr('disabled');
            }
        }).fail(function (request, status, error) {
            if (request.status === 403 || request.status === 405) {
                alert("접근 권한이 없습니다.");
            } else {
                alert(JSON.stringify(error));
            }
        });

    },

    changeFile: function () {
        // var fileName = $('#file').val();
        var fileName = $('#file')[0].files[0].name;
        $('#file-name').val(fileName);
    }

};

main.init();