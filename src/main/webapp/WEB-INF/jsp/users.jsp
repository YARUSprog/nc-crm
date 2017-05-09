<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    /* Always set the map height explicitly to define the size of the div * element that contains the map. */
    #map {
        height: 270px;
    }
</style>
<%@ include file="/WEB-INF/jsp/component/tableStyle.jsp" %>
<div class="content-body" data-page-name="Users">
    <div id="content-body" class="row">

        <div class="col s12">
            <ul id="tabs" class="tabs">
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
                    <li class="tab col s3"><a class="active" href="#all-users-wrapper">All Users</a></li>
                    <li class="tab col s3"><a href="#create-wrapper">Create</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_CUSTOMER')">
                    <li class="tab col s3"><a class="active" href="#my-users-wrapper">My Users</a></li>
                </sec:authorize>
            </ul>
        </div>
        <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">
            <div id="all-users-wrapper" class="col s12">

            </div>

            <div id="create-wrapper" class="col s12">
                <div class="row">
                    <form id="form-user-create" class="col s12">
                        <div class="row">
                            <div class="col s6">
                                <div class="input-field">
                                    <i class="material-icons prefix">account_circle</i>
                                    <input id="user_first_name" name="firstName" type="text" class="validate">
                                    <label for="user_first_name">First Name</label>
                                </div>
                                <div class="input-field">
                                    <i class="material-icons prefix">account_circle</i>
                                    <input id="user_middle_name" name="middleName" type="text" class="validate">
                                    <label for="user_middle_name">Middle Name</label>
                                </div>
                                <div class="input-field">
                                    <i class="material-icons prefix">account_circle</i>
                                    <input id="user_last_name" name="lastName" type="text" class="validate">
                                    <label for="user_last_name">Last Name</label>
                                </div>
                                <div class="input-field customer-field">
                                    <i class="material-icons prefix">location_on</i>
                                    <input type="text" id="customer_address"/>
                                    <label for="customer_address">Address</label>
                                </div>
                                <div class="input-field customer-field">
                                    <i class="material-icons prefix">open_with</i>
                                    <input id="customer_address_details" name="addressDetails" type="text"
                                           class="validate">
                                    <label for="customer_address_details">Address Details</label>
                                </div>
                                <div>
                                    <div class="customer-field" id="map" style="width: auto; height: 270px;"></div>
                                </div>
                            </div>
                            <div class="col s6">
                                <div class="input-field">
                                    <i class="material-icons prefix">work</i>
                                    <select name="userRole" id="user_role">
                                        <option value="ROLE_CUSTOMER">Customer</option>
                                        <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                            <option value="ROLE_ADMIN">Administrator</option>
                                            <option value="ROLE_CSR">CSR</option>
                                            <option value="ROLE_PMG">PMG</option>
                                        </sec:authorize>
                                    </select>
                                    <label for="user_role">User Role</label>
                                </div>
                                <div class="input-field" style="margin-top: 34px;">
                                    <i class="material-icons prefix">email</i>
                                    <input id="user_email" name="email" type="email" class="validate">
                                    <label for="user_email">E-mail</label>
                                </div>
                                <div class="input-field">
                                    <i class="material-icons prefix">phone</i>
                                    <input id="user_phone" name="phone" type="tel" class="validate">
                                    <label for="user_phone">Phone</label>
                                </div>
                                <p class="customer-field" style="margin-top: 15px;">
                                    <input type="checkbox" class="filled-in" id="customer_contact_person"
                                           name="contactPerson"
                                           checked="unchecked"/>
                                    <label for="customer_contact_person">Contact Person</label>
                                </p>
                                <div class="input-field customer-field" style="margin-top: 52px;">
                                    <i class="material-icons prefix">business</i>
                                    <input type="text" id="customer_organization_name" name="organizationName"
                                           class="autocomplete">
                                    <label for="customer_organization_name">Organization</label>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s6">
                                <input type="hidden" name="addressRegionName" id="customer_region_name">
                                <input type="hidden" name="addressLatitude" id="customer_address_lat">
                                <input type="hidden" name="addressLongitude" id="customer_address_long">
                                <button id="submit-user-create" class="btn waves-effect waves-light" type="submit"
                                        name="action">Create Customer
                                    <i class="material-icons right">send</i>
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_CUSTOMER')">
            <div id="my-users-wrapper" class="col s12">

            </div>
        </sec:authorize>

    </div>
</div>
<%@ include file="/WEB-INF/jsp/component/tableScript.jsp" %>
<script>

    $('ul#tabs').tabs({
        onShow: function (tab) {
        }
    });

    //////// create ////////

    $('select').material_select();
    $('#user_role option[value="ROLE_CUSTOMER"]').attr("selected", true);

    var organizationData = {};
    $.get("/organization/all", function (data) {
        data.forEach(function (item) {
            organizationData[item.name] = null;
        });

        $('#customer_organization_name').autocomplete({
            data: organizationData,
            limit: 5, // The max amount of results that can be shown at once. Default: Infinity.
            onAutocomplete: function (val) {
                $('#customer_organization_name').val(val);
            }
        });
    });

    $('#map').locationpicker({
        location: {
            latitude: 40.7324319,
            longitude: -73.82480777777776
        },
        locationName: "",
        radius: 1,
        inputBinding: {
            locationNameInput: $('#customer_address')
        },
        enableAutocomplete: true,
        enableReverseGeocode: true,
        draggable: true,
        onchanged: function (currentLocation, radius, isMarkerDropped) {
            var mapContext = $(this).locationpicker('map');
            $('#customer_region_name').val(mapContext.location.addressComponents.stateOrProvince);
            $('#customer_address_lat').val(mapContext.location.latitude);
            $('#customer_address_long').val(mapContext.location.longitude);
        },
        addressFormat: 'street_number'
    });

    $(document).on("change", "#user_role", function () {
        if ($('#user_role option:selected').val() == 'ROLE_CUSTOMER') {
            $('.customer-field').css("display", "block");
        } else {
            $('.customer-field').css("display", "none");
        }
        $('#customer_contact_person').prop('checked', false);
        $('#submit-user-create').css("display", "block");
        $('#submit-user-create').html('Create ' + $('#user_role option:selected').text());
    });

    $(document).on("click", "#submit-user-create", function () {
        event.preventDefault();
        var userForm = "#form-user-create";
        var url = "/user/registration";
        sendPost(userForm, url);
    });


    //////// all ////////


</script>