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
    <link type="text/css" rel="stylesheet" href="resources/css/buttons.css"/>
    <script type="text/javascript" src="resources/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="resources/js/mask-input.js"></script>
    <script type="text/javascript" src="resources/js/form.js"></script>
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
            <input type="text" class="form-control w-25 border-dark" id="regex" aria-describedby="emailHelp"
                   placeholder="Регулярное выражение"/>
        </div>

        <div class="command-buttons">
            <button type="button" class="btn btn-warning w-100" data-toggle="modal" data-target="#pallete">Открыть палитру</button>
            <p></p>
            <button type="button" class="btn btn-secondary w-100" onclick="clearAllFields()">Очистить все</button>
        </div>

        <label for="dateintervals" style="display: block">Выберите временные промежутки: </label>
        <div class="form-group w-75"  id="here" style="display: inline-block;">
            <div class="row w-100 date-interval" id="dateintervals" >
                <div class="col">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">Начало</span>
                        </div>
                        <input type="text" class="form-control border-dark interval-field" aria-describedby="basic-addon1"
                               placeholder="дд-мм-гггг чч-мм-сс" name="dateFrom"/>

                    </div>
                </div>
                <div class="col">
                    <div class="input-group mb-3">
                        <div class="input-group-prepend border-dark">
                            <span class="input-group-text" id="basic-addon2">Конец</span>
                        </div>
                        <input type="text" class="form-control border-dark interval-field" aria-describedby="basic-addon2"
                               placeholder="дд-мм-гггг чч-мм-сс" name="dateTo"/>

                    </div>
                </div>
                <div class="col w-25 buttons">
                    <button type="button" class="btn btn-danger delete-interval" onclick="removeDateInterval(this)">Удалить</button>

                    <button type="button" id="button-add" class="btn btn-success add-interval"onclick="addDateInterval(this)" >Добавить</button>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label>Выберите расположение файла: </label>
            <input type="text" class="form-control w-25 border-dark" aria-describedby="emailHelp"
                   placeholder="Название сервера, кластера или домена" name="location"/>
        </div>

        <div class="form-group" >
            <label>Сохранять ли найденные логи в файл: </label>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="realization" id="yesoption" value="true">
                <label class="form-check-label" for="yesoption">Да</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="realization" id="nooption" value="false" checked>
                <label class="form-check-label" for="nooption">Нет</label>
            </div>
        </div>




        <label for="extension">Выберите расширение файла: </label>
        <div class="form-row w-25">
            <div class="col">
                <div class="form-group">
                    <select class="custom-select mr-sm-2" id="extension" name="extension">
                        <option selected>Extension...</option>
                        <option value="1">XML</option>
                        <option value="2">HTML</option>
                        <option value="3">PDF</option>
                        <option value="1">RTF</option>
                        <option value="2">DOC</option>
                        <option value="3">LOG</option>
                    </select>
                </div>
            </div>
        </div>

        <div class="form-row w-100">
            <button type="submit" class="btn btn-primary w-25">Поиск</button>
        </div>

        <button type="button" class="btn btn-danger exit-button" data-toggle="modal" data-target="#confimation">Выход</button>



        <div class="modal fade bd-example-modal-lg" id="pallete" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Выберите цвет формы</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row w-100 pallette-buttons">
                            <div class="col ">
                                <button type="button" class="btn  w-100 b4ffff-button"  onclick="switchColor('#b4f0f0')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col ">
                                <button type="button" class="btn  w-100 b4b4ff-button"  onclick="switchColor('#b4b4ff')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col ">
                                <button type="button" class="btn  w-100 b4c8ff-button"  onclick="switchColor('#b4c8ff')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn  w-100 b4ffc8-button"  onclick="switchColor('#b4ffc8')" data-dismiss="modal">Choose</button>
                            </div>
                        </div>
                        <div class="row w-100 pallette-buttons">
                            <div class="col">
                                <button type="button" class="btn w-100 ccb7cc-button"  onclick="switchColor('#ccb7cc')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn w-100 f2b8f2-button"  onclick="switchColor('#f2b8f2')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn w-100 b8b6bd-button"  onclick="switchColor('#b8b6bd')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn w-100 f2f0b4-button"  onclick="switchColor('#f2f0b4')" data-dismiss="modal">Choose</button>
                            </div>
                        </div>
                        <div class="row w-100 pallette-buttons">
                            <div class="col w-25">
                                <button type="button" class="btn  w-100 f5d0ba-button"  onclick="switchColor('#f5d0ba')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col w-25">
                                <button type="button" class="btn  w-100 c5dbbf-button"  onclick="switchColor('#c5dbbf')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col w-25">
                                <button type="button" class="btn  w-100 ffedcc-button"  onclick="switchColor('#ffedcc')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn  w-100 fae6c8-button"  onclick="switchColor('#fae6c8')" data-dismiss="modal">Choose</button>
                            </div>
                        </div>
                        <div class="row w-100 pallette-buttons">
                            <div class="col">
                                <button type="button" class="btn  w-100 f0fadc-button"  onclick="switchColor('#f0fadc')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn  w-100 f2f0c8-button"  onclick="switchColor('#f2f0c8')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn  w-100 b4f0f0-button"  onclick="switchColor('#b4f0f0')" data-dismiss="modal">Choose</button>
                            </div>
                            <div class="col">
                                <button type="button" class="btn  w-100 e9fab4-button"  onclick="switchColor('#e9fab4')" data-dismiss="modal">Choose</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>



    </form>
    <div class="modal fade bd-example-modal-sm" id="confimation" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exit">Выход</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Вы уверены, что хотите выйти?
                </div>
                <div class="modal-footer">
                    <form action="logout" method="POST">
                        <button type="submit" class="btn btn-danger" value="Да">Да</button>

                        <button type="button" class="btn btn-secondary"  data-dismiss="modal">Нет</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
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
