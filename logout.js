function confirmLogout(event) {
    if (!confirm("Вы уверены, что хотите выйти?")) {
        event.preventDefault(); // Отменяет отправку формы
    }
}