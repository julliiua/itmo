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
    <title>Проверка попадания точки</title>
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
        .container {
            display: flex;
            gap: 20px;
        }
        .form-section {
            flex: 1;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .graph-section {
            flex: 2;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .results-section {
            margin-top: 20px;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .radio-group {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        .radio-group label {
            display: flex;
            align-items: center;
            font-weight: normal;
        }
        input[type="text"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #3498db;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #2980b9;
        }
        .error {
            color: #e74c3c;
            font-size: 12px;
            margin-top: 5px;
        }
        #graph {
            border: 1px solid #ddd;
            cursor: crosshair;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .hit { color: green; font-weight: bold; }
        .miss { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Проверка попадания точки в область</h1>
        <p>Зубулина Юлия Максимовна | Группа 3212 | Вариант 468296</p>
    </div>

    <div class="container">
        <div class="form-section">
            <form id="pointForm" action="controller" method="GET">
                <div class="form-group">
                    <label>Координата X:</label>
                    <div class="radio-group" id="x-group">
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
                    <div class="error" id="x-error"></div>
                </div>

                <div class="form-group">
                    <label>Координата Y:</label>
                    <input type="text" id="y" name="y" placeholder="Введите число от -3 до 3">
                    <div class="error" id="y-error"></div>
                </div>

                <div class="form-group">
                    <label>Радиус R:</label>
                    <div class="radio-group" id="r-group">
                        <label><input type="radio" name="r" value="1"> 1</label>
                        <label><input type="radio" name="r" value="1.5"> 1.5</label>
                        <label><input type="radio" name="r" value="2"> 2</label>
                        <label><input type="radio" name="r" value="2.5"> 2.5</label>
                        <label><input type="radio" name="r" value="3"> 3</label>
                    </div>
                    <div class="error" id="r-error"></div>
                </div>

                <button type="submit">Проверить</button>
            </form>
        </div>

        <div class="graph-section">
            <h3>Область на координатной плоскости</h3>
            <canvas id="graph" width="400" height="400"></canvas>
            <div id="graph-info"></div>
        </div>
    </div>

    <div class="results-section">
        <h3>Результаты предыдущих проверок</h3>
        <table id="resultsTable">
            <thead>
                <tr>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Результат</th>
                    <th>Время</th>
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

    <script>
        // Валидация формы
        document.getElementById('pointForm').addEventListener('submit', function(e) {
            let isValid = true;

            // Проверка X
            const xSelected = document.querySelector('input[name="x"]:checked');
            if (!xSelected) {
                document.getElementById('x-error').textContent = 'Выберите значение X';
                isValid = false;
            } else {
                document.getElementById('x-error').textContent = '';
            }

            // Проверка Y
            const yInput = document.getElementById('y');
            const yValue = parseFloat(yInput.value);
            if (isNaN(yValue) || yValue < -3 || yValue > 3) {
                document.getElementById('y-error').textContent = 'Введите число от -3 до 3';
                isValid = false;
            } else {
                document.getElementById('y-error').textContent = '';
            }

            // Проверка R
            const rSelected = document.querySelector('input[name="r"]:checked');
            if (!rSelected) {
                document.getElementById('r-error').textContent = 'Выберите значение R';
                isValid = false;
            } else {
                document.getElementById('r-error').textContent = '';
            }

            if (!isValid) {
                e.preventDefault();
            }
        });

        // График
        const canvas = document.getElementById('graph');
        const ctx = canvas.getContext('2d');
        const graphInfo = document.getElementById('graph-info');

        function drawGraph(r) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            // Настройки
            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const scale = 40; // пикселей на единицу

            // Сетка и оси
            ctx.strokeStyle = '#ccc';
            ctx.lineWidth = 1;

            // Вертикальные линии
            for (let x = centerX % scale; x < canvas.width; x += scale) {
                ctx.beginPath();
                ctx.moveTo(x, 0);
                ctx.lineTo(x, canvas.height);
                ctx.stroke();
            }

            // Горизонтальные линии
            for (let y = centerY % scale; y < canvas.height; y += scale) {
                ctx.beginPath();
                ctx.moveTo(0, y);
                ctx.lineTo(canvas.width, y);
                ctx.stroke();
            }

            // Оси
            ctx.strokeStyle = '#000';
            ctx.lineWidth = 2;

            // Ось X
            ctx.beginPath();
            ctx.moveTo(0, centerY);
            ctx.lineTo(canvas.width, centerY);
            ctx.stroke();

            // Ось Y
            ctx.beginPath();
            ctx.moveTo(centerX, 0);
            ctx.lineTo(centerX, canvas.height);
            ctx.stroke();

            // Стрелки
            ctx.fillStyle = '#000';

            // Стрелка оси X
            ctx.beginPath();
            ctx.moveTo(canvas.width - 10, centerY - 5);
            ctx.lineTo(canvas.width, centerY);
            ctx.lineTo(canvas.width - 10, centerY + 5);
            ctx.fill();

            // Стрелка оси Y
            ctx.beginPath();
            ctx.moveTo(centerX - 5, 10);
            ctx.lineTo(centerX, 0);
            ctx.lineTo(centerX + 5, 10);
            ctx.fill();

            // Подписи
            ctx.fillStyle = '#000';
            ctx.font = '12px Arial';

            // Подписи по оси X
            for (let i = -5; i <= 5; i++) {
                if (i !== 0) {
                    const x = centerX + i * scale;
                    ctx.fillText(i.toString(), x - 5, centerY + 15);
                }
            }

            // Подписи по оси Y
            for (let i = -5; i <= 5; i++) {
                if (i !== 0) {
                    const y = centerY - i * scale;
                    ctx.fillText(i.toString(), centerX + 10, y + 5);
                }
            }

            // Отрисовка области если задан R
            if (r) {
                ctx.fillStyle = 'rgba(52, 152, 219, 0.5)';
                ctx.strokeStyle = '#3498db';
                ctx.lineWidth = 2;

                const scaledR = r * scale;

                // Круг (1 четверть)
                ctx.beginPath();
                ctx.arc(centerX, centerY, scaledR, Math.PI, Math.PI * 1.5);
                ctx.lineTo(centerX, centerY);
                ctx.closePath();
                ctx.fill();
                ctx.stroke();

                // Прямоугольник (3 четверть)
                ctx.beginPath();
                ctx.rect(centerX - scaledR, centerY, scaledR, scaledR / 2);
                ctx.fill();
                ctx.stroke();

                // Треугольник (1 четверть)
                ctx.beginPath();
                ctx.moveTo(centerX, centerY);
                ctx.lineTo(centerX + scaledR / 2, centerY);
                ctx.lineTo(centerX, centerY - scaledR / 2);
                ctx.closePath();
                ctx.fill();
                ctx.stroke();
            }


            // Отрисовка существующих точек
            <%
                List<Point> pointResults = (List<Point>) application.getAttribute("results");
                if (pointResults != null) {
                    for (Point point : pointResults) {
                        String currentR = request.getParameter("r");
                        if (currentR != null && !currentR.isEmpty()) {
                            double rValue = Double.parseDouble(currentR);
                            if (point.getR() == rValue) {
            %>
                drawPoint(<%= point.getX() %>, <%= point.getY() %>, <%= point.isHit() %>, <%= rValue %>);
            <%
                            }
                        }
                    }
                }
            %>
        }

        function drawPoint(x, y, isHit, r) {
            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const scale = 40;

            const pointX = centerX + x * scale;
            const pointY = centerY - y * scale;

            ctx.fillStyle = isHit ? 'green' : 'red';
            ctx.beginPath();
            ctx.arc(pointX, pointY, 4, 0, Math.PI * 2);
            ctx.fill();
        }

        // Обработка клика по графику
        canvas.addEventListener('click', function(e) {
            const rSelected = document.querySelector('input[name="r"]:checked');

            if (!rSelected) {
                graphInfo.textContent = 'Сначала выберите радиус R';
                graphInfo.style.color = 'red';
                return;
            }

            const rect = canvas.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const scale = 40;
            const r = parseFloat(rSelected.value);

            // Преобразование координат
            const graphX = (x - centerX) / scale;
            const graphY = (centerY - y) / scale;

            // Округление до 2 знаков
            const roundedX = Math.round(graphX * 100) / 100;
            const roundedY = Math.round(graphY * 100) / 100;

            // Установка значений в форму
            document.querySelector(`input[name="x"][value="${roundedX}"]`).checked = true;
            document.getElementById('y').value = roundedY;

            // Отправка формы
            document.getElementById('pointForm').submit();
        });

        // Обновление графика при изменении R
        document.querySelectorAll('input[name="r"]').forEach(radio => {
            radio.addEventListener('change', function() {
                drawGraph(parseFloat(this.value));
                graphInfo.textContent = '';
            });
        });

        // Инициализация графика
        drawGraph(null);
    </script>
</body>
</html>