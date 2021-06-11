<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>
<h1><%= "Themes" %>
</h1>
<br>
<h1 class="text-dark" align="center"><fmt:message key="text.themes"/></h1>
<br>

<form action="controller" class="px-2" align="center">
    <label><fmt:message key="text.label.theme"/>: </label>
    <input type="text" name="name" value="${theme.name}">
    <label>&nbsp; &nbsp;</label>
    <input type="submit" value="<fmt:message key="text.button.create"/>" class="btn btn-success px-2">
    <input type="hidden" name="command" value="createTheme">
</form>

<br>
<br>
<c:forEach var="theme" items="${themes}">
    <div class="d-flex justify-content-center">
        <form action="controller" class="px-2">
            <label><fmt:message key="text.label.theme"/>: </label>
            <input type="text" name="name" value="${theme.name}">
            <label>&nbsp; &nbsp;</label>
            <input type="submit" value="<fmt:message key="text.button.update"/>" class="btn btn-primary px-2">
            <input type="hidden" name="command" value="updateTheme">
            <input type="hidden" name="themeId" value="${theme.id}">
        </form>

        <form action="controller" class="px-2">
            <input type="submit" value="<fmt:message key="text.button.delete"/>" class="btn btn-danger">
            <input type="hidden" name="command" value="deleteTheme">
            <input type="hidden" name="themeId" value="${theme.id}">
        </form>



    </div>
    <br>
</c:forEach>

<br>
<c:if test="${noOfPages > 1}">
    <div class="d-flex justify-content-center">
        <table border="1" cellpadding="5" cellspacing="5">
            <tr>
                <c:if test="${currentPage != 1}">
                    <td><a href="?command=users&page=${currentPage - 1}">Previous</a></td>
                </c:if>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="?command=users&page=${i}">${i}</a></td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt noOfPages}">
                    <td><a href="?command=users&page=${currentPage + 1}">Next</a></td>
                </c:if>
            </tr>
        </table>
    </div>
</c:if>

</body>
</html>