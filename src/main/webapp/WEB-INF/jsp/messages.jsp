<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
</style>
<div class="content-body" data-page-name="Messages">

    <div class="row">
        <div class="col s12">
            <ul id="tabs" class="tabs">
                <li class="tab col s3"><a class="active" href="#all-orders-activate">Activate</a></li>
                <li class="tab col s3"><a href="#all-orders-pause">Request to pause</a></li>
                <li class="tab col s3"><a href="#all-orders-resume">Request to resume</a></li>
                <li class="tab col s3"><a href="#all-orders-disable">Request to disable</a></li>
            </ul>
        </div>
    </div>
    <div id="all-orders-activate" class="col s12">
        <ul id="activate" class="collection with-header">
        </ul>
    </div>
    <div id="all-orders-pause" class="col s12">
        <ul id="pause" class="collection with-header">
        </ul>
    </div>
    <div id="all-orders-resume" class="col s12">
        <ul id="resume" class="collection with-header">
        </ul>
    </div>
    <div id="all-orders-disable" class="col s12">
        <ul id="disable" class="collection with-header">
        </ul>
    </div>
</div>


<input type="hidden" id="csrfToken" value="${_csrf.token}"/>
<input type="hidden" id="csrfHeader" value="${_csrf.headerName}"/>
</div>
<script>

    $('ul#tabs').tabs({
        onShow: function (tab) {
        }
    });
    setInterval(function () {
        fetch();
    }, 150000);
    fetch();
    function fetch() {
        fetchMessages('activate');
        fetchMessages('pause');
        fetchMessages('resume');
        fetchMessages('disable');
    }

    function fetchMessages(url) {
        $.get("/messages/" + url).success(function (data) {
            var ul = $('#' + url);
            ul.children().remove();
            var header = '';
            if (url != 'activate') {
                header += 'Request to ';
            }
            header += url;
            ul.append('<li class="collection-header"><h4>' + header.toUpperCase() + '</h4></li>');
            $.each(data, function (i, item) {
                var div = $("<div>").addClass("row");

                var button_to_order = $('<a class="waves-effect waves-light btn right" href="#order/' + item.id + '">Move to order</a>');
                var button = $('<a class="waves-effect waves-light btn right green" id="' + getStatus(item.status) + item.id + '" >' + getStatus(item.status) + '</a>');
                button.click(function () {
                    submitOrder(item.id, item.status);
                });
                var li = $("<li>").addClass("collection-item");
                if (url == 'activate') {
                    div.append(item.title + ', status - ' + item.status + ', prefered date for activate ' + item.date);
                } else {
                    div.append(item.title + ', status - ' + item.status);
                }

                div.append(button);
                div.append(button_to_order);
                li.append(div);
                ul.append(li);
            })
        })
    }

    function submitOrder(id, status) {
        var url = '/orders/' + id + '/' + getStatus(status);
        sendPut(id, url, getStatus(status));
    }

    function getStatus(status) {
        if (status == 'PROCESSING') {
            return 'activate';
        } else if (status == 'REQUEST_TO_RESUME') {
            return 'resume';
        } else if (status == 'REQUEST_TO_PAUSE') {
            return 'pause';
        } else if (status == 'REQUEST_TO_DISABLE') {
            return 'disable';
        }
    }

    function sendPut(id, url, status) {
        var token = $('#csrfToken').val();
        var header = $('#csrfHeader').val();

        var xhr = $.ajax({
            type: 'PUT',
            url: url,
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            statusCode: {
                200: function (data) {
                    Materialize.toast(xhr.getResponseHeader("successMessage"), 5000);
                    console.log('#' + status + id);
                    $('#' + status + id).remove();
                }
            }
        });
        return xhr;
    }

</script>
