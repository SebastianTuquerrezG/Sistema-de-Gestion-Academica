package org.unicauca.modulorubricacriterio;

// import java.util.ArrayList;
// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.unicauca.modulorubricacriterio.AccesoADatos.model.CriterioEntity;
// import org.unicauca.modulorubricacriterio.AccesoADatos.model.NivelEntity;
// import org.unicauca.modulorubricacriterio.AccesoADatos.model.RubricaEntity;
import org.unicauca.modulorubricacriterio.AccesoADatos.repository.CriterioRepository;
import org.unicauca.modulorubricacriterio.AccesoADatos.repository.NivelRepository;
import org.unicauca.modulorubricacriterio.AccesoADatos.repository.RubricaRepository;
// import org.unicauca.modulorubricacriterio.Fachada.validacionEstados.EstadosEnum;

@SpringBootApplication
public class ModuloRubricaCriterioApplication {

    @Autowired
    private RubricaRepository objRubricaRepository;

    @Autowired
    private CriterioRepository criterioRepository;

    @Autowired
    private NivelRepository nivelRepository;

    public static void main(String[] args) {
        SpringApplication.run(ModuloRubricaCriterioApplication.class, args);
    }

    // @Override
	// public void run(String... args) throws Exception {
	// 	cargarDatos();
	// }

    
    // private void cargarDatos(){
    //     List<RubricaEntity> listaRubricas = new ArrayList<>();
    //     RubricaEntity rubrica1 = new RubricaEntity(1L, "Evaluación de Proyecto de Software", "Ingeniería de Software III", 3, "Objetivo 1", null, EstadosEnum.ACTIVO);
    //     RubricaEntity rubrica2 = new RubricaEntity(2L, "Evaluación de Prácticas de Programación", "Estructura de Datos", 3, "Objetivo 2", null, EstadosEnum.INACTIVO);
    //     RubricaEntity rubrica3 = new RubricaEntity(3L, "Evaluación de Presentación de Arquitectura de Software", "Ingeniería de Software I", 3, "Objetivo 3", null, EstadosEnum.ACTIVO);
    //     RubricaEntity rubrica4 = new RubricaEntity(4L, "Evaluación de Documentación Técnica", "Programación Orientada a Objetos", 3, "Objetivo 4", null, EstadosEnum.INACTIVO);
    //     RubricaEntity rubrica5 = new RubricaEntity(5L, "Evaluación de Testeo de Software", "Calidad de Software", 3, "Objetivo 5", null, EstadosEnum.ACTIVO);

    //     listaRubricas.add(rubrica1);
    //     listaRubricas.add(rubrica2);
    //     listaRubricas.add(rubrica3);
    //     listaRubricas.add(rubrica4);
    //     listaRubricas.add(rubrica5);
        
    //     this.objRubricaRepository.saveAll(listaRubricas);

    //     List<CriterioEntity> listaCriterios = new ArrayList<>();
    //     CriterioEntity criterio1 = new CriterioEntity(1L, rubrica1, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 1", null);
    //     CriterioEntity criterio2 = new CriterioEntity(2L, rubrica5, "Cumple con los estándares de calidad", 0.3f, 0f, "Comentario 2", null);
    //     CriterioEntity criterio3 = new CriterioEntity(3L, rubrica2, "Cumple con los plazos establecidos", 0.4f, 0f, "Comentario 3", null);
    //     CriterioEntity criterio4 = new CriterioEntity(4L, rubrica2, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 4", null);
    //     CriterioEntity criterio5 = new CriterioEntity(5L, rubrica1, "Cumple con los estándares de calidad", 0.3f, 0f, "Comentario 5", null);
    //     CriterioEntity criterio6 = new CriterioEntity(6L, rubrica2, "Cumple con los plazos establecidos", 0.4f, 0f, "Comentario 6", null);
    //     CriterioEntity criterio7 = new CriterioEntity(7L, rubrica4, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 7", null);
    //     CriterioEntity criterio8 = new CriterioEntity(8L, rubrica3, "Cumple con los estándares de calidad", 0.3f, 0f, "Comentario 8", null);
    //     CriterioEntity criterio9 = new CriterioEntity(9L, rubrica3, "Cumple con los plazos establecidos", 0.4f, 0f, "Comentario 9", null);
    //     CriterioEntity criterio10 = new CriterioEntity(10L, rubrica4, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 10", null);

    //     listaCriterios.add(criterio1);
    //     listaCriterios.add(criterio2);
    //     listaCriterios.add(criterio3);
    //     listaCriterios.add(criterio4);
    //     listaCriterios.add(criterio5);
    //     listaCriterios.add(criterio6);
    //     listaCriterios.add(criterio7);
    //     listaCriterios.add(criterio8);
    //     listaCriterios.add(criterio9);
    //     listaCriterios.add(criterio10);
    //     this.criterioRepository.saveAll(listaCriterios);

    //     // Crear niveles y asociarlos a los criterios
    //     List<NivelEntity> listaNiveles = new ArrayList<>();
    //     NivelEntity nivel1 = new NivelEntity(1L, criterio1, "Nivel 1", "0-1");
    //     NivelEntity nivel2 = new NivelEntity(2L, criterio1, "Nivel 2", "1-2");
    //     NivelEntity nivel3 = new NivelEntity(3L, criterio1, "Nivel 3", "2-3");
    //     NivelEntity nivel4 = new NivelEntity(4L, criterio2, "Nivel 1", "3-4");
    //     NivelEntity nivel5 = new NivelEntity(5L, criterio2, "Nivel 2", "4-5");

    //     listaNiveles.add(nivel1);
    //     listaNiveles.add(nivel2);
    //     listaNiveles.add(nivel3);
    //     listaNiveles.add(nivel4);
    //     listaNiveles.add(nivel5);
    //     this.nivelRepository.saveAll(listaNiveles);
        
        
    // }
}
