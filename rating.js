let stars = document.getElementsByClassName("star");
let output = document.getElementById("output");

// Function to update rating
function updateRating(n) {
    resetStars();
    for (let i = 0; i < n; i++) {
        stars[i].className = "star " + getStarClass(n);
    }
    output.innerText = "Rating is: " + n + "/5";
}

// Function to get the class based on the rating
function getStarClass(n) {
    switch (n) {
        case 1: return "one";
        case 2: return "two";
        case 3: return "three";
        case 4: return "four";
        case 5: return "five";
        default: return ""; // Fallback in case of invalid rating
    }
}

// To remove the pre-applied styling
function resetStars() {
    for (let star of stars) {
        star.className = "star";
    }
}