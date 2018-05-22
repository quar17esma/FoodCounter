<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
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
    <div>
        <jsp:include page="/pages/header.jsp"/>
    </div>

    <div>
        <div>
            <fmt:message key="label.calorie.need"/>
            <c:out value="${calories}"/>
            <fmt:message key="label.kcal"/>
        </div>
        <div>
            <c:choose>
                <c:when test="${caloriesLeft >= 0}">
                    <fmt:message var="labelCaloriesLeft" key="label.calorie.left"/>
                </c:when>
                <c:otherwise>
                    <fmt:message var="labelCaloriesLeft" key="label.calorie.overdose"/>
                </c:otherwise>
            </c:choose>
            <label><c:out value="${labelCaloriesLeft}"/></label>
            <ctg:absValue value="${caloriesLeft}"/>
            <fmt:message key="label.kcal"/>
            <br>
        </div>
        <br>
    </div>
    <div>
        <c:choose>
            <c:when test="${meals != null && not empty meals}">
                <div>
                    <label><b><fmt:message key="label.your.today.meals"/></b></label>
                    <br>
                    <br>
                </div>
                <div>
                    <c:forEach items="${meals}" var="meal">
                        <div class="field">
                            <b><c:out value="${meal.food.name}"/></b>
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
            </c:when>
        </c:choose>
    </div>
</div>
</body>
</html>
