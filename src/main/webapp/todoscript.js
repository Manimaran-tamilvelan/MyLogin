let myArray = []
let consistArray = []

//var glogin = false;


window.onLoadCallback = function() {
	gapi.auth2.init({
		client_id: '628485492305-40ss5ddfpuaq2i3f5gp1g02etuasrdda.apps.googleusercontent.com'
	});
}

let addTask = function() {

	if (myArray == "") {

		for (let index = 0; index < $(".list").length; index++) {


			let s = (index + 1).toString();
			let a = document.getElementById(s);

			if (a == null) {
				//console.log("deleted ids:"+s);
			}

			else {
				myArray.push(a.textContent.trim());
				consistArray.push(a.textContent.trim());
			}
			//console.log();

		}

	}



	let task = document.getElementById("taskfield");

	if (task.value == "") {
		alert("Please Enter Task");
		return "";
	}

	//console.log(task.value);
	//console.log(myArray.length);
	for (var index = 0; index < myArray.length; index++) {
		if (consistArray[index] == task.value) {
			alert("It's already in your Task !");
			$("#taskfield").val("");
			return "";
		}
	}

	$.ajax({
		url: "todoservlet",
		type: "POST",
		data: {
			"taskName": task.value,

		},
		success: function(result) {

			if (result.trim() == "" || result.trim() == null) {
				alert("Something Went Wrong!");
				return "";
			}

			$("#noContent").hide();
			$("ul").append("<p><input value=\"&#10004;\" style=\"height: 20px; text-align:center; border-radius: 5px;\" type=\"button\" onclick=clicked(" + (myArray.length + 1) + ") id=" + (myArray.length + 100) + "> <label  for=" + (myArray.length + 100) + " class=\"list\" onclick=clicked(" + (myArray.length + 1) + ") id=" + (myArray.length + 1) + ">" + result.trim() + "</label> <input type=\"submit\" onclick=\"remove(" + (index + 1) + ")\" id=" + (index + 1 + "button") + " value= \"\" class=\"deleteButton\" > </p>");
			myArray.push(result.trim());
			consistArray.push(result.trim());

		}
	});

	$("#taskfield").val("");
}

let clicked = function(num) {
	let a = num + 99;
	$(document.getElementById(num)).css("text-decoration", 'line-through');
	$(document.getElementById(a)).css("background-color", "#FEDBD0");
}

let remove = function(num) {

	let elem = $(document.getElementById(num)).text();


	var index = consistArray.indexOf(elem);
	if (index >= 0) {
		consistArray.splice(index, 1);
	}


	$.ajax({
		url: "deleteTodo",
		type: "POST",
		data: {
			"taskName": elem,

		},
		success: function(result) {

			console.log(result);
			
			$(document.getElementById(num + "button")).remove();
			$(document.getElementById(num)).remove();
			let temp = num + 99;
			$(document.getElementById(temp)).remove();


			let listcount = $(".list").length;

			if (listcount == 0) {

				$("ul").append("<p style=\" color:gray; text-align:center;\" id=\"noContent\">No Task is Available !</p>");

			}

		}
	});

}


/*
function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function() {
		console.log('User signed out.');

	});
}*/

function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function() {
		console.log('User signed out.');
	});
}

function onLoad() {
	gapi.load('auth2', function() {
		gapi.auth2.init();
	});
}







