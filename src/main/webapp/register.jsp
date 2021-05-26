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

<form method="post" action="${pageContext.request.contextPath}/reg">
    <h3> Login </h3> <br>
    <input name="login" type="text" placeholder="Your login">
    <h3> Password: </h3> <br>
    <input name="pass" type="password" placeholder="password" >

    <input type="submit" value="Login">
</form>

</body>
</html>