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
        }).fail(function (error, textStatus) {
            // alert(JSON.stringify(error));
            console.log(error);
            console.log(textStatus);
        });
    }

};

main.init();