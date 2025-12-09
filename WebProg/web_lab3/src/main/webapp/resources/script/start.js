document.addEventListener('DOMContentLoaded', function() {
    function updateClock() {
        const clock = document.getElementById("time");
        const now = new Date();
        const timeString = now.toLocaleTimeString("ru-RU");
        const dateString = now.toLocaleDateString("ru-RU");
        clock.innerHTML = `${timeString}<br>${dateString}`;
    }

    updateClock();
    setInterval(updateClock, 13000);
});