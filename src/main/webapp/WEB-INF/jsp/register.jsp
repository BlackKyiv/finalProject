<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>

<div class=" pt-5 d-flex justify-content-center">
    <form role="form" name="login"
          action="controller" method="post"
          class=" pt-5">
        <div class="form-outline mb-4">
            <h3 class="d-flex justify-content-center"><fmt:message key="text.register"/></h3>
        </div>

        <div class="form-group">
            <input type="text" name="login" id="form1Example1" class="form-control"
                   placeholder="<fmt:message key="text.placeholder.createLogin"/>"
                   required/>
        </div>


        <div class="form-group">
            <input type="password" name="password" class="form-control"
                   placeholder="<fmt:message key="text.placeholder.createPassword"/>"
                   required/>
        </div>
        <div class="form-group">
            <input type="password" name="passwordRepeat" class="form-control"
                   placeholder="<fmt:message key="text.placeholder.repeatPassword"/>"
                   required/>
        </div>
        <div class="text-center">
            <input type="submit" value="<fmt:message key="text.button.register"/>" id="submit" class="btn btn-success">
        </div>

        <input type="hidden" name="command" value="register">
    </form>
</div>

</body>
</html>
