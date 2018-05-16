<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setLocale value="en_US"/>
<c:if test="${pageContext.session.getAttribute('locale') == 'ru_RU'}">
    <fmt:setLocale value="ru_RU"/>
</c:if>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <style>
        .button {
            float: left;
        }
    </style>
    <title><fmt:message key="title.food"/></title>
</head>
<body>

    <jsp:include page="/pages/header.jsp"/>

    <div>
        <c:out value="${successAddFoodMessage}"/>
        <c:out value="${successDeleteFoodMessage}"/>
    </div>
    <div>
        <form name="searchForm" method="POST" action="./search_food">
            <label><fmt:message key="label.search.food"/></label>
            <input type="search" name="searchString" value="${searchString}">
            <fmt:message var="buttonSearch" key="button.search"/>
            <input type="submit" value="${buttonSearch}">
        </form>
        <br>
    </div>
    <div>
        <c:out value="${sorryFoodNotFoundMessage}"/>
    </div>
    <div>
        <c:forEach items="${foods}" var="food">
            <div class="field">
                <b><c:out value="${food.name}"/></b>
            </div>
            <div class="field">
                <label><fmt:message key="label.carbs"/></label>
                <c:out value="${food.carbs}"/>
                <label><fmt:message key="label.protein"/></label>
                <c:out value="${food.protein}"/>
                <label><fmt:message key="label.fat"/></label>
                <c:out value="${food.fat}"/>
            </div>
            <div class="field">
                <c:out value="${food.kcal}"/>
                <label><fmt:message key="label.kcal"/></label>
            </div>
            <div class="field">
                <form class="button" name="editMealForm" method="POST" action="./edit_meal">
                    <input type="hidden" name="foodId" value="${food.id}">
                    <fmt:message var="addMealButton" key="button.add.meal"/>
                    <input type="submit" value="${addMealButton}">
                </form>
            </div>

            <%--&lt;%&ndash;For ADMIN&ndash;%&gt;--%>
            <%--<c:if test="${sessionScope.client.user.role == 'ADMIN'}">--%>
                <%--<form class="button" name="goToEditGoodForm" method="POST" action="./edit_good">--%>
                    <%--<input type="hidden" name="goodId" value="${food.id}">--%>
                    <%--<fmt:message var="buttonEdit" key="button.edit"/>--%>
                    <%--<input type="submit" value="${buttonEdit}">--%>
                <%--</form>--%>
                <%--<form class="button" name="deleteGoodForm" method="POST" action="./delete_good">--%>
                    <%--<input type="hidden" name="goodId" value="${food.id}">--%>
                    <%--<fmt:message var="buttonDelete" key="button.delete"/>--%>
                    <%--<input type="submit" value="${buttonDelete}">--%>
                <%--</form>--%>
            <%--</c:if>--%>
            <br/>
            <hr/>
        </c:forEach>
        <br/>
    </div>

    <div>
        <c:forEach begin="1" end="${pagesQuantity}" varStatus="loop">
            <form class="button" name="foodsPagesForm" method="POST" action="./show_foods">
                <input type="hidden" name="page" value="${loop.count}">
                <input type="submit" value="${loop.count}">
            </form>
        </c:forEach>
    </div>

</body>
</html>
