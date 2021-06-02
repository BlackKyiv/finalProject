<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Index Page</title>
</head>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<%@ include file="/WEB-INF/jspf/simple-nav.jspf" %>

<c:forEach var="edition" items="${editionsList}" >
    <p>${edition.name}</p>
</c:forEach>



</body>
</html>