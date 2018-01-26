<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"><head id="j_idt2"><link type="text/css" rel="stylesheet" href="resources/css/style.css" /><script type="text/javascript">if(window.PrimeFaces){PrimeFaces.settings.locale='ru_RU';}</script>
    <title>
        Authorization
    </title>
    <link rel="shortcut icon" href="resources/images/favicon.ico" />
</head>
<body>
    <form action="j_security_check" method="post" class="form-container">
        <div class="form-title"><h2>Авторизуйтесь</h2></div>
        <div class="form-title">Имя</div><input id="j_username" type="text" name="j_username" class="form-field" /><br />
        <div class="form-title">Пароль</div><input id="j_password" type="password" name="j_password" value="" class="form-field" /><br />
        <div class="submit-container">
            <input type="submit" value="Вход" class="submit-button" />
        </div>
    </form>
</body>

</html>
