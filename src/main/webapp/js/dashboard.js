function getCookie(cname) {
	let name = cname + "=";
	let ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}
function checkCookie() {
	let user = getCookie("token");
	if ((user !== "")) {
		console.log("check get cookie"); 	
		return true;
	} else {
		console.log("check get cookie false");
		return false;
	}
}

jQuery(document).ready(function ($) {
	checkCookie();
});