package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Rol;
import com.example.demo.domain.Usuario;
import com.example.demo.services.CategoriaService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.PuntoAdopcionService;
import com.example.demo.services.UsuarioService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(ProductoService productoService, CategoriaService categoriaService,
			UsuarioService usuarioService, PuntoAdopcionService puntoAdopcionService) {
		return args -> {
			Categoria cat1 = categoriaService.añadir(new Categoria(0L, "Comida"));
			Categoria cat2 = categoriaService.añadir(new Categoria(0L, "Accesorios"));

			productoService.añadir(new Producto(0L, "Collar", true, "Superreducido", 20d, 100, cat2));
			productoService.añadir(new Producto(0L, "Arnés", false, "Reducido", 100d, 100, cat2));

			productoService.añadir(new Producto(0L, "Piensos adulto", true, "Reducido", 25d, 100, cat1));
			productoService.añadir(new Producto(0L, "Piensos cachorro", false, "Normal", 30d, 100, cat1));

			productoService.añadir(new Producto(0L, "Correa", true, "Reducido", 15.99d, 100, cat2));
			productoService.añadir(new Producto(0L, "Chubasquero", false, "Reducido", 50d, 100, cat2));
			
			usuarioService
					.añadir(new Usuario(0L, "Sergio", "Ronda de Outeiro 40", "1234", Rol.USUARIO));
			usuarioService
					.añadir(new Usuario(0L, "Luis", "Ronda de Nelle 59", "1234", Rol.ADMIN));

			puntoAdopcionService.cargarAdoptaDesdeFichero();
		};
	}
}
