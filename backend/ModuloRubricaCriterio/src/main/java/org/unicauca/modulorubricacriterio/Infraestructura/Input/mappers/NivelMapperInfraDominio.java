package org.unicauca.modulorubricacriterio.Infraestructura.Input.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtoPeticion.NivelDTOPeticion;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.dtorespuesta.NivelDTORespuesta;

@Mapper(componentModel = "spring")
public interface NivelMapperInfraDominio {

    Nivel mapNivelDtoaNivel(NivelDTOPeticion nivelDTO);/*Mapea de NivelDtoPeticion a Nivel */

    NivelDTORespuesta mapNivelToDto(Nivel nivel);/*Mapea de Nivel a NivelDTORespuesta */

    List<NivelDTORespuesta> mapNivelListToDtoList(List<Nivel> niveles);/*Mapea listas de Nivel a una lista de NivelDTORespuesta */

}
