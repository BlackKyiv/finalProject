<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>
<h1><%= "My profile" %>
</h1>
<br/>

<br>
<h1 class="text-dark" align="center"><fmt:message key="text.users"/></h1>
<br>

<c:forEach var="user" items="${users}">
    <div class="d-flex justify-content-center">

        <form action="controller" class="px-2">
            <label><fmt:message key="text.login"/>: </label>
            <input type="text" name="login" value="${user.login}">
            <label><fmt:message key="text.profile.account"/>: </label>
            <input type="number" step="0.01" name="account" value="${user.account}">

            <label for="status"><fmt:message key="text.label.status"/>: </label>
            <select id="status" name="status">
                <c:choose>
                    <c:when test="${user.getStatus().toString() == 'active'}">
                        <option value="active"><fmt:message key="text.list.active"/></option>
                        <option value="blocked"><fmt:message key="text.list.blocked"/></option>
                    </c:when>
                    <c:otherwise>
                        <option value="blocked"><fmt:message key="text.list.blocked"/></option>
                        <option value="active"><fmt:message key="text.list.active"/></option>
                    </c:otherwise>
                </c:choose>
            </select>
            <label>&nbsp; &nbsp;</label>

            <input type="submit" value="<fmt:message key="text.button.update"/>" class="btn btn-primary px-2">
            <input type="hidden" name="command" value="updateUser">
            <input type="hidden" name="userId" value="${user.id}">
        </form>


        <form action="controller" class="px-2">
            <input type="submit" value="<fmt:message key="text.button.delete"/>" class="btn btn-danger">
            <input type="hidden" name="command" value="deleteUser">
            <input type="hidden" name="userId" value="${user.id}">
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
                    <td><a href="?command=users&page=${currentPage - 1}"><fmt:message key="text.button.previous"/></a>
                    </td>
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
                    <td><a href="?command=users&page=${currentPage + 1}"><fmt:message key="text.button.next"/></a></td>
                </c:if>
            </tr>
        </table>
    </div>
</c:if>

</body>
</html>