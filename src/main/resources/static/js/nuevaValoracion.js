window.addEventListener("load",iniciar);

function iniciar() {
	document.getElementById("enviar").addEventListener('click',validarFormulario);
}

function validarPuntuacion() {
	let elemento = document.getElementById("puntuacion");
	
    if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error1(elemento, "Debe introducir una puntuacion");
		}
        else if (elemento.validity.rangeOverflow) {
            error1(elemento,"La puntuación no puede ser mayor que 10");
        }
        else if (elemento.validity.rangeUnderflow) {
            error1(elemento, "La puntuación no puede ser menor que 0");
        }
		return false;
	}
	return true;
}


function validarTexto() {
	let elemento = document.getElementById("texto");
	
    if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error2(elemento, "Debe introducir un comentario");
		}
		return false;
	}
	return true;
}


function validarFormulario(e) {
	eliminarError();
	if (validarPuntuacion() && validarTexto()) {
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