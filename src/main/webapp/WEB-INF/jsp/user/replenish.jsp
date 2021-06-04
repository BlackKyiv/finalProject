<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>

<div class=" pt-5 d-flex justify-content-center">
    <form method="get" action="controller"
          class=" pt-5">
        <div class="form-outline mb-4">
            <h3 class="d-flex justify-content-center"><fmt:message key="text.replenish"/></h3>
        </div>

        <div class="form-group">
            <input type="number" name="replenish" id="form1Example1" class="form-control" placeholder="Input sum" min="0" required/>
        </div>
        <div class="text-center">
            <input type="submit" value="<fmt:message key="text.button.replenish"/>" id="submit" class="btn btn-primary">
        </div>

        <input type="hidden" name="command" value="replenish" >
    </form>
</div>

</body>
</html>

