<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>
<h1><%= "Error" %>
</h1>
<br/>

<br>
<h1 class="text-dark" align="center"><fmt:message key="text.error"/></h1>
<br>

<br>
<h3 class="text-dark" align="center">${sessionScope.errorMessage}</h3>
<br>
</body>
</html>