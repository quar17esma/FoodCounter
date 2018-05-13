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
    <title><fmt:message key="title.registration"/></title>
</head>
<body>

<div>
    <c:out value="${errorRegistrationMessage}"/>
</div>

<div>
    <form name="registrationForm" method="POST" action="./register_client">
        <input type="hidden" name="action" value="register_client"/>

        <label><fmt:message key="label.login"/></label>
        <br/>
        <input type="text" name="login" value="${login}"/>
        <c:out value="${errorBusyEmailMessage}"/>
        <br/>

        <label><fmt:message key="label.password"/></label>
        <br/>
        <input type="password" name="password" value=""/>
        <br/>

        <label><fmt:message key="label.name"/></label>
        <br/>
        <input type="text" name="name" value="${name}"/>
        <br/>

        <label><fmt:message key="label.height"/></label>
        <br/>
        <input type="text" name="height" value="${height}"/>
        <br/>

        <label><fmt:message key="label.weight"/></label>
        <br/>
        <input type="text" name="weight" value="${weight}"/>
        <br/>

        <label><fmt:message key="label.gender"/></label>
        <br/>
        <input type="radio" name="gender" value="MALE"> <fmt:message key="label.gender.male"/><br>
        <input type="radio" name="gender" value="FEMALE"> <fmt:message key="label.gender.female"/><br>
        <br/>

        <label><fmt:message key="label.lifestyle"/></label>
        <br/>
        <input type="radio" name="lifestyle" value="LAZY"> <fmt:message key="label.lifestyle.lazy"/><br>
        <input type="radio" name="lifestyle" value="ORDINARY"> <fmt:message key="label.lifestyle.ordinary"/><br>
        <input type="radio" name="lifestyle" value="ACTIVE"> <fmt:message key="label.lifestyle.active"/><br>
        <br/>

        <label><fmt:message key="label.birth.date"/></label>
        <br/>
        <input type="date" name="birthDate" value="${birthDate}"/>
        <br/>

        <br/>
        <fmt:message var="buttonRegister" key="button.register"/>
        <input type="submit" value="${buttonRegister}"/>
    </form>
</div>

</body>
</html>
