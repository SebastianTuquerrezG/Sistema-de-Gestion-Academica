package org.unicauca.modulorubricacriterio.Infraestructura.Input.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion.CriterioDTOPeticion;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta.CriterioDTORespuesta;

@Mapper(componentModel = "spring")
public interface CriterioMapperInfraDominio {

    Criterio mapDtoaCriterio(CriterioDTOPeticion criterioDTO);/*Mapea de CriterioDtoPeticion a Criterio */

    CriterioDTORespuesta mapCriterioToDto(Criterio criterio);/*Mapea de Criterio a CriterioDTORespuesta */

    List<CriterioDTORespuesta> mapCriterioListToDtoList(List<Criterio> criterios);/*Mapea listas de Criterio a una lista de CriterioDTORespuesta */


}
