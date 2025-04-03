package com.nullco.rhservice.tools;

import com.nullco.rhservice.model.Perfil.respuestaEvalPerfil;
import com.nullco.rhservice.model.colaborador;
import com.nullco.rhservice.model.perfilLaboral;
import com.nullco.rhservice.model.Perfil.evalPerfilCargo;
import com.nullco.rhservice.repository.RespuestaEvalPerfilRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Component
public class comparadorPerfil {

    @Autowired
    private RespuestaEvalPerfilRepository respuestaEvalPerfilRepository;

    private static final int MAX_SIMILITUD = 50; // Porcentaje mínimo para considerar similitud válida (ajustable)

    // Normalizar cadenas eliminando tildes y convirtiendo a minúsculas
    private String normalizar(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    // Comparar conocimientos del empleado con los del perfil
    public int compararConocimientos(List<String> conocimientosEmpleado, List<String> conocimientosPerfil, List<String> conocimientosFaltantes) {
        if (conocimientosEmpleado == null || conocimientosPerfil == null || conocimientosPerfil.isEmpty()) {
            throw new IllegalArgumentException("Las listas de conocimientos no deben ser nulas ni vacías.");
        }

        LevenshteinDistance levenshtein = new LevenshteinDistance();
        int conocimientosCoinciden = 0;

        for (String conocimientoPerfil : conocimientosPerfil) {
            boolean coincide = conocimientosEmpleado.stream()
                    .anyMatch(conocimientoEmpleado -> {
                        int distancia = levenshtein.apply(conocimientoEmpleado.toLowerCase(), conocimientoPerfil.toLowerCase());
                        int longitudMax = Math.max(conocimientoEmpleado.length(), conocimientoPerfil.length());
                        double similitud = 1 - (double) distancia / longitudMax; // Similitud normalizada
                        return similitud >= 0.8; // Umbral de similitud
                    });

            if (coincide) {
                conocimientosCoinciden++;
            } else {
                conocimientosFaltantes.add(conocimientoPerfil); // Agregar a la lista de faltantes si no coincide
            }
        }

        return (int) ((double) conocimientosCoinciden / conocimientosPerfil.size() * 100);
    }

    private int esSimilar(String conocimientoEmpleado, String conocimientoPerfil) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        int distancia = levenshteinDistance.apply(normalizar(conocimientoEmpleado), normalizar(conocimientoPerfil));
        int longitudMaxima = Math.max(conocimientoEmpleado.length(), conocimientoPerfil.length());
        return (int) ((1 - (double) distancia / longitudMaxima) * 100);
    }

    public boolean compararFormacion(String formacionEmpleado, String formacionPerfil) {
        return esSimilar(formacionEmpleado, formacionPerfil) >= MAX_SIMILITUD;
    }

    public boolean compararExperiencia(Integer experienciaEmpleado, Integer experienciaPerfil) {
        return experienciaEmpleado >= experienciaPerfil;
    }


   public respuestaEvalPerfil calcularPorcentajeSimilitud(colaborador empleado, perfilLaboral perfil) {
       List<String> conocimientosFaltantes = new ArrayList<>();

       evalPerfilCargo evaluacion = perfil.getEvalPerfilCargo();
       if (evaluacion == null) {
           throw new IllegalArgumentException(empleado.getIdColaborador() + " no tiene evaluación de perfil asociada.");
       }

       double similitudConocimientos = compararConocimientos(empleado.getConocimientosBasicos(), perfil.getConocimientosBasicosPerfil(), conocimientosFaltantes);
       double similitudFormacion = compararFormacion(empleado.getFormacionAcademica(), perfil.getFormacionAcademica()) ? 100 : 0;
       double similitudExperiencia = compararExperiencia(empleado.getExperienciaLaboralAños(), perfil.getExperienciaLaboralAños()) ? 100 : 0;

       // Aplicar ponderaciones
       double porcentajeFinal =
               (similitudConocimientos * evaluacion.getPonderadoFormacion()) +
               (similitudFormacion * evaluacion.getPonderadoEducacion()) +
               (similitudExperiencia * evaluacion.getPonderadoExperiencia());

       int porcentajeSimilitud = (int) Math.min(porcentajeFinal, 100); // Asegurar que no exceda el 100%

       // Buscar si ya existe una respuesta para el colaborador
       respuestaEvalPerfil respuestaExistente = respuestaEvalPerfilRepository.findByColaborador(empleado);

       if (respuestaExistente != null) {
           // Actualizar la respuesta existente
           respuestaExistente.setSimilitudEducacion((int) similitudFormacion);
           respuestaExistente.setSimilitudConocimientos((int) similitudConocimientos);
           respuestaExistente.setSimilitudExperiencia((int) similitudExperiencia);
           respuestaExistente.setPorcentajeSimilitud(porcentajeSimilitud);
           respuestaExistente.setConocimientosFaltantes(conocimientosFaltantes);
           return respuestaEvalPerfilRepository.save(respuestaExistente);
       } else {
           // Crear y guardar una nueva respuesta
           respuestaEvalPerfil nuevaRespuesta = new respuestaEvalPerfil(null, (int) similitudFormacion, (int) similitudConocimientos, (int) similitudExperiencia, porcentajeSimilitud, conocimientosFaltantes, empleado);
           return respuestaEvalPerfilRepository.save(nuevaRespuesta);
       }
   }

    public boolean compararEmpleadoConPerfil(colaborador empleado, perfilLaboral perfil) {
        int porcentajeSimilitud = calcularPorcentajeSimilitud(empleado, perfil).getPorcentajeSimilitud();
        return porcentajeSimilitud >= 75; // Umbral configurable si es necesario
    }
}