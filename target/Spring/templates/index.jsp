<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 29.01.2018
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        Good Luck
    </title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="resources/css/style.css"/>
</head>
<body>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="form">
    <div class="header">
        <div class="greetings">
            <h1>Поиск логов сервера</h1>
            <h3>LogsFinder</h3>
        </div>
        <div class="main_label">
            <img alt="IT god" src="resources/images/oracle.png" width="60%" />
        </div>
    </div>
    <sf:form method="POST" modelAttribute="searchInfoService">
        <div class="form-group">
            <label for="regEx">Введите регулярное выражение: </label>
            <sf:input type="input" class="form-control w-25 border-dark" id="regEx" aria-describedby="emailHelp"
                      placeholder="Регулярное выражение" path="searchInfo.regularExpression"/>
        </div>
        <div class="form-group">
            <label for="dateintervals">Выберите временные промежутки: </label>
            <div class="row w-75" id="dateintervals">
                <div class="col">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">Начало</span>
                        </div>
                        <sf:input type="text" class="form-control border-dark" aria-describedby="basic-addon1"
                                  placeholder="дд-мм-гггг чч-мм-сс"
                                  pattern="[0-9]{2}-[0-9]{2}-[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}"
                                  path="dateInterval.dateFromString"/>

                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend border-dark">
                            <span class="input-group-text" id="basic-addon2">Конец</span>
                        </div>
                        <sf:input type="text" class="form-control border-dark" aria-describedby="basic-addon2"
                                  placeholder="дд-мм-гггг чч-мм-сс"
                                  pattern="[0-9]{2}-[0-9]{2}-[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}"
                                  path="dateInterval.dateToString"/>

                    </div>
                </div>
                <div class="col">
                    <button type="button" class="btn btn-danger" id="delete">Удалить</button>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="location">Выберите расположение файла: </label>
            <sf:input type="input" class="form-control w-25 border-dark" id="location" aria-describedby="emailHelp"
                      placeholder="Название сервера, кластера или домена" path="searchInfo.location"/>
        </div>

        <div class="form-group">
            <label>Сохранять ли найденные логи в файл: </label>
            <div class="form-check form-check-inline">
                <sf:radiobutton class="form-check-input" name="inlineRadioOptions" id="optionNo" value="false"
                                path="searchInfo.realization"/>
                <label class="form-check-label" for="optionNo">Нет</label>
            </div>
            <div class="form-check form-check-inline">
                <sf:radiobutton class="form-check-input" name="inlineRadioOptions" id="optionYes" value="true"
                                path="searchInfo.realization"/>
                <label class="form-check-label" for="optionYes">Да</label>
            </div>
        </div>


        <label for="extension">Выберите расширение файла: </label>
        <div class="form-row w-50">
            <div class="col">
                <div class="form-group">

                    <sf:select class="form-control w-50 border-dark" id="extension" path="searchInfo.fileExtention">
                        <sf:option value=""/>
                        <sf:option value="XML"/>
                        <sf:option value="PDF"/>
                        <sf:option value="RTF"/>
                        <sf:option value="HTML"/>
                        <sf:option value="DOC"/>
                        <sf:option value="LOG"/>
                    </sf:select>
                </div>
            </div>
            <div class="col">
                <button type="submit" class="btn btn-success w-25">Поиск</button>
            </div>
        </div>
    </sf:form>
</div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>
