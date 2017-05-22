<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    .percentage {
        color: green;
    }

    .products {
        max-height: 600px;
        overflow: auto;
    }

</style>
<div class="content-body z-depth-1" data-page-name="Group #${group.id}">
    <a class="modal-trigger brown-text change-trigger" href="#update"><i class='material-icons medium'>settings</i></a>
    <div id="update" class="modal modal-fixed-footer">
        <div class="row">
            <form id="update-group">
                <div class="modal-content row">
                    <div class="row">
                        <input type='hidden' name='id' value="${group.id}"/>
                        <div class='input-field col s6'>
                            <i class="material-icons prefix">short_text</i>
                            <input class='validate' type='text' name='name' value="${group.name}" id='group_name'/>
                            <label for="group_name" class="active">Name</label>
                        </div>
                        <div class="input-field col s6">
                            <i class="material-icons prefix">loyalty</i>
                            <input type="text" id="discount-input" class="autocomplete">
                            <input type="hidden" id="discount-hidden-input" name="discountId"/>
                            <label for="discount-input" class="active">Selected discount: <span
                                    id="selected-discount"></span></label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <i class="material-icons prefix">view_list</i>
                            <input type="text" id="product-input" class="autocomplete">
                            <input type="hidden" id="product-hidden-input" name="products"/>
                            <label for="product-input">Select products</label>
                        </div>
                        <div class="col s6">
                            <ul id="selected-products" class="collection"></ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s6">
                            <button class="btn waves-effect waves-light" type="submit" id="submit-group" name="action">
                                Update
                                <i class="material-icons right">send</i>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="container">
            <h4 class="title field center">${group.name}</h4>
            <div class="divider"></div>
            <div class="row">
                <c:if test="${group.discount != null}">
                    <h4 class="title field center"><a href="#discount/${group.discount.id}">${group.discount.title}</a>
                        - <span class="percentage">${group.discount.percentage}%</span></h4>
                    <div class="divider"></div>
                </c:if>
            </div>
            <div class="row">
                <div class="section products center">
                    <ul class="collection">
                        <c:forEach items="${products}" var="product">
                            <li class="collection-item"><a href="#product/${product.id}">${product.title}</a>
                                - ${product.status}</li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_CSR')">

    $('.modal').modal({
            opacity: .5, // Opacity of modal background
            endingTop: '8%' // Starting top style attribute
        }
    );


    $("#update-group").on("submit", function (e) {
        e.preventDefault();
        send("#update-group", "/groups", "PUT").done(function () {
            $('.modal').modal('close');
            $(window).trigger('hashchange')
        })
    });

    $('#discount-input').karpo_autocomplete({
        url: "/discounts/autocomplete",
        label: "#selected-discount",
        defaultValue: "${group.discount.id} ${group.discount.title}",
        hideInput: "#discount-hidden-input"
    });



    var $groupProductSelect = $("#product-input").karpo_multi_select({
        url: "/products/autocomplete?type=withoutGroup",
        collection: "#selected-products",
        hideInput: "#product-hidden-input"
    });

    function fillCollection() {
        <c:forEach items="${products}" var="product">
            $groupProductSelect.addSelected("${product.id}" + " " + "${product.title}");
        </c:forEach>
    }
    fillCollection();


    </sec:authorize>

</script>