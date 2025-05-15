package org.unicauca.modulorubricacriterio;

import java.util.ArrayList;
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