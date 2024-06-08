window.addEventListener("load",iniciar);

function iniciar() {
	document.getElementById("enviar").addEventListener('click',validarFormulario);
}

function validarNombre() {
	let elemento = document.getElementById("nombre");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error1(elemento, "Debe introducir un nombre");
		}
		return false;
	}
	return true;
}


function validarPrecio() {
	let elemento = document.getElementById("precio");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error2(elemento, "Debe introducir un precio");
		}
		return false;
	}
	return true;
}


function validarStock() {
	let elemento = document.getElementById("stock");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error3(elemento, "Debe introducir un stock");
		}
		return false;
	}
	return true;
}

function validarFormulario(e) {
	eliminarError();
	if (validarNombre() && validarPrecio() && validarStock() && confirm("¿Desea crear este producto?")) {
		return true;
	}
	else {
		e.preventDefault();
		return false;
	}
}

function error1(elemento, mensaje) {
	document.getElementById("mensajeError0").innerHTML = mensaje;
	elemento.focus();
}


function error2(elemento, mensaje) {
	document.getElementById("mensajeError1").innerHTML = mensaje;
	elemento.focus();
}

function error3(elemento, mensaje) {
	document.getElementById("mensajeError2").innerHTML = mensaje;
	elemento.focus();
}

function eliminarError() {
    let mensajesError = document.querySelectorAll("span[id^='mensajeError']"); 
    mensajesError.forEach(function(mensaje) { 
        mensaje.innerHTML = ""; 
    });
}