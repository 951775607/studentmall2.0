$(function () {
    $('#log-out').click(function () {

        $.ajax({
            url: "/student/local/logout",
            type: "post",
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    window.location.href = '/student/local/login?usertype=1';
                }
            },
            error: function (data, error) {
                alert(error);
            }
        });
    });

});
