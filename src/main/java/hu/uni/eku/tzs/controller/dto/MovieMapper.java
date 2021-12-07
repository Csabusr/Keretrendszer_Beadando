package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto movie2MovieDto(Movie movie);

    Movie movieDto2Movie(MovieDto dto);

}
