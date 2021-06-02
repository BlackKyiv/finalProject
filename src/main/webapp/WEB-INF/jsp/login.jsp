<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>

<div class=" pt-5 d-flex justify-content-center">
    <form method="post" action="controller?command=login"
          class=" pt-5">
        <div class="form-outline mb-4">
            <h3 class="d-flex justify-content-center">Log in</h3>
        </div>

        <div class="form-group">
            <input type="text" name="login" id="form1Example1" class="form-control" placeholder="Login"
                   required/>
        </div>


        <div class="form-group">
            <input type="password" name="password" class="form-control" placeholder="Password"
                   required/>
        </div>
        <div class="text-center">
            <input type="submit" value="Login" id="submit" class="btn btn-primary">
        </div>
    </form>
</div>

</body>
</html>

