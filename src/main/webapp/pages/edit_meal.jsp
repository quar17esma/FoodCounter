<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--<%@ taglib prefix="ctg" uri="customtags" %>--%>
<fmt:setLocale value="en_US"/>
<c:if test="${pageContext.session.getAttribute('locale') == 'ru_RU'}">
    <fmt:setLocale value="ru_RU"/>
</c:if>
<fmt:setBundle basename="Labels"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.add.meal"/></title>
</head>
<body>

    <jsp:include page="/pages/header.jsp"/>

    <div>
        <c:out value="${successAddToCart}"/>
    </div>

    <c:if test="${food != null}">
        <div class="field">
            <label><fmt:message key="label.name"/></label>
            <c:out value="${food.name}"/>
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

        <form name="addMealForm" method="POST" action="./add_meal">
            <input type="hidden" name="foodId" value="${food.id}">
            <input type="number" min="0" step="1" name="gram" value="${meal.gram}"
                   required="required">

            <fmt:message var="buttonAddMeal" key="button.add.meal"/>
            <input class="button" type="submit" value="${buttonAddMeal}">
        </form>
        <br/>
    </c:if>

</body>
</html>
