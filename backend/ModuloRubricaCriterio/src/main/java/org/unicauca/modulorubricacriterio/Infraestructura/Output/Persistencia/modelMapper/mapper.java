package org.unicauca.modulorubricacriterio.Infraestructura.Output.Persistencia.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class mapper {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
