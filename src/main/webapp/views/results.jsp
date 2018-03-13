<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 25.01.2018
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Hello</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<table class="table table-striped table-bordered">
    <thead class="thead-dark">
    <tr>
        <th scope="col" class="w-25">Time</th>
        <th scope="col" class="w-25">Location</th>
        <th scope="col" class="w-50">Content</th>
    </tr>
    </thead>
    <c:forEach var="result" items="${searchInfoResult.resultLogsList}">
        <tr>
            <td>${result.timeMoment}</td>
            <td>${result.fileName}</td>
            <td>${result.content}</td>
        </tr>
    </c:forEach>
</table>
<c:if test="${searchInfoResult.errorMessage != null}">
    <div class="alert alert-secondary" role="alert">
        Ошибка! Код ${searchInfoResult.errorCode} - ${searchInfoResult.errorMessage}
    </div>
</c:if>

</body>
</html>
