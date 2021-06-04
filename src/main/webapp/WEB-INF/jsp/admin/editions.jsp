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
<h1 class="text-dark" align="center">Editions</h1>
<br>

<form action="controller" class="px-2" align="center">
    <label>Name: </label>
    <input type="text" name="name">
    <label>Price: </label>
    <input type="number" step="0.01" name="price">
    <label for="th">Theme: </label>
    <select id="th" name="themeId">
        <c:forEach var="theme" items="${themes}">
            <option value="${theme.id}">${theme.name}</option>
        </c:forEach>
    </select>
    <label for="st">Status: </label>
    <select id="st" name="status">
        <option value="active">Active</option>
        <option value="hidden">Hidden</option>
    </select>
    <label>&nbsp; &nbsp;</label>
    <input type="submit" value="Create" class="btn btn-success px-2">
    <input type="hidden" name="command" value="createEdition">
</form>

<br>
<br>

<c:forEach var="edition" items="${editions}">
    <div class="d-flex justify-content-center">

        <form action="controller" class="px-2">
            <label>Name: </label>
            <input type="text" name="name" value="${edition.name}">
            <label>Price: </label>
            <input type="number" step="0.01" name="price" value="${edition.price}">
            <label for="themeId">Theme: </label>
            <select id="themeId" name="themeId">
                <c:forEach var="theme" items="${themes}">
                    <c:choose>
                        <c:when test="${edition.theme.id == theme.id}">
                            <option value="${theme.id}" selected>${theme.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${theme.id}">${theme.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
            <label for="status">Status: </label>
            <select id="status" name="status">
                <c:choose>
                    <c:when test="${edition.getStatus().toString() == 'active'}">
                        <option value="active" selected>Active</option>
                        <option value="hidden">Hidden</option>
                    </c:when>
                    <c:otherwise>
                        <option value="hidden" selected disabled>Hidden</option>
                        <option value="active">Active</option>
                    </c:otherwise>
                </c:choose>
            </select>
            <label>&nbsp; &nbsp;</label>

            <input type="submit" value="Update" class="btn btn-primary px-2">
            <input type="hidden" name="command" value="updateEdition">
            <input type="hidden" name="editionId" value="${edition.id}">
        </form>


        <form action="controller" class="px-2">
            <input type="submit" value="Delete" class="btn btn-danger">
            <input type="hidden" name="command" value="deleteEdition">
            <input type="hidden" name="editionId" value="${edition.id}">
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
                    <td><a href="?command=editions&page=${currentPage - 1}">Previous</a></td>
                </c:if>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="?command=editions&page=${i}">${i}</a></td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt noOfPages}">
                    <td><a href="?command=editions&page=${currentPage + 1}">Next</a></td>
                </c:if>
            </tr>
        </table>
    </div>
</c:if>


</body>
</html>