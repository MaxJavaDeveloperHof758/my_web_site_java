function toNum(str) {
    return Number(str.replace(/ /g, ""));
}

function toCurrency(num) {
    return new Intl.NumberFormat("be-BY", {
        style: "currency",
        currency: "BYN",
        minimumFractionDigits: 0,
    }).format(num);
}

const cartNum = document.querySelector("#cart_num");
const cart = document.querySelector("#cart");
const popup = document.querySelector(".popup");
const popupClose = document.querySelector("#popup_close");
const body = document.body;
const popupProductList = document.querySelector("#popup_product_list");
const popupCost = document.querySelector("#popup_cost");
const popupDiscount = document.querySelector("#popup_discount");
const popupCostDiscount = document.querySelector("#popup_cost_discount");

document.addEventListener("DOMContentLoaded", () => {
    cartNum.textContent = myCart.count; // Обновить счетчик при загрузке страницы
});

class Product {
    constructor(card) {
        this.imageSrc = card.querySelector(".card__image img").src;
        this.name = card.querySelector(".card__title").innerText;
        this.price = card.querySelector(".card__price--common").innerText;
        this.priceDiscount = card.querySelector(".card__price--discount").innerText;
    }
}

class Cart {
    constructor() {
        this.products = JSON.parse(localStorage.getItem("cart"))?.products || [];
    }

    get count() {
        return this.products.length;
    }

    addProduct(product) {
        this.products.push(product);
        this.updateLocalStorage();
    }

    removeProduct(index) {
        this.products.splice(index, 1);
        this.updateLocalStorage();
    }

    updateLocalStorage() {
        localStorage.setItem("cart", JSON.stringify(this));
    }

    get cost() {
        return this.products.reduce((sum, product) => sum + toNum(product.price), 0);
    }

    get costDiscount() {
        return this.products.reduce((sum, product) => sum + toNum(product.priceDiscount), 0);
    }

    get discount() {
        return this.cost - this.costDiscount;
    }
}

const myCart = new Cart();

cart.addEventListener("click", (e) => {
    e.preventDefault();
    popup.classList.add("popup--open");
    body.classList.add("lock");
    popupContainerFill();
});

popupClose.addEventListener("click", (e) => {
    e.preventDefault();
    popup.classList.remove("popup--open");
    body.classList.remove("lock");
});

document.querySelectorAll(".card__add").forEach(cardAdd => {
    cardAdd.addEventListener("click", (e) => {
        e.preventDefault();
        const card = e.target.closest(".card");
        const product = new Product(card);
        myCart.addProduct(product);
        cartNum.textContent = myCart.count;
    });
});

function popupContainerFill() {
    popupProductList.innerHTML = '';
    myCart.products.forEach((product, index) => {
        const productItem = document.createElement("div");
        productItem.classList.add("popup__product");
        productItem.innerHTML = `
            <div class="popup__product-wrap">
                <img class="popup__product-image" src="${product.imageSrc}" alt="${product.name}">
                <h2 class="popup__product-title">${product.name}</h2>
            </div>
            <div class="popup__product-wrap">
                <div class="popup__product-price">${toCurrency(toNum(product.priceDiscount))}</div>
                <button class="popup__product-delete">Remove</button>
            </div>
        `;

        const productDelete = productItem.querySelector(".popup__product-delete");
        productDelete.addEventListener("click", () => {
            myCart.removeProduct(index);
            popupContainerFill();
            cartNum.textContent = myCart.count;
        });

        popupProductList.appendChild(productItem);
    });

    popupCost.value = toCurrency(myCart.cost);
    popupDiscount.value = toCurrency(myCart.discount);
    popupCostDiscount.value = toCurrency(myCart.costDiscount);
    cartNum.textContent=myCart.count;
}