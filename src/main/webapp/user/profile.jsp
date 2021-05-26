<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
</head>
<body>
<h1><%= "My profile" %>
</h1>
<br/>
<p> <%= request.getSession().getAttribute("login")%></p>
<a href="${pageContext.request.contextPath}/logout">Logout</a>

</body>
</html>