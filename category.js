document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.category-button');
    const cards = document.querySelectorAll('.card');

    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const category = button.getAttribute('data-category');

            cards.forEach(card => {
                const cardCategory = card.getAttribute('data-category');

                console.log(`Card category: ${card.getAttribute('data-category')}`);

                // Показываем или скрываем карточку в зависимости от выбранной категории
                if (category === 'all' || cardCategory === category) {
                    card.classList.remove('hidden'); // Показываем карточку
                } else {
                    card.classList.add('hidden'); // Скрываем карточку
                }
            });
        });
    });
});