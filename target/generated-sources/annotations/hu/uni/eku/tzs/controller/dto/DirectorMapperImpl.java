package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.DirectorDto.DirectorDtoBuilder;
import hu.uni.eku.tzs.model.Director;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class DirectorMapperImpl implements DirectorMapper {

    @Override
    public DirectorDto director2DirectorDto(Director director) {
        if ( director == null ) {
            return null;
        }

        DirectorDtoBuilder directorDto = DirectorDto.builder();

        directorDto.id( director.getId() );
        directorDto.firstname( director.getFirstname() );
        directorDto.lastname( director.getLastname() );

        return directorDto.build();
    }

    @Override
    public Director directorDto2Director(DirectorDto dto) {
        if ( dto == null ) {
            return null;
        }

        Director director = new Director();

        director.setId( dto.getId() );
        director.setFirstname( dto.getFirstname() );
        director.setLastname( dto.getLastname() );

        return director;
    }
}
