// Получаем элементы модального окна и кнопки
const modal = document.getElementById("orderModal");
const buyNowBtn = document.getElementById("buyNowBtn");
const closeModal = document.getElementById("closeModal");

// Открываем модальное окно при нажатии на кнопку
buyNowBtn.onclick = function() {
    modal.style.display = "block";
}

// Закрываем модальное окно при нажатии на "x"
closeModal.onclick = function() {
    modal.style.display = "none";
}

// Закрываем модальное окно при нажатии вне его
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

document.getElementById("orderForm").onsubmit = function(event) {
    // Здесь вы можете добавить валидацию, если нужно
    // Не отменяем стандартное поведение
};