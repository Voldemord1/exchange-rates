<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Currency</title>
</head>
<body>
<div align="center">
    <form action="/admin/currency" method="post">
        <h3>Please, choose currency</h3>
        <p>
            <select name="mnemonics">
                <c:forEach var="currency" items="${currencies}">
                    <option value="${currency.mnemonics}">${currency.mnemonics}</option>
                </c:forEach>
            </select>
        </p
        ${message}
        <p>
            <input type="submit" class="inline" value="Submit">
            <input type="button" class="inline" value="Exit" onClick='location.href="/exit"'>
        </p>
    </form>
    <form action="/admin/currency" method="get">
        ${message}
    </form>
</div>
</body>
</html>
