package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.MovieDto.MovieDtoBuilder;
import hu.uni.eku.tzs.model.Movie;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public MovieDto movie2MovieDto(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieDtoBuilder movieDto = MovieDto.builder();

        movieDto.id( movie.getId() );
        movieDto.name( movie.getName() );
        movieDto.year( movie.getYear() );
        movieDto.rank( movie.getRank() );

        return movieDto.build();
    }

    @Override
    public Movie movieDto2Movie(MovieDto dto) {
        if ( dto == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setId( dto.getId() );
        movie.setName( dto.getName() );
        movie.setYear( dto.getYear() );
        movie.setRank( dto.getRank() );

        return movie;
    }
}
