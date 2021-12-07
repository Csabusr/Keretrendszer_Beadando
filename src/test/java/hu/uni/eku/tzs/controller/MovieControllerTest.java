package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.DirectorDto;
import hu.uni.eku.tzs.controller.dto.DirectorMapper;
import hu.uni.eku.tzs.controller.dto.MovieDto;
import hu.uni.eku.tzs.controller.dto.MovieMapper;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.DirectorManager;
import hu.uni.eku.tzs.service.MovieManager;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerTest {

    @Mock
    private MovieManager movieManager;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(movieManager.readAll()).thenReturn(List.of(MovieControllerTest.TestDataProvider.getOhnDoe()));
        when(movieMapper.movie2MovieDto(any())).thenReturn(MovieControllerTest.TestDataProvider.getOhnDoeDto());
        Collection<MovieDto> expected = List.of(MovieControllerTest.TestDataProvider.getOhnDoeDto());
        // when
        Collection<MovieDto> actual = controller.readAllDirectors();
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createMovieHappyPath() throws MovieAlreadyExistsException {
        // given
        Movie johnDoe = MovieControllerTest.TestDataProvider.getOhnDoe();
        MovieDto johnDoeDto = MovieControllerTest.TestDataProvider.getOhnDoeDto();
        when(movieMapper.movieDto2Movie(johnDoeDto)).thenReturn(johnDoe);
        when(movieManager.record(johnDoe)).thenReturn(johnDoe);
        when(movieMapper.movie2MovieDto(johnDoe)).thenReturn(johnDoeDto);
        // when
        MovieDto actual = controller.create(johnDoeDto);
        // then
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(johnDoeDto);
    }

    @Test
    void createMovieThrowsCustomerAlreadyExistsException() throws MovieAlreadyExistsException {
        // given
        Movie johnDoe = MovieControllerTest.TestDataProvider.getOhnDoe();
        MovieDto johnDoeDto = MovieControllerTest.TestDataProvider.getOhnDoeDto();
        when(movieMapper.movieDto2Movie(johnDoeDto)).thenReturn(johnDoe);
        when(movieManager.record(johnDoe)).thenThrow(new MovieAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> controller.create(johnDoeDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws MovieNotFoundException {
        // given
        Movie johnDoe = MovieControllerTest.TestDataProvider.getOhnDoe();
        when(movieManager.readById(MovieControllerTest.TestDataProvider.getOhnDoe().getId())).thenReturn(johnDoe);
        doNothing().when(movieManager).delete(johnDoe);
        // when
        controller.delete(MovieControllerTest.TestDataProvider.getOhnDoe().getId());
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenCustomerNotFound() throws MovieNotFoundException {
        // given
        final int notFoundCustomerId = MovieControllerTest.TestDataProvider.unknownId;
        doThrow(new MovieNotFoundException(String.format("Cannot find movie with ID %d", notFoundCustomerId)))
                .when(movieManager).readById(notFoundCustomerId);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundCustomerId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int unknownId = -1;

        public static final Movie testMovie = new Movie(1,"InterSpar",1982,2.233f);
        public static final MovieDto testMovieDto = MovieDto.builder()
                .id(testMovie.getId())
                .name(testMovie.getName())
                .year(testMovie.getYear())
                .rank(testMovie.getRank())
                .build();


        public static Movie getOhnDoe() {
            return testMovie;
        }

        public static MovieDto getOhnDoeDto() {
            return MovieDto.builder()
                    .id(testMovie.getId())
                    .name(testMovie.getName())
                    .year(testMovie.getYear())
                    .rank(testMovie.getRank())
                    .build();
        }

        public static final Movie testMovie1 = new Movie(2,"InterSpar1",1983,2.223f);
        public static final MovieDto testMovieDto1 = MovieDto.builder()
                .id(testMovie1.getId())
                .name(testMovie1.getName())
                .year(testMovie1.getYear())
                .rank(testMovie1.getRank())
                .build();

        public static Movie getMikeOxlong() {
            return testMovie1;
        }

        public static MovieDto getMikeOxlongDto() {
            return MovieDto.builder()
                    .id(testMovie1.getId())
                    .name(testMovie1.getName())
                    .year(testMovie1.getYear())
                    .rank(testMovie1.getRank())
                    .build();
        }
    }
}