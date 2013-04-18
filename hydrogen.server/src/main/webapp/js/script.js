var selected = '';

function select1() {
	var img1 = document.getElementById("img1");
	var img2 = document.getElementById("img2");
	img1.style.border = "2px solid #ff9a00";
	img2.style.border = "1px solid #000";
	selected = '1'
}

function select2() {
	var img1 = document.getElementById("img1");
	var img2 = document.getElementById("img2");
	img1.style.border = "1px solid #000";
	img2.style.border = "2px solid #ff9a00";
	selected = '2'
}

function submit() {
	if (selected === '') {
		alert("Select an item!")
		return;
	}

	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			alert("You says " + selected + " and Server says " + xmlhttp.responseText);
		}
	};
	xmlhttp.open("GET", "/track/1/" + selected, false);
	xmlhttp.send();
}