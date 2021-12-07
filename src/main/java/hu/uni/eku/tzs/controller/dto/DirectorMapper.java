package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Director;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectorMapper {

    DirectorDto director2DirectorDto(Director director);

    Director directorDto2Director(DirectorDto dto);
}
