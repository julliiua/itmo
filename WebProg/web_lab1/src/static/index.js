"use strict";

const state = {
    x: NaN,
    y: NaN,
    r: 1.0,
};

const table = document.getElementById("result-table");
const tbody = document.querySelector("tbody");
const error = document.getElementById("error");
const canvas = document.getElementById("graph");
const ctx = canvas.getContext("2d");
const form = document.getElementById("pointForm");

drawGraph();


// Y
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

// X
document.getElementById("x-group").addEventListener("change", (e) => {
    if (e.target.type === 'checkbox') {
        document.querySelectorAll("#x-group input[type='checkbox']").forEach(c => {
            if (c !== e.target) c.checked = false;
        });
        state.x = parseFloat(e.target.value);
        hideError();
    }
});

// R
document.getElementById("r-group").addEventListener("change", (e) => {
    if (e.target.type === 'checkbox') {
        document.querySelectorAll("#r-group input[type='checkbox']").forEach(c => {
            if (c !== e.target) c.checked = false;
        });
        if (e.target.checked) {
            state.r = parseFloat(e.target.value);
        } else {
            state.r = 1.0;
        }
        hideError();
    }
});


function validateState() {
    if (isNaN(state.y) || state.y < -5 || state.y > 5) {
        showError("Y должен быть от -5 до 5");
        return false;
    }
    if (isNaN(state.x)) {
        showError("X не выбран, выбери X");
        return false;
    }
    if (isNaN(state.r) || state.r < 1 || state.r > 3) {
        showError("R не выбран, выбери R");
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
    modal.style.display = "none"
    };

    window.onclick = (event) => {
        if (event.target === modal){
        modal.style.display = "none";
        }
    };
}

function hideError() {
    error.hidden = true;
    document.getElementById("error-modal").style.display = "none";
}


form.addEventListener("submit", async function (ev) {
    ev.preventDefault();

        if (!validateState(state)){

         return;
         }

        const newRow = tbody.insertRow(0);


        const rowX = newRow.insertCell(0);
        const rowY = newRow.insertCell(1);
        const rowR = newRow.insertCell(2);
        const rowTime = newRow.insertCell(3);
        const rowExecTime = newRow.insertCell(4);
        const rowResult = newRow.insertCell(5);

        const params = new URLSearchParams();
        params.append('x', state.x);
        params.append('y', state.y);
        params.append('r', state.r);

try {
        // ОТПРАВКА ЗАПРОСА НА СЕРВЕР
        const response = await fetch("/fcgi-bin/labwork1.jar?" + params.toString());

        const results = {
            x: state.x,
            y: state.y,
            r: state.r,
            execTime: "",
            time: "",
            result: "",
        };

        if (response.ok) {
            const result = await response.json();
            results.time = new Date(result.now).toLocaleString();
            results.execTime = `${result.time} ns`;
            results.result = result.result.toString();
        } else if (response.status === 400) {
            const result = await response.json();
            results.time = new Date(result.now).toLocaleString();
            results.execTime = "N/A";
            results.result = `error: ${result.reason}`;

            rowResult.classList.add("error");
        } else {
            results.time = "N/A";
            results.execTime = "N/A";
            results.result = "server error";

            rowResult.classList.add("error");
        }

        const prevResults = JSON.parse(localStorage.getItem("results") || "[]");
        localStorage.setItem("results", JSON.stringify([...prevResults, results]));

        rowX.innerText = results.x.toString();
        rowY.innerText = results.y.toString();
        rowR.innerText = results.r.toString();
        rowTime.innerText = results.time;
        rowExecTime.innerText = results.execTime;
        rowResult.innerText = results.result;


        if (results.result === "true") {
            rowResult.innerText = "hit";
            rowResult.classList.add("hit");
        } else if (results.result === "false") {
            rowResult.innerText = "miss";
            rowResult.classList.add("miss");
        }

    } catch (error) {
        console.error("Fetch error:", error);

        const results = {
            x: state.x,
            y: state.y,
            r: state.r,
            execTime: "N/A",
            time: "N/A",
            result: "connection error"
        };

        const prevResults = JSON.parse(localStorage.getItem("results") || "[]");
        localStorage.setItem("results", JSON.stringify([...prevResults, results]));

        rowX.innerText = results.x.toString();
        rowY.innerText = results.y.toString();
        rowR.innerText = results.r.toString();
        rowTime.innerText = results.time;
        rowExecTime.innerText = results.execTime;
        rowResult.innerText = results.result;
        rowResult.classList.add("error");
    }
});


function drawGraph() {
    const w = canvas.width;
    const h = canvas.height;
    const scale = 125;
    const r = state.r;

    ctx.clearRect(0, 0, w, h);

    ctx.strokeStyle = "#000";
    ctx.lineWidth = 2;

    // Y
    ctx.beginPath();
    ctx.moveTo(w/2, h);
    ctx.lineTo(w/2, 0);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(w/2, 0); ctx.lineTo(w/2-5,10);
    ctx.moveTo(w/2, 0); ctx.lineTo(w/2+5,10);
    ctx.stroke();

    // X
    ctx.beginPath();
    ctx.moveTo(0, h/2);
    ctx.lineTo(w, h/2);
    ctx.stroke();

    ctx.beginPath();
    ctx.moveTo(w, h/2); ctx.lineTo(w-10, h/2-5);
    ctx.moveTo(w, h/2); ctx.lineTo(w-10, h/2+5);
    ctx.stroke();


    ctx.fillStyle="#000";
    ctx.font="14px sans-serif";
    ctx.fillText("x", w-20, h/2-10);
    ctx.fillText("y", w/2+10, 15);


    ctx.strokeStyle = "#555";
    ctx.lineWidth = 1;
    ctx.setLineDash([3, 3]);
    ctx.fillStyle = "#1e1e1e";
    ctx.font = "10px Arial";


    ctx.beginPath();
    ctx.moveTo(w/2 + r*scale, h/2 - 5);
    ctx.lineTo(w/2 + r*scale, h/2 + 5);
    ctx.moveTo(w/2 - 5, h/2 - r*scale);
    ctx.lineTo(w/2 + 5, h/2 - r*scale);
    ctx.stroke();

    ctx.fillText("R", w/2 + r*scale - 5, h/2 + 15);
    ctx.fillText("R", w/2 + 10, h/2 - r*scale + 5);


    ctx.beginPath();
    ctx.moveTo(w/2 - r*scale, h/2 - 5);
    ctx.lineTo(w/2 - r*scale, h/2 + 5);
    ctx.moveTo(w/2 - 5, h/2 + r*scale);
    ctx.lineTo(w/2 + 5, h/2 + r*scale);
    ctx.stroke();

    ctx.fillText("-R", w/2 - r*scale - 8, h/2 + 15);
    ctx.fillText("-R", w/2 + 10, h/2 + r*scale + 5);


    ctx.beginPath();
    ctx.moveTo(w/2 + (r/2)*scale, h/2 - 3);
    ctx.lineTo(w/2 + (r/2)*scale, h/2 + 3);
    ctx.moveTo(w/2 - 3, h/2 - (r/2)*scale);
    ctx.lineTo(w/2 + 3, h/2 - (r/2)*scale);
    ctx.stroke();

    ctx.fillText("R/2", w/2 + (r/2)*scale - 8, h/2 + 15);
    ctx.fillText("R/2", w/2 + 10, h/2 - (r/2)*scale + 5);


    ctx.beginPath();
    ctx.moveTo(w/2 - (r/2)*scale, h/2 - 3);
    ctx.lineTo(w/2 - (r/2)*scale, h/2 + 3);
    ctx.moveTo(w/2 - 3, h/2 + (r/2)*scale);
    ctx.lineTo(w/2 + 3, h/2 + (r/2)*scale);
    ctx.stroke();

    ctx.fillText("-R/2", w/2 - (r/2)*scale - 12, h/2 + 15);
    ctx.fillText("-R/2", w/2 + 10, h/2 + (r/2)*scale + 5);


    ctx.setLineDash([]);

    ctx.fillStyle = "rgba(100,149,237,0.5)";

    // треугольник
    ctx.beginPath();
    ctx.moveTo(w/2, h/2 - r/2*scale);
    ctx.lineTo(w/2 + r/2*scale, h/2);
    ctx.lineTo(w/2, h/2);
    ctx.closePath();
    ctx.fill();

    // кург
    ctx.beginPath();
    ctx.arc(w/2, h/2, r*scale, Math.PI, Math.PI*1.5, false);
    ctx.lineTo(w/2, h/2);
    ctx.closePath();
    ctx.fill();

    // квадрат
    ctx.fillRect(w/2, h/2, r*scale, r*scale);
}


function loadFromLocalStorage() {
    const prevResults = JSON.parse(localStorage.getItem("results") || "[]");
    prevResults.forEach(result => {
        const newRow = tbody.insertRow(-1);

        const rowX = newRow.insertCell(0);
        const rowY = newRow.insertCell(1);
        const rowR = newRow.insertCell(2);
        const rowTime = newRow.insertCell(3);
        const rowExecTime = newRow.insertCell(4);
        const rowResult = newRow.insertCell(5);

        rowX.innerText = result.x.toString();
        rowY.innerText = result.y.toString();
        rowR.innerText = result.r.toString();
        rowTime.innerText = result.time;
        rowExecTime.innerText = result.execTime;

        if (result.result === "true") {
            rowResult.innerText = "hit";
            rowResult.classList.add("hit");
        } else if (result.result === "false") {
            rowResult.innerText = "miss";
            rowResult.classList.add("miss");
        } else {
            rowResult.innerText = result.result;
            if (result.result.includes("error")) rowResult.classList.add("error");
        }
    });
};
