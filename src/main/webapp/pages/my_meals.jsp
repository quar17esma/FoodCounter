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
    <title><fmt:message key="title.my.meals"/></title>
</head>
<body>

<div>
    <jsp:include page="/pages/header.jsp"/>
</div>

<div>
    <c:forEach items="${meals}" var="meal">

        <div class="field">
            <b><c:out value="${meal.food.name}"/></b>
        </div>
        <div class="field">
            <label><fmt:message key="label.carbs"/></label>
            <c:out value="${meal.food.carbs}"/>
            <label><fmt:message key="label.protein"/></label>
            <c:out value="${meal.food.protein}"/>
            <label><fmt:message key="label.fat"/></label>
            <c:out value="${meal.food.fat}"/>
        </div>
        <div class="field">
            <c:out value="${meal.kcal}"/>
            <label><fmt:message key="label.kcal"/></label>
            <c:out value="${meal.gram}"/>
            <label><fmt:message key="label.gram"/></label>
        </div>
        <br>
    </c:forEach>

</div>

</body>
</html>
