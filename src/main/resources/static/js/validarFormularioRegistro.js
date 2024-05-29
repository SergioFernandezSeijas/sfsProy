window.addEventListener("load",iniciar);

function iniciar() {
	document.getElementById("enviar").addEventListener('click',validarFormulario);
}

function validarUsuario() {
	let elemento = document.getElementById("nombre");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error1(elemento, "Debe introducir un usuario");
		}
		return false;
	}
	return true;
}

function validarEmail() {
	let elemento = document.getElementById("email");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error2(elemento, "Debe introducir un email");
		}
        else if (elemento.validity.typeMismatch) {
            error2(elemento, "Debe introducir un email válido");
        }
		return false;
	}
	return true;
}


function validarDomicilio() {
	let elemento = document.getElementById("domicilio");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error3(elemento, "Debe introducir un domicilio");
		}
		return false;
	}
	return true;
}

function validarContrasena() {
	let elemento = document.getElementById("contrasenha");
	if (!elemento.checkValidity()) {
		if (elemento.validity.valueMissing) {
			error4(elemento, "Debe introducir una contraseña");
		}
		return false;
	}
	return true;
}

function validarFormulario(e) {
	eliminarError();
	if (validarUsuario() && validarEmail() && validarDomicilio() && validarContrasena() && confirm("Desea enviar el formulario")) {
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

function error4(elemento, mensaje) {
	document.getElementById("mensajeError3").innerHTML = mensaje;
	elemento.focus();
}



function eliminarError() {
    // Busca todos los elementos <span> cuyo id empieza con "mensajeError"
    let mensajesError = document.querySelectorAll("span[id^='mensajeError']"); 
    mensajesError.forEach(function(mensaje) { 
        mensaje.innerHTML = ""; 
    });
}