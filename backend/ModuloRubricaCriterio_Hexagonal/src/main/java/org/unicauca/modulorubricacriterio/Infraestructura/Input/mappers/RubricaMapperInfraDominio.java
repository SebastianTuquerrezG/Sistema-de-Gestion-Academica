package org.unicauca.modulorubricacriterio.Infraestructura.Input.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion.RubricaDTOPeticion;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta.RubricaDTORespuesta;

@Mapper(componentModel = "spring")
public interface RubricaMapperInfraDominio {

    Rubrica mapDtoaRubrica(RubricaDTOPeticion rubricaDTO);/*Mapea de RubricaDtoPeticion a Rúbrica */

    RubricaDTORespuesta mapRubricaToDto(Rubrica rubrica);/*Mapea de Rúbrica a RúbricaDTORespuesta */

    List<RubricaDTORespuesta> mapRubricaListToDtoList(List<Rubrica> rubricas);/*Mapea listas de Rúbrica a una lista de RúbricaDTORespuesta */
}
