package com.example.demo.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.PuntoAdopcion;

@Service
public class PuntoAdopcionService {
    private List<PuntoAdopcion> lugaresAdopcion = new ArrayList<>();

    public List<String> getLugaresAdopcion() {
        List<String> nombreAdopta = new ArrayList<>();
        for (PuntoAdopcion adopta : lugaresAdopcion) {
            nombreAdopta.add(adopta.getProvincia());
        }
        return nombreAdopta;
    }

    public PuntoAdopcion getAdopta(String nombre) {
        for (PuntoAdopcion adopta : lugaresAdopcion) {
            if (adopta.getProvincia().equals(nombre)) {
                return adopta;
            }
        }
        return null;
    }

    public boolean cargarAdoptaDesdeFichero() {
        List<String> lineas;
        String[] partes = new String[3];
        try {
            Path path = Paths.get("provincias.csv");
            System.out.println("Ruta del archivo CSV: " + path);
            lineas = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println("Número de líneas leídas: " + lineas.size());
        } catch (IOException ex) {
            System.err.printf("%nError%s", ex.getMessage());
            return false;
            // catch (IOException ex) {
            //     throw new RuntimeException("Error al leer el archivo CSV: " + ex.getMessage());
        }
        for (String linea : lineas) {
            partes = linea.split(";");
            if (partes.length == 6) {
                lugaresAdopcion.add(new PuntoAdopcion(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]));
            }
            else {
                return false;
            }
        }
        System.out.println("Lugares de adopción cargados desde el archivo CSV: " + lugaresAdopcion.size());
        return true;
    }
}
