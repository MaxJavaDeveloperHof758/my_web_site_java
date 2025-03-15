function validateRegistration() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    if (password !== confirmPassword) {
        alert('Пароли не совпадают.');
        return false;
    }
    return true;
}