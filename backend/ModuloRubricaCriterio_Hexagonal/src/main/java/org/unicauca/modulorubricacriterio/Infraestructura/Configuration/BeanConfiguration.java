package org.unicauca.modulorubricacriterio.Infraestructura.Configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDCriterioPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDNivelPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionCriterioAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionNivelAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionRubricasAdapter;

@Configuration
public class BeanConfiguration {
    /*Intancias de los adaptadores de la capa de dominio
    para la gestion de rubricas, criterios y niveles, necesarias en la infraestructura*/

    @Bean
    public GestionRubricasAdapter crearObjGestorRubricas(IConectorBDRubricaPort conectorBDRubricaPort) {
        return new GestionRubricasAdapter(conectorBDRubricaPort);
    }

    @Bean
    public GestionCriterioAdapter crearObjGestorCriterios(IConectorBDCriterioPort conectorBDCriterioPort) {
        return new GestionCriterioAdapter(conectorBDCriterioPort);
    }

    @Bean
    public GestionNivelAdapter crearObjGestorNiveles(IConectorBDNivelPort conectorBDNivelPort) {
        return new GestionNivelAdapter(conectorBDNivelPort);
    }

    @Bean
    public CommandLineRunner printMappings(ApplicationContext ctx) {
        return args -> {
            RequestMappingHandlerMapping mapping =
                (RequestMappingHandlerMapping) ctx.getBean("requestMappingHandlerMapping");

            mapping.getHandlerMethods().forEach((key, value) -> {
                System.out.println(key + " => " + value);
            });
        };
    }

}