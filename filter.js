function filterByCategory(category) {
    const form = document.getElementById('filter-form');

    // Удаляем класс active у всех кнопок
    const buttons = document.querySelectorAll('.category-button');
    buttons.forEach(button => {
        button.classList.remove('active');
    });

    // Добавляем класс active к нажатой кнопке
    const activeButton = document.querySelector(`.category-button[data-category="${category}"]`);
    if (activeButton) {
        activeButton.classList.add('active');
    }

    // Скрываем предыдущие значения категории
    const categoryInput = form.querySelector('input[name="category"]');
    if (categoryInput) {
        categoryInput.remove();
    }

    // Добавляем новое значение категории
    const newCategoryInput = document.createElement('input');
    newCategoryInput.type = 'hidden';
    newCategoryInput.name = 'category';
    newCategoryInput.value = category;

    form.appendChild(newCategoryInput);
    form.submit(); // Отправляем форму
}