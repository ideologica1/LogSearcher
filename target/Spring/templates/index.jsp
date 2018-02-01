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
    <script type="text/javascript" src="resources/js/form.js"></script>
    <script type="text/javascript" src="resources/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="resources/js/mask-input.js"></script>
  <!--  <script type="text/javascript">
        jQuery(function ($) {
            $(".interval-field").mask("99-99-9999 99:99:99");
        });
    </script> -->
</head>
<body>

<div class="form">
    <div class="header">
        <div class="greetings">
            <h1>Поиск логов сервера</h1>
            <h3>LogsFinder</h3>
        </div>
        <div class="main_label">
            <img alt="IT god" src="resources/images/oracle.png" width="60%"/>
        </div>
    </div>
    <form method="POST" action="form">
        <div class="form-group">
            <label for="regEx">Введите регулярное выражение: </label>
            <input type="text" class="form-control w-25 border-dark" id="regex" name="regex" aria-describedby="emailHelp"
                   placeholder="Регулярное выражение"/>
        </div>
        <label for="dateintervals" style="display: block">Выберите временные промежутки: </label>
        <div class="form-group w-75" style="display: inline-block;">
            <div class="row w-100 date-interval" id="dateintervals">
                <div class="col">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">Начало</span>
                        </div>
                        <input type="text" class="form-control border-dark interval-field"
                               aria-describedby="basic-addon1"
                               placeholder="дд-мм-гггг чч-мм-сс" name="dateFrom"/>

                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend border-dark">
                            <span class="input-group-text" id="basic-addon2">Конец</span>
                        </div>
                        <input type="text" class="form-control border-dark interval-field"
                               aria-describedby="basic-addon2"
                               placeholder="дд-мм-гггг чч-мм-сс" name="dateTo"/>

                    </div>
                </div>
                <div class="col w-25 buttons">
                    <button type="button" class="btn btn-danger delete-interval" onclick="removeDateInterval(this)">
                        Удалить
                    </button>

                    <button type="button" class="btn btn-success add-interval" onclick="addDateInterval()">Добавить
                    </button>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label>Выберите расположение файла: </label>
            <input type="text" class="form-control w-25 border-dark" aria-describedby="emailHelp"
                   placeholder="Название сервера, кластера или домена" name="location"/>
        </div>

        <div class="form-group">
            <label>Сохранять ли найденные логи в файл: </label>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="asyncrealization" id="yesoption" value="true">
                <label class="form-check-label" for="yesoption">Да</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="syncrealization" id="nooption" value="false" checked>
                <label class="form-check-label" for="nooption">Нет</label>
            </div>
        </div>


        <label for="extension">Выберите расширение файла: </label>
        <div class="form-row w-25">
            <div class="col">
                <div class="form-group">
                    <select class="custom-select mr-sm-2" id="extension" name="extension">
                        <option selected>Extension...</option>
                        <option value="XML">XML</option>
                        <option value="HTML">HTML</option>
                        <option value="PDF">PDF</option>
                        <option value="RTF">RTF</option>
                        <option value="DOC">DOC</option>
                        <option value="LOG">LOG</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="form-row w-100">
            <button type="submit" class="btn btn-primary w-25">Поиск</button>
        </div>
    </form>
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
