console.log("Script loaded");


//change theme work
let currentTheme=getTheme();

//initial-->
document.addEventListener('DOMContentLoaded', () => {
changeTheme();
});
//todo
function changeTheme(){
	//set to web page
	changePageTheme(currentTheme,"");

//set the listner to change theme button
const changeThemeButton=document.querySelector("#theme_change_button");

//change the text of button
	changeThemeButton.querySelector("span").textContent = 
	currentTheme == "light" ? "Dark" : "Light";
	
    changeThemeButton.addEventListener("click",(event)=>{
	let oldTheme=currentTheme;
	
	console.log("change theme button clicked")
	
	if(currentTheme=="dark"){
		//iska matlab theme ko light karna hai
		currentTheme="light";
	}
	else{
		currentTheme="dark";
	}
	
	changePageTheme(currentTheme,oldTheme);
	
});
}

//set theme to localstorage
function setTheme(theme){
	localStorage.setItem("theme",theme);
}

//get theme from localstorage
function getTheme(){
	let theme=localStorage.getItem("theme");
	return theme ? theme : "light";
}

//change current page theme

function changePageTheme(theme,oldTheme){
	
	//localstorage me change karenge
	setTheme(currentTheme);
	
if(oldTheme){
		//remove the current theme
	document.querySelector("html").classList.remove(oldTheme);
}
	
	//set the current theme
	document.querySelector("html").classList.add(theme);
	
	//change the text of button
	document.querySelector('#theme_change_button').querySelector("span").textContent = 
	theme == "light" ? "Dark" : "Light";
	
}
//end of change theme work
