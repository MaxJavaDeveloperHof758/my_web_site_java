function validateLogin() {
    // Простая валидация
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    if (!username || !password) {
        alert('Пожалуйста, заполните все поля.');
        return false;
    }
    return true;
}