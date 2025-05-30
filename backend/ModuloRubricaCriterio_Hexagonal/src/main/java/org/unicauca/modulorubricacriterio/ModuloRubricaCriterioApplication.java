package org.unicauca.modulorubricacriterio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.*;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.*;

@SpringBootApplication
public class ModuloRubricaCriterioApplication implements CommandLineRunner {

    
    @Autowired
    private RubricaRepository objRubricaRepository;

    @Autowired
    private CriterioRepository criterioRepository;

    @Autowired
    private NivelRepository nivelRepository;

    @Autowired
    private RARepository raRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public static void main(String[] args)   {
        SpringApplication.run(ModuloRubricaCriterioApplication.class, args);
    }

    /*
     * Este método se ejecuta al iniciar la aplicación y se encarga de cargar datos de prueba en la base de datos.
    */
    @Override
	public void run(String... args) throws Exception {
		cargarDatos();
	}


    /*
     * Este método se encarga de cargar datos de prueba en la base de datos.
    */
    private void cargarDatos(){
        List<RAEntity> listaRA = new ArrayList<>();
        RAEntity ra1 = new RAEntity();
        RAEntity ra2 = new RAEntity();
        RAEntity ra3 = new RAEntity();
        RAEntity ra4 = new RAEntity();

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
        RubricaEntity rubrica1 = new RubricaEntity("Evaluación de Proyecto de Software", materia1, 3, "Evaluar la capacidad de crear mecanismos eficientes de control de la evaluación de proyectos software", null, EstadosEnum.ACTIVO, ra1);
        RubricaEntity rubrica2 = new RubricaEntity("Evaluación de Prácticas de Programación", materia2, 3, "Realizar un análisis y calificación del uso efectivo de buenas prácticas de programación", null, EstadosEnum.INACTIVO, ra1);
        RubricaEntity rubrica3 = new RubricaEntity("Evaluación de Presentación de Arquitectura de Software", materia3, 3, "Evaluar la capacidad del desarrollador para realizar correctas interpretaciones de la arquitectura más conveniente para la realización de programas", null, EstadosEnum.ACTIVO, ra2);
        RubricaEntity rubrica4 = new RubricaEntity("Evaluación de Documentación Técnica", materia4, 3, "Hacer una evaluación sobre los documentos formales requeridos por el cliente para el uso correcto del sistema software", null, EstadosEnum.INACTIVO, ra3);
        RubricaEntity rubrica5 = new RubricaEntity("Evaluación de Testeo de Software", materia5, 3, "Calificar la correcta sintaxis y documentación acerca de la ejecución de pruebas software", null, EstadosEnum.ACTIVO, ra4);

        listaRubricas.add(rubrica1);
        listaRubricas.add(rubrica2);
        listaRubricas.add(rubrica3);
        listaRubricas.add(rubrica4);
        listaRubricas.add(rubrica5);
        
        this.objRubricaRepository.saveAll(listaRubricas);

        List<CriterioEntity> listaCriterios = new ArrayList<>();

    // Rubrica 1 - Evaluación de Proyecto de Software
    CriterioEntity criterio1 = new CriterioEntity(rubrica1, "Cumple con los requisitos del proyecto", 30, 0f, "El proyecto cubre los requerimientos funcionales, aunque algunos módulos no fueron completamente implementados.", null);
    CriterioEntity criterio2 = new CriterioEntity(rubrica1, "Claridad en la documentación", 30, 0f, "La documentación técnica está bien estructurada pero carece de ejemplos claros en ciertas secciones.", null);
    CriterioEntity criterio3 = new CriterioEntity(rubrica1, "Innovación en la solución", 40, 0f, "La propuesta presenta un enfoque creativo para la gestión de procesos, aunque podría optimizarse en rendimiento.", null);

    // Rubrica 2 - Evaluación de Prácticas de Programación
    CriterioEntity criterio4 = new CriterioEntity(rubrica2, "Cumple con los estándares de calidad", 30, 0f, "Se siguen patrones de codificación estándar, aunque algunos métodos no cumplen principios SOLID.", null);
    CriterioEntity criterio5 = new CriterioEntity(rubrica2, "Trabajo en equipo y colaboración", 30, 0f, "El equipo mostró coordinación en tareas comunes, aunque la integración de los módulos presentó conflictos.", null);
    CriterioEntity criterio6 = new CriterioEntity(rubrica2, "Cumple con los plazos establecidos", 40, 0f, "El proyecto fue entregado a tiempo, sin embargo, se detectaron entregas parciales incompletas durante el proceso.", null);

    // Rubrica 3 - Evaluación de Presentación de Arquitectura de Software
    CriterioEntity criterio7 = new CriterioEntity(rubrica3, "Presentación clara de la arquitectura", 30, 0f, "La arquitectura propuesta se explicó con claridad y fue acompañada de diagramas comprensibles.", null);
    CriterioEntity criterio8 = new CriterioEntity(rubrica3, "Documentación arquitectónica", 30, 0f, "La documentación incluye estructuras de capas y patrones aplicados, aunque faltan detalles en la capa de servicios.", null);
    CriterioEntity criterio9 = new CriterioEntity(rubrica3, "Justificación de decisiones técnicas", 40, 0f, "Se argumentó correctamente el uso de microservicios, aunque no se detallaron los criterios de escalabilidad.", null);

    // Rubrica 4 - Evaluación de Documentación Técnica
    CriterioEntity criterio10 = new CriterioEntity(rubrica4, "Calidad de la documentación", 30, 0f, "La documentación cumple con los lineamientos del cliente, aunque presenta errores de redacción.", null);
    CriterioEntity criterio11 = new CriterioEntity(rubrica4, "Formato y presentación", 30, 0f, "El documento presenta buena estructura visual, pero se omite el índice y los anexos requeridos.", null);
    CriterioEntity criterio12 = new CriterioEntity(rubrica4, "Contenido técnico", 40, 0f, "Incluye descripciones detalladas de los procesos del sistema, pero no especifica la configuración del entorno de pruebas.", null);

    // Rubrica 5 - Evaluación de Testeo de Software
    CriterioEntity criterio13 = new CriterioEntity(rubrica5, "Cobertura de pruebas", 30, 0f, "Se realizaron pruebas unitarias en la mayoría de los módulos, pero se omiten pruebas de integración.", null);
    CriterioEntity criterio14 = new CriterioEntity(rubrica5, "Claridad en los resultados", 30, 0f, "Los reportes de resultados son entendibles y contienen ejemplos claros, aunque carecen de métricas de rendimiento.", null);
    CriterioEntity criterio15 = new CriterioEntity(rubrica5, "Documentación de pruebas", 40, 0f, "La documentación incluye pasos detallados para ejecutar las pruebas y resultados esperados.", null);

    listaCriterios.addAll(Arrays.asList(
        criterio1, criterio2, criterio3, criterio4, criterio5, criterio6,
        criterio7, criterio8, criterio9, criterio10, criterio11, criterio12,
        criterio13, criterio14, criterio15
    ));

    this.criterioRepository.saveAll(listaCriterios);


    List<NivelEntity> listaNiveles = new ArrayList<>();

    listaNiveles.addAll(Arrays.asList(
        // rubrica1 - criterio1
        new NivelEntity(criterio1, "No se identifican los requisitos fundamentales del proyecto.", "0-1.6"),
        new NivelEntity(criterio1, "Algunos requisitos están identificados, pero faltan elementos clave.", "1.7-3.4"),
        new NivelEntity(criterio1, "Todos los requisitos están claramente definidos y aplicados correctamente.", "3.5-5"),

        // rubrica1 - criterio2
        new NivelEntity(criterio2, "La documentación es confusa y desorganizada.", "0-1.6"),
        new NivelEntity(criterio2, "La documentación es comprensible pero tiene omisiones.", "1.7-3.4"),
        new NivelEntity(criterio2, "La documentación es clara, coherente y completa.", "3.5-5"),

        // rubrica1 - criterio3
        new NivelEntity(criterio3, "La solución propuesta carece de originalidad y enfoque.", "0-1.6"),
        new NivelEntity(criterio3, "La solución muestra intentos de innovación con limitaciones técnicas.", "1.7-3.4"),
        new NivelEntity(criterio3, "La solución es altamente innovadora y técnicamente sólida.", "3.5-5"),

        // rubrica2 - criterio4
        new NivelEntity(criterio4, "No se respetan los estándares básicos de calidad.", "0-1.6"),
        new NivelEntity(criterio4, "Se cumplen parcialmente los estándares establecidos.", "1.7-3.4"),
        new NivelEntity(criterio4, "Se respetan todos los estándares de calidad con precisión.", "3.5-5"),

        // rubrica2 - criterio5
        new NivelEntity(criterio5, "El trabajo en equipo fue deficiente y poco colaborativo.", "0-1.6"),
        new NivelEntity(criterio5, "Hubo colaboración, pero con conflictos o descoordinación.", "1.7-3.4"),
        new NivelEntity(criterio5, "Excelente colaboración y cohesión del equipo de trabajo.", "3.5-5"),

        // rubrica2 - criterio6
        new NivelEntity(criterio6, "Se incumplieron los plazos establecidos sin justificación.", "0-1.6"),
        new NivelEntity(criterio6, "Algunos plazos fueron cumplidos, con retrasos en otros.", "1.7-3.4"),
        new NivelEntity(criterio6, "Todos los plazos fueron respetados rigurosamente.", "3.5-5"),

        // rubrica3 - criterio7
        new NivelEntity(criterio7, "La presentación arquitectónica es confusa o inexistente.", "0-1.6"),
        new NivelEntity(criterio7, "La arquitectura está parcialmente presentada y entendible.", "1.7-3.4"),
        new NivelEntity(criterio7, "La arquitectura está bien estructurada y claramente presentada.", "3.5-5"),

        // rubrica3 - criterio8
        new NivelEntity(criterio8, "La documentación de arquitectura es incompleta o ambigua.", "0-1.6"),
        new NivelEntity(criterio8, "La documentación cubre aspectos clave, pero hay omisiones.", "1.7-3.4"),
        new NivelEntity(criterio8, "La documentación es exhaustiva, clara y bien organizada.", "3.5-5"),

        // rubrica3 - criterio9
        new NivelEntity(criterio9, "No se justifica la elección de la arquitectura usada.", "0-1.6"),
        new NivelEntity(criterio9, "La justificación es vaga o poco fundamentada.", "1.7-3.4"),
        new NivelEntity(criterio9, "Las decisiones técnicas están sólidamente justificadas.", "3.5-5"),

        // rubrica4 - criterio10
        new NivelEntity(criterio10, "La documentación es deficiente y presenta errores críticos.", "0-1.6"),
        new NivelEntity(criterio10, "La documentación es aceptable pero presenta errores menores.", "1.7-3.4"),
        new NivelEntity(criterio10, "Documentación técnica de alta calidad sin errores significativos.", "3.5-5"),

        // rubrica4 - criterio11
        new NivelEntity(criterio11, "Formato desorganizado, sin coherencia visual.", "0-1.6"),
        new NivelEntity(criterio11, "El formato es comprensible pero puede mejorarse.", "1.7-3.4"),
        new NivelEntity(criterio11, "Presentación visual clara, profesional y coherente.", "3.5-5"),

        // rubrica4 - criterio12
        new NivelEntity(criterio12, "Contenido técnico irrelevante o erróneo.", "0-1.6"),
        new NivelEntity(criterio12, "El contenido es correcto pero superficial o incompleto.", "1.7-3.4"),
        new NivelEntity(criterio12, "Contenido técnico riguroso, claro y completo.", "3.5-5"),

        // rubrica5 - criterio13
        new NivelEntity(criterio13, "Pocas pruebas realizadas y de escasa cobertura.", "0-1.6"),
        new NivelEntity(criterio13, "Las pruebas cubren lo esencial pero faltan casos importantes.", "1.7-3.4"),
        new NivelEntity(criterio13, "Amplia cobertura de pruebas con variedad de casos bien definidos.", "3.5-5"),

        // rubrica5 - criterio14
        new NivelEntity(criterio14, "Resultados de pruebas poco claros o mal documentados.", "0-1.6"),
        new NivelEntity(criterio14, "Resultados comprensibles pero sin detalles suficientes.", "1.7-3.4"),
        new NivelEntity(criterio14, "Resultados claramente explicados con evidencia de ejecución.", "3.5-5"),

        // rubrica5 - criterio15
        new NivelEntity(criterio15, "No hay documentación de las pruebas realizadas.", "0-1.6"),
        new NivelEntity(criterio15, "Documentación de pruebas parcial o incompleta.", "1.7-3.4"),
        new NivelEntity(criterio15, "Documentación exhaustiva con pasos, resultados y análisis.", "3.5-5")
    ));

    this.nivelRepository.saveAll(listaNiveles);

    }
}