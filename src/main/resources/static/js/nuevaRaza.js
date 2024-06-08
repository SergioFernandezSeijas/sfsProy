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


function validarPuntuacion() {
	let elemento = document.getElementById("puntuacion");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error2(elemento, "Debe introducir una puntuacion");
		}
		return false;
	}
	return true;
}

function validarFormulario(e) {
	eliminarError();
	if (validarNombre() && validarPuntuacion() && confirm("¿Desea crear esta raza de perro?")) {
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

function eliminarError() {
    let mensajesError = document.querySelectorAll("span[id^='mensajeError']"); 
    mensajesError.forEach(function(mensaje) { 
        mensaje.innerHTML = ""; 
    });
}