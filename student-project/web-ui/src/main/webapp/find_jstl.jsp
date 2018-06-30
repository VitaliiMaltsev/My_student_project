<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP-JSTL page</title>
</head>
<body>
<table border="1">
<thead>
<tr>
    <th>#</th>
    <th>Date</th>
    <th>Husband SurName</th>
    <th>URL</th>
</tr>
</thead>
<tbody>
<c:forEach var="sOrder" items="${ORDER_LIST}" varStatus="status">
    <c:url var="orderUrl" value="GoTo">
        <c:param name="orderId" value="${sOrder.studentOrderId}"/>
        <c:param name="orderDate" value="${sOrder.studentOrderDate}"/>
    </c:url>
        <c:if test="${status.count%2==1}">
            <tr style="background-color: yellow"
        </c:if>
    <c:if test="${status.count%2!=1}">
        <tr style="background-color: white"
    </c:if>
    <tr>
        <td>${sOrder.studentOrderId}</td>
        <td>${sOrder.studentOrderDate}</td>
        <td>${sOrder.husband.surName}</td>
        <td><a href="${orderUrl}">URL</a></td>

    </tr>
</c:forEach>
</tbody>
</table>
    </body>
    </html>
