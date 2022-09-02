console.log("Hello world");
const facebook = document.querySelector(".fa-facebook");
const linkedIn = document.querySelector(".fa-linkedin");
const github = document.querySelector(".fa-github");

console.log(github);

facebook.addEventListener("click", myFunction);
linkedIn.addEventListener("click", myFunction1);
github.addEventListener("click", myFunction2);

function myFunction(){
             window.open("https://www.facebook.com/charles.martins.9237");
}

function myFunction1(){
             window.open("https://www.linkedin.com/in/charlesmuvaka");
}

function myFunction2(){
             window.open("https://www.github.com/CharlesMuvaka");
}

const buttons = document.getElementsByClassName("btn");
const projectLink = buttons.getAttribute("value");

buttons.addEventListener("click", myFunction3);

function myFunction3(){
    window.open("https://www." + projectLink);
}

