<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="yuliya.model.Point" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.*" %>
<%@ page import="jakarta.servlet.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Результат проверки</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #2c3e50;
            color: white;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .result-container {
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .hit { color: green; font-weight: bold; }
        .miss { color: red; font-weight: bold; }
        .back-link {
            display: inline-block;
            background-color: #3498db;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
        }
        .back-link:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Результат проверки попадания точки</h1>
        <p>Зубулина Юлия Максимовна | Группа 3212 | Вариант 468296</p>
    </div>

    <div class="result-container">
        <h2>Результат проверки</h2>
        <table>
            <tr>
                <th>Координата X</th>
                <th>Координата Y</th>
                <th>Радиус R</th>
                <th>Результат</th>
            </tr>
            <tr>
                <td>${result.x}</td>
                <td>${result.y}</td>
                <td>${result.r}</td>
                <td class="${result.hitClass}">${result.hitText}</td>
            </tr>
        </table>

        <a href="controller" class="back-link">Вернуться к форме</a>
    </div>

    <div class="result-container">
        <h2>История проверок</h2>
        <table>
            <thead>
                <tr>
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
                        for (Point point : results) {
                %>
                    <tr>
                        <td><%= point.getX() %></td>
                    <td><%= point.getY() %></td>
                    <td><%= point.getR() %></td>
                    <td class="<%= point.getHitClass() %>">
                        <%= point.getHitText() %>
                    </td>
                    <td><%= point.getTimestamp() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="5">Нет данных</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>