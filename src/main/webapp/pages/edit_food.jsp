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
    <title><fmt:message key="title.edit.food"/></title>
</head>
<body>
<jsp:include page="/pages/header.jsp"/>
<div>
    <c:out value="${errorAddFoodMessage}"/>
</div>
<div>
    <form name="addFoodForm" method="POST" action="./add_food">
        <c:if test="${food != null}">
            <input type="hidden" name="foodId" value="${food.id}">
        </c:if>

        <label><fmt:message key="label.food.name"/></label>
        <br/>
        <input type="text" name="name" value="${food.name}" required="required"/>
        <br/>
        <br/>

        <b><fmt:message key="label.per.100.gram"/></b><br>

        <label><fmt:message key="label.carbs"/></label>
        <br/>
        <input type="number" name="carbs" min="0" step="1" value="${food.carbs}"
               required="required">
        <fmt:message key="label.gram"/>
        <br/>

        <label><fmt:message key="label.protein"/></label>
        <br/>
        <input type="number" name="protein" min="0" step="1" value="${food.protein}"
               required="required">
        <fmt:message key="label.gram"/>
        <br/>

        <label><fmt:message key="label.fat"/></label>
        <br/>
        <input type="number" name="fat" min="0" step="1" value="${food.fat}"
               required="required">
        <fmt:message key="label.gram"/>
        <br/>

        <label><fmt:message key="label.kcal"/></label>
        <br/>
        <input type="number" name="kcal" min="0" step="1" value="${food.kcal}"
               required="required">
        <br/>

        <br/>
        <fmt:message var="buttonAddFood" key="button.add.food"/>
        <input type="submit" value="${buttonAddFood}"/>
    </form>
</div>

</body>
</html>
