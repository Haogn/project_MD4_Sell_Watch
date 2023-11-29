
document.getElementById("myAccountLink").addEventListener("click", function () {
    var dropdownContent = document.querySelector(".account-dropdown-content");
    dropdownContent.classList.toggle("show");
});

// Close the dropdown if the user clicks outside of it
window.addEventListener("click", function (event) {
    var dropdownContent = document.querySelector(".account-dropdown-content");
    if (
        event.target !== document.getElementById("myAccountLink") &&
        !event.target.closest(".account-dropdown-content")
    ) {
        dropdownContent.classList.remove("show");
    }
});