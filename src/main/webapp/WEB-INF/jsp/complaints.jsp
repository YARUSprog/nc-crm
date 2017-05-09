<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Complaints">
    <div class="col s12">
        <ul class="tabs" id="tabs">
            <sec:authorize access="hasAnyRole('ROLE_PMG')">
                <li class="tab col s3"><a href="#swipe-all-complaints">All complaints</a></li>
                <li class="tab col s3"><a href="#swipe-own-complaints">Your complaints</a></li>
            </sec:authorize>
            <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
                <li class="tab col s3"><a href="#swipe-create-complaint-form">Create</a></li>
                <li class="tab col s3"><a href="#swipe-all-customer-complaints">My complaints</a></li>
            </sec:authorize>
        </ul>
    </div>
    <sec:authorize access="hasAnyRole('ROLE_PMG')">
        <div id="swipe-all-complaints" class="col s12">
            <div id="table-all-complaints" class="table-container row">
                <div class="table-wrapper col s11 center-align">
                    <table class="striped responsive-table centered ">
                        <thead>
                        <tr>
                            <th data-field="1">
                                <a href="#!" class="sorted-element a-dummy">#</a>
                            </th>
                            <th data-field="2">
                                <a href="#!" class="sorted-element a-dummy">Title</a>
                            </th>
                            <th class="th-dropdown" data-field="statusId">
                                <a class='dropdown-button dropdown-status-button a-dummy' href='#'
                                   data-activates='dropdown-status' data-default-name="Status">
                                    Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-status" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="1">OPEN</a></li>
                                    <li><a href="#" class="a-dummy" data-value="2">SOLVING</a></li>
                                    <li><a href="#" class="a-dummy" data-value="3">CLOSED</a></li>
                                </ul>
                            </th>
                            <th data-field="3">
                                <a href="#!" class="sorted-element a-dummy">Customer</a>
                            </th>
                            <th data-field="4">
                                <a href="#!" class="sorted-element a-dummy">Order</a>
                            </th>
                            <th class="th-dropdown" data-field="orderStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-order-status' data-default-name="Order Status">
                                    Order Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-order-status" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                                    <li><a href="#" class="a-dummy" data-value="5">In queue</a></li>
                                    <li><a href="#" class="a-dummy" data-value="6">Processing</a></li>
                                    <li><a href="#" class="a-dummy" data-value="7">Active</a></li>
                                    <li><a href="#" class="a-dummy" data-value="8">Disabled</a></li>
                                    <li><a href="#" class="a-dummy" data-value="9">Paused</a></li>
                                </ul>
                            </th>
                            <th data-field="5">
                                <a href="#!" class="sorted-element a-dummy">Product</a>
                            </th>
                            <th class="th-dropdown" data-field="productStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-product-status' data-default-name="Product Status">
                                    Product Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-product-status" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="10">Planned</a></li>
                                    <li><a href="#" class="a-dummy" data-value="11">Actual</a></li>
                                    <li><a href="#" class="a-dummy" data-value="12">Outdated</a></li>
                                </ul>
                            </th>
                            <th data-field="6">
                                <a href="#!" class="sorted-element a-dummy">PMG</a>
                            </th>
                            <th data-field="7">
                                <a href="#!" class="sorted-element a-dummy">Date</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_PMG')">
        <div id="swipe-own-complaints" class="col s12">
            <div id="table-your-complaints" class="table-container row">
                <div class="table-wrapper col s11 center-align">
                    <table class="striped responsive-table centered ">
                        <thead>
                        <tr>
                            <th data-field="1">
                                <a href="#!" class="sorted-element a-dummy">#</a>
                            </th>
                            <th data-field="2">
                                <a href="#!" class="sorted-element a-dummy">Title</a>
                            </th>
                            <th class="th-dropdown" data-field="statusId">
                                <a class='dropdown-button dropdown-status-button a-dummy' href='#'
                                   data-activates='dropdown-status-own' data-default-name="Status">
                                    Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-status-own" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="1">OPEN</a></li>
                                    <li><a href="#" class="a-dummy" data-value="2">SOLVING</a></li>
                                    <li><a href="#" class="a-dummy" data-value="3">CLOSED</a></li>
                                </ul>
                            </th>
                            <th data-field="3">
                                <a href="#!" class="sorted-element a-dummy">Customer</a>
                            </th>
                            <th data-field="4">
                                <a href="#!" class="sorted-element a-dummy">Order</a>
                            </th>
                            <th class="th-dropdown" data-field="orderStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-order-status-own' data-default-name="Order Status">
                                    Order Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-order-status-own" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="4">New</a></li>
                                    <li><a href="#" class="a-dummy" data-value="5">In queue</a></li>
                                    <li><a href="#" class="a-dummy" data-value="6">Processing</a></li>
                                    <li><a href="#" class="a-dummy" data-value="7">Active</a></li>
                                    <li><a href="#" class="a-dummy" data-value="8">Disabled</a></li>
                                    <li><a href="#" class="a-dummy" data-value="9">Paused</a></li>
                                </ul>
                            </th>
                            <th data-field="5">
                                <a href="#!" class="sorted-element a-dummy">Product</a>
                            </th>
                            <th class="th-dropdown" data-field="productStatusId">
                                <a class='dropdown-button a-dummy' href='#'
                                   data-activates='dropdown-product-status-own' data-default-name="Product Status">
                                    Product Status
                                </a>
                                <span class="deleter"><a href="#" class="a-dummy">&#215;</a></span>
                                <ul id="dropdown-product-status-own" class='dropdown-content'>
                                    <li><a href="#" class="a-dummy" data-value="10">Planned</a></li>
                                    <li><a href="#" class="a-dummy" data-value="11">Actual</a></li>
                                    <li><a href="#" class="a-dummy" data-value="12">Outdated</a></li>
                                </ul>
                            </th>
                            <th data-field="7">
                                <a href="#!" class="sorted-element a-dummy">Date</a>
                            </th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
        <div id="swipe-all-customer-complaints" class="col s12">
            <table class="striped" id="historyTable" style="width: calc(100% - 20px * 2); margin: 20px;">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Order</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Message</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="i" items="${complaints}">
                    <tr>
                        <td>${i.id}</td>
                        <td>${i.title}</td>
                        <td>${i.order.id} ${i.order.product.title} </td>
                        <td>${i.date.toLocalDate()} ${i.date.toLocalTime().getHour()}:${i.date.toLocalTime().getMinute()} </td>
                        <td>${i.status}</td>
                        <td>
                                <%--<a class="waves-effect waves-light btn" href="#modal${i.id}">Show</a>--%>
                            <button data-target="modal${i.id}" class="btn">show</button>
                            <div id="modal${i.id}" class="modal">
                                <div class="modal-content">
                                    <h4>${i.title}</h4>
                                    <p>${i.message.replaceAll("\\n", "<br>")}</p>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_CUSTOMER')">
        <div id="swipe-create-complaint-form" class="col s12">
            <div class="row">
                <form id="createComplaintForm" class="col s12">
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">title</i>
                            <input id="title" type="text" class="validate" name="title" maxlength="50">
                            <label for="title">Title</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">view_carousel</i>
                            <select id="orderSelect" name="orderId" size="5">
                                <option value="" disabled selected>Choose order</option>
                                <c:forEach items="${orders}" var="i">
                                    <option value="${i.id}">${i.id} ${i.product.title} </option>
                                </c:forEach>
                            </select>
                            <label>Order Select</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">mode_edit</i>
                            <textarea name="message" id="message" class="materialize-textarea"
                                      maxlength="400"></textarea>
                            <label for="message">Description</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" name="action" id="createButton">
                                Create Copmplaint
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </sec:authorize>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>
    $(document).ready(function () {
        $('ul.tabs').tabs();
        $('select').material_select();
        $('.modal').modal();

        $("#createComplaintForm").on("submit", function (e) {
                e.preventDefault();
                var titleLenth = $("#title").val().length;
                var messageLenth = $("#message").val().length;
                var selectVal = $("#orderSelect").val();
                if (titleLenth < 5 || titleLenth > 50) {
                    Materialize.toast("Title length must be 5-50", 5000, 'rounded');
                } else if (messageLenth < 5 || messageLenth > 400) {
                    Materialize.toast("Description length must be 5-400", 5000, 'rounded');
                } else if (!selectVal > 0) {
                    Materialize.toast("Order can't be empty", 5000, 'rounded');
                } else {
                    $('ul.tabs').tabs('select_tab', 'swipe-all-customer-complaints');
                    $(".progress").addClass("progress-active");
                    $.post("/customer/createComplaint", $("#createComplaintForm").serialize(), function (data) {
                        $("#createComplaintForm")[0].reset();
                        var monthValue = checkLength(data.date.monthValue);
                        var dayOfMonth = checkLength(data.date.dayOfMonth);
                        var hour = data.date.hour;
                        var minute = data.date.minute;

                        var row = $("<tr>");
                        row.append($("<td>", {text: data.id}));
                        row.append($("<td>", {text: data.title}));
                        row.append($("<td>", {text: data.order.id + " " + data.order.product.title}));
                        row.append($("<td>", {
                            text: data.date.year + "-" +
                            monthValue + "-" +
                            dayOfMonth + " " +
                            hour + ":" +
                            minute
                        }))
                        row.append($("<td>", {text: data.status}));
                        var td = $("<td>");
                        td.html(' <button data-target="modal' + data.id + '" class="btn">show</button>' +
                            '<div id="modal' + data.id + '" class="modal"> ' +
                            '<div class="modal-content"> ' +
                            '<h4>' + data.title + '</h4> ' +
                            '<p>' + data.message.replace(/\n/g, '<br>') + '</p> ' +
                            ' </div> ' +
                            '  </div> ');
                        row.append(td);

                        $(".content-body tbody").prepend(row);

                        $('.modal').modal();

                        $(".progress").removeClass("progress-active");
                        Materialize.toast("Complaint with id " + data.id + " successfuly created", 5000, 'rounded');
                    });
                }
            }
        );
    })

    $("#table-all-complaints").karpo_table({
        urlSearch: "/pmg/load/complaintsNames",
        urlTable: "/pmg/load/complaints",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>", {text: object.id}));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.customer}));
            tr.append($("<td>", {text: object.order}));
            tr.append($("<td>", {text: object.orderStatus}));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.productStatus}));
            tr.append($("<td>", {text: object.pmg}));
            tr.append($("<td>", {text: object.date}));
            return tr;
        }
    });

    $("#table-your-complaints").karpo_table({
        urlSearch: "/pmg/load/ownComplaintsNames",
        urlTable: "/pmg/load/ownComplaints",
        mapper: function (object) {
            var tr = $("<tr>");
            tr.append($("<td>", {text: object.id}));
            tr.append($("<td>", {text: object.title}));
            tr.append($("<td>", {text: object.status}));
            tr.append($("<td>", {text: object.customer}));
            tr.append($("<td>", {text: object.order}));
            tr.append($("<td>", {text: object.orderStatus}));
            tr.append($("<td>", {text: object.productTitle}));
            tr.append($("<td>", {text: object.productStatus}));
            tr.append($("<td>", {text: object.date}));
            return tr;
        }
    });

    function checkLength(string) {
        if (string.toString().length < 2) {
            string = "0" + string;
        }
        return string;
    }

</script>