<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
</head>
<body>
    <form action="/login.html" method="post">
        用户名：<input type="text" name="username"></br>
        密&nbsp;&nbsp;码：<input type="password" name="password"></br>
        <input type="submit" value="登录">
        ${error}
    </form>
</body>
</html>
