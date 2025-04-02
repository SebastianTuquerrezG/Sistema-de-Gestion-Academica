package org.unicauca.modulorubricacriterio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.CriterioEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.NivelEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.entity.RubricaEntity;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.CriterioRepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.NivelRepository;
import org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.repository.RubricaRepository;

@SpringBootApplication
public class ModuloRubricaCriterioApplication implements CommandLineRunner {

    
    @Autowired
    private RubricaRepository objRubricaRepository;

    @Autowired
    private CriterioRepository criterioRepository;

    @Autowired
    private NivelRepository nivelRepository;

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
        List<RubricaEntity> listaRubricas = new ArrayList<>();
        RubricaEntity rubrica1 = new RubricaEntity("Evaluación de Proyecto de Software", "Ingeniería de Software III", 3, "Objetivo 1", null, EstadosEnum.ACTIVO);
        RubricaEntity rubrica2 = new RubricaEntity("Evaluación de Prácticas de Programación", "Estructura de Datos", 3, "Objetivo 2", null, EstadosEnum.INACTIVO);
        RubricaEntity rubrica3 = new RubricaEntity("Evaluación de Presentación de Arquitectura de Software", "Ingeniería de Software I", 3, "Objetivo 3", null, EstadosEnum.ACTIVO);
        RubricaEntity rubrica4 = new RubricaEntity("Evaluación de Documentación Técnica", "Programación Orientada a Objetos", 3, "Objetivo 4", null, EstadosEnum.INACTIVO);
        RubricaEntity rubrica5 = new RubricaEntity("Evaluación de Testeo de Software", "Calidad de Software", 3, "Objetivo 5", null, EstadosEnum.ACTIVO);

        listaRubricas.add(rubrica1);
        listaRubricas.add(rubrica2);
        listaRubricas.add(rubrica3);
        listaRubricas.add(rubrica4);
        listaRubricas.add(rubrica5);
        
        this.objRubricaRepository.saveAll(listaRubricas);

        List<CriterioEntity> listaCriterios = new ArrayList<>();
        CriterioEntity criterio1 = new CriterioEntity(rubrica1, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 1", null);
        CriterioEntity criterio2 = new CriterioEntity(rubrica5, "Cumple con los estándares de calidad", 0.3f, 0f, "Comentario 2", null);
        CriterioEntity criterio3 = new CriterioEntity(rubrica2, "Cumple con los plazos establecidos", 0.4f, 0f, "Comentario 3", null);
        CriterioEntity criterio4 = new CriterioEntity(rubrica2, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 4", null);
        CriterioEntity criterio5 = new CriterioEntity(rubrica1, "Cumple con los estándares de calidad", 0.3f, 0f, "Comentario 5", null);
        CriterioEntity criterio6 = new CriterioEntity(rubrica2, "Cumple con los plazos establecidos", 0.4f, 0f, "Comentario 6", null);
        CriterioEntity criterio7 = new CriterioEntity(rubrica4, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 7", null);
        CriterioEntity criterio8 = new CriterioEntity(rubrica3, "Cumple con los estándares de calidad", 0.3f, 0f, "Comentario 8", null);
        CriterioEntity criterio9 = new CriterioEntity(rubrica3, "Cumple con los plazos establecidos", 0.4f, 0f, "Comentario 9", null);
        CriterioEntity criterio10 = new CriterioEntity(rubrica4, "Cumple con los requisitos del proyecto", 0.3f, 0f, "Comentario 10", null);

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
        NivelEntity nivel1 = new NivelEntity(criterio1, "Nivel 1", "0-1");
        NivelEntity nivel2 = new NivelEntity(criterio1, "Nivel 2", "1-2");
        NivelEntity nivel3 = new NivelEntity(criterio1, "Nivel 3", "2-3");
        NivelEntity nivel4 = new NivelEntity(criterio2, "Nivel 1", "3-4");
        NivelEntity nivel5 = new NivelEntity(criterio2, "Nivel 2", "4-5");

        listaNiveles.add(nivel1);
        listaNiveles.add(nivel2);
        listaNiveles.add(nivel3);
        listaNiveles.add(nivel4);
        listaNiveles.add(nivel5);
        this.nivelRepository.saveAll(listaNiveles);
        
        
    }
}
