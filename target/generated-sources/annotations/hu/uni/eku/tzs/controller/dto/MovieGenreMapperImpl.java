package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.MovieDto.MovieDtoBuilder;
import hu.uni.eku.tzs.controller.dto.MovieGenreDto.MovieGenreDtoBuilder;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.MovieGenre;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class MovieGenreMapperImpl implements MovieGenreMapper {

    @Override
    public MovieGenreDto movieGenre2MovieGenreDto(MovieGenre movieGenre) {
        if ( movieGenre == null ) {
            return null;
        }

        MovieGenreDtoBuilder movieGenreDto = MovieGenreDto.builder();

        movieGenreDto.movie( movieToMovieDto( movieGenre.getMovie() ) );
        movieGenreDto.genre( movieGenre.getGenre() );

        return movieGenreDto.build();
    }

    @Override
    public MovieGenre movieGenreDto2MovieGenre(MovieGenreDto dto) {
        if ( dto == null ) {
            return null;
        }

        MovieGenre movieGenre = new MovieGenre();

        movieGenre.setMovie( movieDtoToMovie( dto.getMovie() ) );
        movieGenre.setGenre( dto.getGenre() );

        return movieGenre;
    }

    protected MovieDto movieToMovieDto(Movie movie) {
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

    protected Movie movieDtoToMovie(MovieDto movieDto) {
        if ( movieDto == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setId( movieDto.getId() );
        movie.setName( movieDto.getName() );
        movie.setYear( movieDto.getYear() );
        movie.setRank( movieDto.getRank() );

        return movie;
    }
}
