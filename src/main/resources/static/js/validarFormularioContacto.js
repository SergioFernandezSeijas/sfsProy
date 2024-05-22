window.addEventListener("load",iniciar);

function iniciar() {
	document.getElementById("enviar").addEventListener('click',validarFormulario);
}

function validarAsunto() {
	let elemento = document.getElementById("asunto");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error1(elemento, "Debe introducir un asunto");
		}
		return false;
	}
	return true;
}

function validarEmail() {
	let elemento = document.getElementById("email");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error3(elemento, "Debe introducir un email");
		}
		// if (elemento.validity.valueMisMatch) {
		// 	error3(elemento, "Formato de email incorrecto");
		// }
		return false;
	}
	return true;
}


function validarComentario() {
	let elemento = document.getElementById("comentarios");
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
	if (validarAsunto() && validarEmail() && validarComentario() && confirm("Desea enviar el formulario")) {
		alert("Formulario enviado con exito");
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
    // Busca todos los elementos <span> cuyo id empieza con "mensajeError"
    let mensajesError = document.querySelectorAll("span[id^='mensajeError']"); 
    mensajesError.forEach(function(mensaje) { 
        mensaje.innerHTML = ""; 
    });
}