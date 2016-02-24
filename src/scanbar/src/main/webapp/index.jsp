<html>
<body>
<h2>Hello World!</h2>
<%
 Cookie[] cookies = request.getCookies();
session.setAttribute("abc", "123");
System.out.print(cookies);
if(cookies != null){
	for(Cookie cookie : cookies){
		out.print(cookie.getName() + ":" + cookie.getValue());
	}
}
%>
</body>
</html>
