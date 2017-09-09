function checkCheckboxes() {
	var checkedCounter = 0;
	var inputs = document.getElementsByTagName("input");
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox") {
			if (inputs[i].checked == true) {
				checkedCounter++;
			}
		}
	}
	var deleteBanchButton = document.getElementById("banch-deletion-button");
	if (checkedCounter > 0) {
		deleteBanchButton.style.visibility = "visible";
	} else {
		deleteBanchButton.style.visibility = "hidden";
	}
}
