$(document).on('click', 'a', function (event) {
    //event.preventDefault();

    $('html, body').animate({
        scrollTop: $($.attr(this, 'href')).offset().top - 100
    }, 500);
});

document.getElementById('hiddenSection').style.display = 'none';

function showHiddenSection() {
    document.getElementById('hiddenSection').style.display = 'block';
}

document.getElementById('hiddenDiv').style.display = 'none';

function editProfile() {
    document.getElementById('hiddenDiv').style.display = 'block';
}