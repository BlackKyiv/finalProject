<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>
<h1><%= "My profile" %>
</h1>
<br/>


<div class="d-flex justify-content-center">
    <div class="p-2">
        <div class="card px-3">
            <div class="container">
                <div class="d-flex flex-row">
                    <div class="p-2">
                        <h1 class="pr-3">
                            <svg xmlns="http://www.w3.org/2000/svg" width="130" height="130" fill="currentColor"
                                 class="bi bi-person-circle" viewBox="0 0 16 16">
                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                                <path fill-rule="evenodd"
                                      d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                            </svg>
                        </h1>
                    </div>
                    <div class="p-2">
                        <div class="text-profile py-xl-2">
                            <div class="row"><h5 class="text-profile"><b>Login:</b> ${user.login}</h5></div>
                            <div class="row"><h5 class="text-profile"><b>Role:</b> ${user.role.toString()} </h5></div>
                            <div class="row"><h5 class="text-profile"><b>Account:</b> ${user.account} $ </h5></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<br>
<h3 class="text-secondary" align="center">My subscriptions</h3>
<br>
<c:forEach var="subscriptionRow" items="${subscriptionList}">
    <div class="d-flex justify-content-center">
        <c:forEach var="subscription" items="${subscriptionRow}">
            <div class="p-2">
                <div class="card px-3">
                    <div class="container">
                        <div class="d-flex flex-row">
                            <div class="p-2">
                                <div class="text-dark py-xl-2">
                                    <div class="row"><b><h5>${subscription.edition.name}</h5></b></div>
                                    <div class="row"><b><h6>${subscription.edition.price}$ per month</h6></b></div>
                                    <div class="row"><b><h6>${subscription.edition.theme.name}</h6></b></div>
                                </div>
                            </div>

                            <div class="pl-5 py-md-5">
                                <form method="post" action="controller">
                                    <input type="hidden" name="subscriptionId" value="${subscription.id}">
                                    <input type="hidden" name="editionId" value="${subscription.edition.id}">
                                    <input type="hidden" name="command" value="cancelSubscription">
                                    <input type="submit" class="btn btn-danger" value="Cancel"
                                           onclick="return confirm('Are you sure you want to cancel subscription '+'${subscription.edition.name}'+'?')">
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:forEach>


<c:if test="${noOfPages > 1}">
    <br>
    <div class="d-flex justify-content-center">
            <%--For displaying Previous link except for the 1st page --%>
        <table border="1" cellpadding="5" cellspacing="5">
            <tr>
                <c:if test="${currentPage != 1}">
                    <td><a href="?command=profile&page=${currentPage - 1}">Previous</a></td>
                </c:if>

                    <%--For displaying Page numbers.
                    The when condition does not display a link for the current page--%>


                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <td>${i}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="?command=profile&page=${i}">${i}</a></td>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>


                    <%--For displaying Next link --%>
                <c:if test="${currentPage lt noOfPages}">
                    <td><a href="?command=profile&page=${currentPage + 1}">Next</a></td>
                </c:if>
            </tr>
        </table>
    </div>
</c:if>


</body>
</html>