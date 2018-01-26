<html>
<head>
    <title>
        Good Luck
    </title>
    <link type="text/css" rel="stylesheet" href="resources/css/style.css" />
</head>
<body>
<h2>Hello World!</h2>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
<h2>Create a free Spitter account</h2>
<sf:form method="POST" modelAttribute="searchInfo"> <!-- Связать форму -->
    <fieldset> <!-- с атрибутом модели -->
    <table cellspacing="0">
    <tr>
    <th><label for="regex">Regular expression:</label></th>
    <td><sf:input path="regularExpression" size="15" id="regex"/></td>
    </tr>
    <tr>
    <th><label for="location">Location:</label></th>
    <td><sf:input path="location" size="15" maxlength="15"
                  id="user_screen_name"/>
    </td>
    </tr>
    <tr>
    <th><label for="datefrom">Date From:</label></th>
    <td><sf:input path="significantDateInterval.dateFromString" size="30"
                     id="datefrom"/> <!-- Поле пароля -->
    </td>
    </tr>
    <tr>
    <th><label for="dateTo">Date To:</label></th>
    <td><sf:input path="significantDateInterval.dateToString" size="30"
                  id="dateTo"/>
    </td>
    </tr>
    <tr>
        <th><label for="extension">Extension:</label></th>
    <td><sf:input path="fileExtention" size="30"
                  id="extension"/>
    </td>
    </tr>
    </table>
        <input type="submit" value="OK" class="submit-button" />
    </fieldset>
    </sf:form>
    <spring:url value="/users/update" var="updateUrl" />

    <button class="submit-button"
            onclick="location.href='${updateUrl}'">Query</button>
</body>
</html>
