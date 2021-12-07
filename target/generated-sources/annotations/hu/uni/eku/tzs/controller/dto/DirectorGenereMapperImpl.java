package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.DirectorDto.DirectorDtoBuilder;
import hu.uni.eku.tzs.controller.dto.DirectorGenereDto.DirectorGenereDtoBuilder;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenere;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class DirectorGenereMapperImpl implements DirectorGenereMapper {

    @Override
    public DirectorGenereDto directorGenere2DirectorGenereDto(DirectorGenere directorGenere) {
        if ( directorGenere == null ) {
            return null;
        }

        DirectorGenereDtoBuilder directorGenereDto = DirectorGenereDto.builder();

        directorGenereDto.director( directorToDirectorDto( directorGenere.getDirector() ) );
        directorGenereDto.genre( directorGenere.getGenre() );
        directorGenereDto.prob( directorGenere.getProb() );

        return directorGenereDto.build();
    }

    @Override
    public DirectorGenere directorGenereDto2DirectorGenere(DirectorGenereDto dto) {
        if ( dto == null ) {
            return null;
        }

        DirectorGenere directorGenere = new DirectorGenere();

        directorGenere.setDirector( directorDtoToDirector( dto.getDirector() ) );
        directorGenere.setGenre( dto.getGenre() );
        directorGenere.setProb( dto.getProb() );

        return directorGenere;
    }

    protected DirectorDto directorToDirectorDto(Director director) {
        if ( director == null ) {
            return null;
        }

        DirectorDtoBuilder directorDto = DirectorDto.builder();

        directorDto.id( director.getId() );
        directorDto.firstname( director.getFirstname() );
        directorDto.lastname( director.getLastname() );

        return directorDto.build();
    }

    protected Director directorDtoToDirector(DirectorDto directorDto) {
        if ( directorDto == null ) {
            return null;
        }

        Director director = new Director();

        director.setId( directorDto.getId() );
        director.setFirstname( directorDto.getFirstname() );
        director.setLastname( directorDto.getLastname() );

        return director;
    }
}
