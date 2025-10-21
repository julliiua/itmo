<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="yuliya.model.Point" %>
<!DOCTYPE html>
<html>
<head>
    <title>Лабораторная работа №2</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Проверка попадания точки в область</h1>
        <div class="header-info">
            <h1>Лабораторная работа №2</h1>
            <p>Проверка попадания точки в область</p>
            <p>Студент: Зубулина Юлия Максимовна </p>
            <p> Группа: P3212</p>
            <p> Вариант: 468296</p>
        </div>
    </div>
    <div class="content">
        <div class="form-section">
            <h2>Параметры точки и области</h2>
            <form id="pointForm" method="GET" action="controller">
                <h2>Выберите X</h2>
                <div class="checkbox-group" id="x-group">
                    <label><input type="radio" name="x" value="-5"> -5</label>
                    <label><input type="radio" name="x" value="-4"> -4</label>
                    <label><input type="radio" name="x" value="-3"> -3</label>
                    <label><input type="radio" name="x" value="-2"> -2</label>
                    <label><input type="radio" name="x" value="-1"> -1</label>
                    <label><input type="radio" name="x" value="0"> 0</label>
                    <label><input type="radio" name="x" value="1"> 1</label>
                    <label><input type="radio" name="x" value="2"> 2</label>
                    <label><input type="radio" name="x" value="3"> 3</label>
                </div>

                <div class="form-group">
                    <label for="y">Координата Y:</label>
                    <input type="text" id="y" name="y" placeholder="Введите число от -3 до 3" value="">
                    <div class="error-message" id="yError"></div>
                </div>

            <h2>Выберите R</h2>
            <div class="checkbox-group" id="r-group">
                <label><input type="radio" name="r" value="1"> 1</label>
                <label><input type="radio" name="r" value="1.5"> 1.5</label>
                <label><input type="radio" name="r" value="2"> 2</label>
                <label><input type="radio" name="r" value="2.5"> 2.5</label>
                <label><input type="radio" name="r" value="3"> 3</label>
            </div>

                <button type="submit" class="submit-btn">Проверить</button>
            </form>
        </div>

        <div class="graph-section">
            <h2>Область на координатной плоскости</h2>
            <div class="graph-container" id="graphContainer">
                <canvas id="graphCanvas" width="500" height="400"></canvas>
            </div>
        </div>

        <div class="results-section">
            <h2>Результаты проверок</h2>
            <table class="results-table">
                <thead>
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Результат</th>
                    <th>Время</th>
                </tr>
                </thead>
                <tbody id="resultsBody">
                <c:forEach var="point" items="${results.points}">
                    <tr>
                        <td>${point.x}</td>
                        <td>${point.y}</td>
                        <td>${point.r}</td>
                        <td class="${point.inside ? 'result-hit' : 'result-miss'}">
                                ${point.inside ? 'Попадание' : 'Промах'}
                        </td>
                        <td>${point.timestamp}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<div class="notification" id="notification"></div>

<script>
    const sessionPoints = [
        <c:forEach var="point" items="${results.points}" varStatus="status">
        {
            x: ${point.x},
            y: ${point.y},
            r: ${point.r},
            inside: ${point.inside}
        }<c:if test="${!status.last}">,</c:if>
        </c:forEach>
    ];
</script>

<script src="index.js"></script>
</body>
</html>