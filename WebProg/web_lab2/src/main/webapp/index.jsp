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
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="header">
        <h1>Проверка попадания точки в область</h1>
        <h1>Лабораторная работа №2</h1>
        <p>Зубулина Юлия Максимовна <p>
        </p> Группа 3212 <p>
        </p> Вариант 468296</p>
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
    <!-- Модальное окно для ошибок -->
    <div id="error-modal" class="modal">
        <div class="modal-content">
            <span class="close-button">&times;</span>
            <h3>Ошибка валидации</h3>
            <p id="modal-error-message"></p>
        </div>
    </div>

    <div class="table-wrapper" style="margin-top: 40px;">
        <h2>История проверок</h2>
        <table id="result-table">
            <thead>
                <tr>
                    <th>№</th>
                    <th>X</th>
                    <th>Y</th>
                    <th>R</th>
                    <th>Результат</th>
                    <th>Время проверки</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<yuliya.model.Point> results = (List<yuliya.model.Point>) application.getAttribute("results");
                    if (results != null && !results.isEmpty()) {
                        int i = 1;
                        for (yuliya.model.Point point : results) {
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

    <script>
        // Состояние приложения
        const state = {
            x: NaN,
            y: NaN,
            r: 3.0
        };
        const canvas = document.getElementById('graph');
        const ctx = canvas.getContext('2d');
        const graphInfo = document.getElementById('graph-info');

        // Валидация Y
        document.getElementById("y").addEventListener("input", (ev) => {
            const input = ev.target;
            const value = input.value;
            if (!/^[-+]?\d*\.?\d+$/.test(value) && value !== "") {
                showError("Y должен быть числом! Дробные числа вводить через точку");
                state.y = NaN;
            } else {
                state.y = parseFloat(value);
                hideError();
            }
        });

        // Валидация X
        document.getElementById("x-group").addEventListener("change", (e) => {
            if (e.target.type === 'radio') {
                state.x = parseFloat(e.target.value);
                hideError();
            }
        });

        // Валидация R
        document.getElementById("r-group").addEventListener("change", (e) => {
            if (e.target.type === 'radio') {
                state.r = parseFloat(e.target.value);
                hideError();

                // Обновляем график при выборе R
                drawGraph(state.r);
            }
        });

        function validateState() {
            if (isNaN(state.y) || state.y < -3 || state.y > 3) {
                showError("Y должен быть числом от -3 до 3");
                return false;
            }
            if (isNaN(state.x)) {
                showError("X не выбран, выберите X");
                return false;
            }
            if (isNaN(state.r) || state.r < 1 || state.r > 3) {
                showError("R не выбран, выберите R");
                return false;
            }
            return true;
        }

        function showError(message) {
            const modal = document.getElementById("error-modal");
            const modalMessage = document.getElementById("modal-error-message");
            const closeButton = modal.querySelector(".close-button");

            modalMessage.textContent = message;
            modal.style.display = "block";

            closeButton.onclick = () => {
                modal.style.display = "none";
            };

            window.onclick = (event) => {
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            };
        }

        function hideError() {
            document.getElementById("error-modal").style.display = "none";
        }

        // Обработка отправки формы
        document.getElementById('pointForm').addEventListener('submit', function(e) {
            if (!validateState()) {
                e.preventDefault();
            }
        });
        function drawGraph(r) {
            ctx.clearRect(0, 0, canvas.width, canvas.height);

            // Настройки
            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const scale = 40;

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
            ctx.strokeStyle = '#2c3e50';
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
        }


        // Отрисовка существующих точек
        function drawExistingPoints(currentR) {
            // Используем данные из скрытого поля или localStorage
            const savedResults = localStorage.getItem('graphPoints');
            if (savedResults) {
                const points = JSON.parse(savedResults);
                points.forEach(point => {
                    if (Math.abs(point.r - currentR) < 0.001) {
                        drawPoint(point.x, point.y, point.hit);
                    }
                });
            }
        }
        function drawPoint(x, y, isHit) {
            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const scale = 40;

            const pointX = centerX + x * scale;
            const pointY = centerY - y * scale;

            // Рисуем точку
            ctx.fillStyle = isHit ? '#588157' : '#d88c8c';
            ctx.beginPath();
            ctx.arc(pointX, pointY, 5, 0, Math.PI * 2);
            ctx.fill();

            ctx.strokeStyle = '#2c3e50';
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.arc(pointX, pointY, 5, 0, Math.PI * 2);
            ctx.stroke();
        }

        // Обработка клика по графику
        canvas.addEventListener('click', function(e) {
            if (isNaN(state.r)) {
                graphInfo.textContent = 'Сначала выберите радиус R';
                graphInfo.className = 'graph-info error';
                showError("Сначала выберите радиус R");
                return;
            }

            const rect = canvas.getBoundingClientRect();
            const x = e.clientX - rect.left;
            const y = e.clientY - rect.top;

            const centerX = canvas.width / 2;
            const centerY = canvas.height / 2;
            const scale = 40;

            const graphX = (x - centerX) / scale;
            const graphY = (centerY - y) / scale;

            const roundedX = Math.round(graphX * 100) / 100;
            const roundedY = Math.round(graphY * 100) / 100;

            // Отправляем запрос на сервер
            checkPointOnServer(roundedX, roundedY, state.r);
        });

        // Функция для отправки запроса на сервер
        function checkPointOnServer(x, y, r) {
            // Создаем форму для отправки
            const formData = new FormData();
            formData.append('x', x);
            formData.append('y', y);
            formData.append('r', r);

            // Отправляем GET запрос (как в форме)
            const params = new URLSearchParams();
            params.append('x', x);
            params.append('y', y);
            params.append('r', r);

            fetch('controller?' + params.toString(), {
                method: 'GET'
            })
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error('Ошибка сервера');
            })
            .then(html => {
                // Парсим ответ чтобы получить результат
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');

                // Ищем результат на странице
                const resultElement = doc.querySelector('.hit, .miss');
                let isHit = false;

                if (resultElement) {
                    isHit = resultElement.classList.contains('hit');

                    // Сохраняем точку в localStorage
                    savePointToStorage(x, y, r, isHit);

                    // Перерисовываем график с новой точкой
                    drawGraph(r);
                    drawPoint(x, y, isHit);
                    drawExistingPoints(r);

                    // Обновляем таблицу на странице результатов
                    setTimeout(() => {
                        window.location.href = 'result.jsp';
                    }, 1500);

                } else {
                    throw new Error('Не удалось определить результат');
                }
            })
            .catch(error => {
                graphInfo.textContent = 'Ошибка при проверке точки';
                graphInfo.className = 'graph-info error';
                showError(error.message);
            });
        }

        // Сохранение точки в localStorage
        function savePointToStorage(x, y, r, hit) {
            const points = JSON.parse(localStorage.getItem('graphPoints') || '[]');
            points.push({ x, y, r, hit, timestamp: new Date().toISOString() });
            localStorage.setItem('graphPoints', JSON.stringify(points));
        }
        document.getElementById("r-group").addEventListener("change", (e) => {
            if (e.target.type === 'radio') {
                state.r = parseFloat(e.target.value);
                hideError();

                //Сохраняем выбранный радиус в localStorage
                localStorage.setItem('selectedR', state.r);

                drawGraph(state.r);
            }
        });

        function initGraph() {
            const savedR = localStorage.getItem('selectedR');
            if (savedR) {
                state.r = parseFloat(savedR);

                const radio = document.querySelector(`input[name="r"][value="${savedR}"]`);
                if (radio) radio.checked = true;

                drawGraph(state.r);
                drawExistingPoints(state.r);
            } else {
                drawGraph(null);
            }
        }

    // Запускаем инициализацию при загрузке страницы
    document.addEventListener('DOMContentLoaded', initGraph);
    </script>
</body>
</html>