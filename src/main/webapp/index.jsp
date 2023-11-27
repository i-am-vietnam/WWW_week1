<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<form action="ControlServlet" method="post">
  <input type="hidden" name="action" value="login">
  Username: <input type="text" name="User">
  Password: <input type="password" name="Pass">
  <input type="submit" value="Login">
</form>
</body>
</html>