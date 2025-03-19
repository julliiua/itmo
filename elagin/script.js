// Инициализация карты
var map = L.map('map').setView([59.976389, 30.245278], 15);

// Добавление слоя карты
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap'
}).addTo(map);

// Добавляем точки на карту
var places = [
    {
        coords: [59.977, 30.247],
        title: "Елагиноостровский дворец",
        img: "https://upload.wikimedia.org/wikipedia/commons/3/33/Yelagin_Palace_SPB_01.jpg",
        text: "Построен Карло Росси в стиле классицизма, вдохновлён античными ордерами."
    },
    {
        coords: [59.978, 30.244],
        title: "Павильон с колоннадой",
        text: "Колонны напоминают античные периптеры, создавая ощущение античного храма."
    }
];

// Добавляем маркеры
places.forEach(place => {
    L.marker(place.coords).addTo(map)
        .bindPopup(`<h3>${place.title}</h3><img src="${place.img || ''}" width="150"><p>${place.text}</p>`);
});
