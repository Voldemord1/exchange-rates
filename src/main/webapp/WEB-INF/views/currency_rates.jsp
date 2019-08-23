<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Currency rates</title>
</head>
<body>
<div align="center">
    <form action="/admin/currency_rates" method="get">
        <table align="top" border="1" cellpadding="4" cellspacing="0">
            <h3>Currency rate for ${currency.mnemonics} on ${date}</h3>
            <tr>
                <th>CodeA</th>
                <th>CodeB</th>
                <th>Buy</th>
                <th>Sell</th>
            </tr>
            <tr>
                <td>${journal.currencyCodeA}</td>
                <td>${journal.currencyCodeB}</td>
                <td>${journal.rateBuy}</td>
                <td>${journal.rateSell}</td>
            </tr>
        </table>
        <p>${message}</p>
    </form>
    <p>
        <input type="button" class="inline" value="Chose currency" onClick='location.href="/admin/currency"'>
        <input type="button" class="inline" value="Exit" onClick='location.href="/exit"'>
    </p>
</div>
</body>
</html>
