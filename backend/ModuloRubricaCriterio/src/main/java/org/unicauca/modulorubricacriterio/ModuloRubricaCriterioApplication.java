package org.unicauca.modulorubricacriterio;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.*;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.*;

@SpringBootApplication
@RequiredArgsConstructor
@EnableDiscoveryClient
public class ModuloRubricaCriterioApplication{

    private final RubricaRepository objRubricaRepository;
    private final CriterioRepository criterioRepository;
    private final NivelRepository nivelRepository;
    private final RARepository raRepository;
    private final MateriaRepository materiaRepository;

    public static void main(String[] args)   {
        SpringApplication.run(ModuloRubricaCriterioApplication.class, args);
    }


    /*
     * Este método se encarga de cargar datos de prueba en la base de datos.
    */
    private void cargarDatos(){
        List<RAEntity> listaRA = new ArrayList<>();
        RAEntity ra1 = new RAEntity(null, "Desarrollar un software funcional");
        RAEntity ra2 = new RAEntity(null, "Implementar prácticas de programación avanzadas");
        RAEntity ra3 = new RAEntity(null, "Diseñar una arquitectura de software eficiente");
        RAEntity ra4 = new RAEntity(null, "Crear documentación técnica de software");

        listaRA.add(ra1);
        listaRA.add(ra2);
        listaRA.add(ra3);
        listaRA.add(ra4);

        this.raRepository.saveAll(listaRA);

        List<MateriaEntity> listaMaterias = new ArrayList<>();
        MateriaEntity materia1 = new MateriaEntity("Desarrollo de Software", 3, "Sofrtware", EstadosEnum.ACTIVO);
        MateriaEntity materia2 = new MateriaEntity("Programación Avanzada", 3, "Sofrtware", EstadosEnum.INACTIVO);
        MateriaEntity materia3 = new MateriaEntity("Arquitectura de Software", 3, "Sofrtware", EstadosEnum.ACTIVO);
        MateriaEntity materia4 = new MateriaEntity("Ingeniería de Software", 3, "Sofrtware", EstadosEnum.INACTIVO);
        MateriaEntity materia5 = new MateriaEntity("Pruebas de Software", 3, "Sofrtware", EstadosEnum.ACTIVO);

        listaMaterias.add(materia1);
        listaMaterias.add(materia2);
        listaMaterias.add(materia3);
        listaMaterias.add(materia4);
        listaMaterias.add(materia5);

        this.materiaRepository.saveAll(listaMaterias);

        List<RubricaEntity> listaRubricas = new ArrayList<>();
        RubricaEntity rubrica1 = new RubricaEntity("Evaluación de Proyecto de Software", materia1, 3, "Objetivo 1", null, EstadosEnum.ACTIVO, ra1);
        RubricaEntity rubrica2 = new RubricaEntity("Evaluación de Prácticas de Programación", materia2, 3, "Objetivo 2", null, EstadosEnum.INACTIVO, ra1);
        RubricaEntity rubrica3 = new RubricaEntity("Evaluación de Presentación de Arquitectura de Software", materia3, 3, "Objetivo 3", null, EstadosEnum.ACTIVO, ra2);
        RubricaEntity rubrica4 = new RubricaEntity("Evaluación de Documentación Técnica", materia4, 3, "Objetivo 4", null, EstadosEnum.INACTIVO, ra3);
        RubricaEntity rubrica5 = new RubricaEntity("Evaluación de Testeo de Software", materia5, 3, "Objetivo 5", null, EstadosEnum.ACTIVO, ra4);

        listaRubricas.add(rubrica1);
        listaRubricas.add(rubrica2);
        listaRubricas.add(rubrica3);
        listaRubricas.add(rubrica4);
        listaRubricas.add(rubrica5);
        
        this.objRubricaRepository.saveAll(listaRubricas);

        List<CriterioEntity> listaCriterios = new ArrayList<>();
        CriterioEntity criterio1 = new CriterioEntity(rubrica1, "Cumple con los requisitos del proyecto", 30, 0f, "Comentario 1: El proyecto cumple parcialmente con los requisitos establecidos.", null);
        CriterioEntity criterio2 = new CriterioEntity(rubrica5, "Cumple con los estándares de calidad", 30, 0f, "Comentario 2: El proyecto muestra un nivel básico de calidad, pero algunos estándares clave no se cumplen.", null);
        CriterioEntity criterio3 = new CriterioEntity(rubrica2, "Presentación y documentación del proyecto", 40, 0f, "Comentario 3: La presentación es clara, pero la documentación necesita mejoras significativas", null);
        CriterioEntity criterio4 = new CriterioEntity(rubrica2, "Trabajo en equipo y colaboración", 30, 0f, "Comentario 4: El equipo ha trabajado de forma efectiva, aunque la colaboración podría mejorar en algunos aspectos", null);
        CriterioEntity criterio5 = new CriterioEntity(rubrica1, "Claridad en la documentación", 30, 0f, "Comentario 5: El equipo ha trabajado de forma efectiva, aunque la colaboración podría mejorar en algunos aspectos", null);
        CriterioEntity criterio6 = new CriterioEntity(rubrica2, "Cumple con los plazos establecidos", 40, 0f, "Comentario 6: El equipo ha trabajado de forma efectiva, aunque la colaboración podría mejorar en algunos aspectos", null);
        CriterioEntity criterio7 = new CriterioEntity(rubrica4, "Cumple con los requisitos del proyecto", 30, 0f, "Comentario 7: El equipo ha trabajado de forma efectiva, aunque la colaboración podría mejorar en algunos aspectos", null);
        CriterioEntity criterio8 = new CriterioEntity(rubrica3, "Cumple con los estándares de calidad", 30, 0f, "Comentario 8: El equipo ha trabajado de forma efectiva, aunque la colaboración podría mejorar en algunos aspectos", null);
        CriterioEntity criterio9 = new CriterioEntity(rubrica3, "Cumple con los plazos establecidos", 40, 0f, "Comentario 9: El equipo ha trabajado de forma efectiva, aunque la colaboración podría mejorar en algunos aspectos", null);
        CriterioEntity criterio10 = new CriterioEntity(rubrica4, "Cumple con los requisitos del proyecto", 30, 0f, "Comentario 10: El equipo ha trabajado de forma efectiva, aunque la colaboración podría mejorar en algunos aspectos", null);

        listaCriterios.add(criterio1);
        listaCriterios.add(criterio2);
        listaCriterios.add(criterio3);
        listaCriterios.add(criterio4);
        listaCriterios.add(criterio5);
        listaCriterios.add(criterio6);
        listaCriterios.add(criterio7);
        listaCriterios.add(criterio8);
        listaCriterios.add(criterio9);
        listaCriterios.add(criterio10);
        this.criterioRepository.saveAll(listaCriterios);

        // Crear niveles y asociarlos a los criterios
        List<NivelEntity> listaNiveles = new ArrayList<>();
        NivelEntity nivel1 = new NivelEntity(criterio1, "No se cumplen los requisitos básicos del proyecto.", "0-1");
        NivelEntity nivel2 = new NivelEntity(criterio1, "Se cumplen algunos de los requisitos, pero hay áreas clave que no están completas.", "1-2");
        NivelEntity nivel3 = new NivelEntity(criterio1, "Todos los requisitos del proyecto están cumplidos satisfactoriamente.", "2-3");
        NivelEntity nivel4 = new NivelEntity(criterio2, "La presentación es desordenada, y la documentación está incompleta", "3-4");
        NivelEntity nivel5 = new NivelEntity(criterio2, "La presentación es aceptable, pero la documentación está parcialmente desarrollada.", "4-5");

        listaNiveles.add(nivel1);
        listaNiveles.add(nivel2);
        listaNiveles.add(nivel3);
        listaNiveles.add(nivel4);
        listaNiveles.add(nivel5);
        this.nivelRepository.saveAll(listaNiveles);
    }
}
