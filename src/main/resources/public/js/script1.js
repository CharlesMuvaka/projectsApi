const github = document.querySelector(".fa-github");
console.log(github.getAttribute("value"));

github.addEventListener("click", myFunction2);

function myFunction2(){
             window.open("https://www." + github.getAttribute("value"));
}