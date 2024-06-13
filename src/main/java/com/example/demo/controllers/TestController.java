package com.example.demo.controllers;

import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Pregunta;
import com.example.demo.domain.Raza;
import com.example.demo.domain.Respuesta;
import com.example.demo.services.TestService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/test")
@Controller
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/")
    public String hacerTest(Model model) {
        List<Pregunta> preguntas = testService.obtenerPreguntas();
        model.addAttribute("preguntas", preguntas);
        model.addAttribute("anhoActual", "©" + Year.now().getValue());
        return "test/testView";
    }

    // @PostMapping("/submit")  
    // public String resultadoTest(@RequestParam List<Long> respuestas, Model model) {
    //     int puntuacion = testService.calcularPuntuacion(respuestas);
    //     List<Raza> razasRecomendadas = testService.obtenerRazasRecomendadas(puntuacion);
    //     model.addAttribute("puntuacion", puntuacion);
    //     model.addAttribute("razasRecomendadas", razasRecomendadas);
    //     return "test/resultadoView";
    // }

    @PostMapping("/submit")  
    public String resultadoTest(@RequestParam("respuestas") List<Long> respuestas, Model model, HttpServletRequest request) {
        int puntuacion = testService.calcularPuntuacion(respuestas);
        List<Raza> razasRecomendadas = testService.obtenerRazasRecomendadas(puntuacion);
        model.addAttribute("puntuacion", puntuacion);
        model.addAttribute("razasRecomendadas", razasRecomendadas);
        model.addAttribute("anhoActual", "©" + Year.now().getValue());

        request.getSession().setAttribute("puntuacionTest", puntuacion);
        // return "test/resultadoView";
        return "redirect:/test/resultados";
    }

    @GetMapping("/resultados")
    public String mostrarResultados(Model model, HttpServletRequest request) {
        // Recuperar la puntuación del test del HttpSession
        Integer puntuacionTest = (Integer) request.getSession().getAttribute("puntuacionTest");
        if (puntuacionTest == null) {
            // Si no hay puntuación en el HttpSession, redirige al formulario del test
            return "redirect:/test/";
        }

        // Obtener las razas según la puntuación del test
        List<Raza> razasRecomendadas = testService.obtenerRazasRecomendadas(puntuacionTest);

        model.addAttribute("puntuacion", puntuacionTest);
        model.addAttribute("razasRecomendadas", razasRecomendadas);
        model.addAttribute("anhoActual", "©" + Year.now().getValue());

        return "test/resultadoView";
    }

    // @PostMapping("/submit")
    // public String resultadoTest(@RequestParam("respuestas") List<Long> respuestas, RedirectAttributes redirectAttributes) {
    //     int puntuacion = testService.calcularPuntuacion(respuestas);
    //     List<Raza> razasRecomendadas = testService.obtenerRazasRecomendadas(puntuacion);
        
    //     redirectAttributes.addFlashAttribute("puntuacion", puntuacion);
    //     redirectAttributes.addFlashAttribute("razasRecomendadas", razasRecomendadas);
        
    //     return "redirect:/test/resultado";
    // }

    // @GetMapping("/resultado")
    // public String mostrarResultado(Model model) {
    //     return "test/resultadoView";
    // }    



    // @GetMapping("/")
    // public String mostrarFormulario(Model model) {
    //     List<Pregunta> preguntas = testService.obtenerPreguntas();
    //     model.addAttribute("preguntas", preguntas);
    //     return "test/testView";
    // }

    // @PostMapping("/")
    // public String mostrarResultados(@RequestParam(name = "respuestas", required = false) List<Integer> respuestasIds, Model model) {
    //     if (respuestasIds == null || respuestasIds.isEmpty()) {
    //         // Controlar el caso en el que no se seleccione ninguna respuesta
    //         return "redirect:/test/";
    //     }

    //     int puntuacion = 0;
    //     for (Integer respuestaId : respuestasIds) {
    //         Respuesta respuesta = testService.obtenerRespuestaPorId(respuestaId);
    //         if (respuesta != null) {
    //             puntuacion += respuesta.getValor();
    //         }
    //     }

    //     List<Raza> razas = testService.buscarRazasPorPuntuacion(puntuacion);
    //     model.addAttribute("razas", razas);
    //     return "test/resultados";
    // }
}
