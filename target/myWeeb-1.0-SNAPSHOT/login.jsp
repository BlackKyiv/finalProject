<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - index.jps Main Page</title>
</head>
<body>
<h1><%= "Login" %>
</h1>
<br/>

<form method="post" action="${pageContext.request.contextPath}/login">
    <input name="login" type="text" value="login">
    <input name="pass" type="password" value="1234">

    <input type="submit" value="Login">
</form>

</body>
</html>