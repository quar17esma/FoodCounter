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
    <form name="registrationForm" method="POST" action="./add_client">
        <input type="hidden" name="action" value="add_client"/>

        <label><fmt:message key="label.email"/></label>
        <br/>
        <input type="text" name="email" value="${email}" required="required"/>
        <c:out value="${errorBusyEmailMessage}"/>
        <br/>

        <label><fmt:message key="label.password"/></label>
        <br/>
        <input type="password" name="password" value="" required="required"/>
        <br/>

        <label><fmt:message key="label.name"/></label>
        <br/>
        <input type="text" name="name" value="${name}" required="required"/>
        <br/>

        <label><fmt:message key="label.height"/></label>
        <br/>
        <input type="number" name="height" min="0" max="250" step="1" value="${height}"
               required="required">
        <br/>

        <label><fmt:message key="label.weight"/></label>
        <br/>
        <input type="number" name="weight" min="0" max="500" step="1" value="${weight}"
               required="required">
        <br/>

        <label><fmt:message key="label.gender"/></label>
        <br/>
        <input type="radio" name="gender" value="MALE" required="required">
        <fmt:message key="label.gender.male"/><br>
        <input type="radio" name="gender" value="FEMALE">
        <fmt:message key="label.gender.female"/><br>
        <br/>

        <label><fmt:message key="label.lifestyle"/></label>
        <br/>
        <input type="radio" name="lifestyle" value="LAZY" required="required">
        <fmt:message key="label.lifestyle.lazy"/>
        <br>
        <input type="radio" name="lifestyle" value="ORDINARY">
        <fmt:message key="label.lifestyle.ordinary"/>
        <br>
        <input type="radio" name="lifestyle" value="ACTIVE">
        <fmt:message key="label.lifestyle.active"/>
        <br>
        <br/>

        <label><fmt:message key="label.birth.date"/></label>
        <br/>
        <input type="date" name="birthDate" value="${birthDate}" required="required"/>
        <br/>

        <br/>
        <fmt:message var="buttonRegister" key="button.register"/>
        <input type="submit" value="${buttonRegister}"/>
    </form>
</div>

</body>
</html>
