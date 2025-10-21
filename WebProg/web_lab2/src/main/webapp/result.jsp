<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="yuliya.model.Point" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результат проверки</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="header">
        <h1>Результат проверки попадания точки в область</h1>
        <p>Зубулина Юлия Максимовна <p>
    </div>

    <div class="result-container table-wrapper" style="margin-top:30px;">
            <h2>Результат проверки</h2>
            <table id="result-table">
                <thead>
                    <tr>
                        <th>Координата X</th>
                        <th>Координата Y</th>
                        <th>Радиус R</th>
                        <th>Результат</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>${result.x}</td>
                        <td>${result.y}</td>
                        <td>${result.r}</td>
                        <td class="${result.hitClass}">${result.hitText}</td>
                    </tr>
                </tbody>
            </table>

        <div class="actions" style="margin-top:20px;">
            <a href="controller" class="results-link">Вернуться к форме</a>
        </div>
    </div>

    <div class="result-container table-wrapper" style="margin-top:30px;">
        <h2>История проверок</h2>
        <table id="result-table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Результат</th>
                    <th>Время проверки</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Point> results = (List<Point>) application.getAttribute("results");
                    if (results != null && !results.isEmpty()) {
                        int i = 1;
                        for (Point point : results) {
                %>
                <tr class="row-<%= point.getHitClass() %>">
                    <td class="row-number"><%= i++ %></td>
                    <td class="coordinates"><span class="coord-value"><%= point.getX() %></span></td>
                    <td class="coordinates"><span class="coord-value"><%= point.getY() %></span></td>
                    <td class="radius-value"><span class="r-badge"><%= point.getR() %></span></td>
                    <td><span class="result-badge <%= point.getHitClass() %>"><%= point.getHitText() %></span></td>
                    <td class="timestamp"><span class="time"><%= point.getTimestamp() %></span></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>