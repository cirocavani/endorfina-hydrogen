function get(url, target) {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			target(xmlhttp.responseText);
		}
	};
	xmlhttp.open("GET", url, false);
	xmlhttp.send();
}

function total(uid) {
	get("/total/" + uid, function(result) {
		var total = document.getElementById("total");
		total.innerHTML = "Total: " + result;
	});
}

function total1(uid) {
	get("/total/" + uid + "/1", function(result) {
		var total = document.getElementById("total1");
		total.innerHTML = "Total 1: " + result;
	});
}

function total2(uid) {
	get("/total/" + uid + "/2", function(result) {
		var total = document.getElementById("total2");
		total.innerHTML = "Total 2: " + result;
	});
}

function totalhour(uid) {
	get("/total/hour/" + uid, function(result) {
		var obj = eval("(" + result + ')'); // FIXME
		var x = []
		var y = []
		for (var _x in obj) {
	        x.push(_x);
	        y.push(obj[_x]);
	    }
		
		var data = {
				labels: x,
				datasets: [ {
					fillColor: "rgba(220,220,220,0.5)",
					strokeColor: "rgba(220,220,220,1)",
					data: y
				} ]
			};
		var total = document.getElementById("totalhour").getContext("2d");
		new Chart(total).Bar(data)
	});
}

function update() {
	var uid = "1";
	total(uid);
	total1(uid);
	total2(uid);
	totalhour(uid);
}