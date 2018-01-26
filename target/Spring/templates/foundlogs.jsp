<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 25.01.2018
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Hello</title>
</head>
<body>
<table class="table table-striped">
<thead>
<tr>
    <th>Time</th>
    <th>Location</th>
    <th>Content</th>
</tr>
</thead>
<c:forEach var="result" items="${searchInfoResult.resultLogsList}">
    <tr>
    <td>
    ${result.timeMoment}
    </td>
    <td>${result.fileName}</td>
    <td>${result.content}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
