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
<h1 class="text-dark" align="center"><fmt:message key="text.edition"/></h1>
<br>

<div class="d-flex justify-content-center">
    <form class="align-content-center" method="get" action="controller">
        <label for="themeId" class="px-2"><fmt:message key="text.label.theme"/>: </label>
        <select id="themeId" name="themeId" class="px-2">
            <option value="0">All</option>
            <c:forEach var="theme" items="${themes}">
                <c:choose>
                    <c:when test="${theme.id == themeId}">
                        <option value="${theme.id}" selected>${theme.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${theme.id}">${theme.name}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>

        <label for="sort" class="px-2"><fmt:message key="text.label.sortBy"/>: </label>
        <select id="sort" name="sort">

            <c:choose>
                <c:when test="${sort == 'names'}">
                    <option value="names" selected>By names</option>
                </c:when>
                <c:otherwise>
                    <option value="names">By names</option>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${sort == 'priceHighest'}">
                    <option value="priceHighest" selected>By price (to highest)</option>
                </c:when>
                <c:otherwise>
                    <option value="priceHighest">By price (to highest)</option>
                </c:otherwise>
            </c:choose>

            <c:choose>
                <c:when test="${sort == 'priceLowest'}">
                    <option value="priceLowest" selected>By price (to lowest)</option>
                </c:when>
                <c:otherwise>
                    <option value="priceLowest">By price (to lowest)</option>
                </c:otherwise>
            </c:choose>

        </select>

        <label class="px-2"><fmt:message key="text.label.search"/>: </label>
        <input type="text" name="query" placeholder="Search for...">
        <input type="hidden" name="command" value="main">
        <input type="submit" value="<fmt:message key="text.label.search"/>" class="btn btn-secondary">
    </form>
</div>

<br>
<br>


<c:forEach var="editionRow" items="${editionsList}">
    <div class="d-flex justify-content-center">
        <c:forEach var="edition" items="${editionRow}">
            <div class="p-2">
                <div class="card px-3">
                    <div class="container">
                        <div class="d-flex flex-row">
                            <div class="p-2">
                                <div class="text-dark py-xl-2">
                                    <div class="row"><b><h5>${edition.name}</h5></b></div>
                                    <div class="row"><b><h6>${edition.price}$ <fmt:message key="text.label.perMonth"/></h6></b></div>
                                    <div class="row"><b><h6>${edition.theme.name}</h6></b></div>
                                </div>
                            </div>

                            <div class="pl-md-5 py-4">

                                <form method="post" action="controller">
                                    <input type="hidden" name="editionId" value="${edition.id}">

                                    <c:if test="${not empty sessionScope.user}"><input type="hidden" name="command"
                                                                                       value="subscribe"></c:if>
                                    <c:if test="${empty sessionScope.user}"><input type="hidden" name="command"
                                                                                   value="login"></c:if>
                                    <input type="submit" class="btn btn-info" value="<fmt:message key="text.button.subscribe"/>"
                                           onclick="return confirm('Are you sure you want to subscribe to '+'${edition.name}'+'?')">
                                </form>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:forEach>
<br>
<c:if test="${noOfPages > 1}">
    <div class="d-flex justify-content-center">
        <table border="1" cellpadding="5" cellspacing="5">
            <tr>
                <c:if test="${currentPage != 1}">
                    <td><a href="?command=main&page=${currentPage - 1}&themeId=${themeId}&sort=${sort}&query=${query}">Previous</a>
                    </td>
                </c:if>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <a href="?command=main&page=${i}&themeId=${themeId}&sort=${sort}&query=${query}">${i}</a>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${currentPage lt noOfPages}">
                    <td><a href="?command=main&page=${currentPage + 1}&themeId=${themeId}&sort=${sort}&query=${query}">Next</a>
                    </td>
                </c:if>
            </tr>
        </table>
    </div>
</c:if>

</body>
</html>