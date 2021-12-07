package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.DirectorGenere;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorGenereMapper {

    DirectorGenereDto directorGenere2DirectorGenereDto(DirectorGenere directorGenere);

    DirectorGenere directorGenereDto2DirectorGenere(DirectorGenereDto dto);
}
