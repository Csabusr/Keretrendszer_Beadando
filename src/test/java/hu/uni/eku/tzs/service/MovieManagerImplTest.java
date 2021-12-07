package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorRepository;
import hu.uni.eku.tzs.dao.MovieRepository;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.dao.entity.MovieGenreEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.MovieGenre;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieGenreAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieGenreNotFoundException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class MovieManagerImplTest {

    @Mock
    MovieRepository movieRepository;

    @InjectMocks
    MovieManagerImpl service;

    @Test
    void recordMovieHappyPath() throws MovieAlreadyExistsException {
        // given
        Movie movie = MovieManagerImplTest.TestDataProvider.getOhnDoe();
        MovieEntity movieEntity = MovieManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(movieRepository.findById(any())).thenReturn(Optional.empty());
        when(movieRepository.save(any())).thenReturn(movieEntity);
        // when
        Movie actual = service.record(movie);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(movie);
    }

    @Test
    void recordMovieAlreadyExistsException() throws MovieGenreAlreadyExistsException {
        //given
        Movie movie = MovieManagerImplTest.TestDataProvider.getOhnDoe();
        MovieEntity movieEntity = MovieManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(movieRepository.findById(MovieManagerImplTest.TestDataProvider.getOhnDoe().getId()))
                .thenReturn(Optional.ofNullable(movieEntity));
        // when then
        assertThatThrownBy(() -> service.record(movie))
                .isInstanceOf(MovieAlreadyExistsException.class);

    }
    @Test
    void readByIdHappyPath() throws MovieNotFoundException {
        // given
        Movie expected = MovieManagerImplTest.TestDataProvider.getOhnDoe();
        when(movieRepository.findById(MovieManagerImplTest.TestDataProvider.getOhnDoe().getId()))
                .thenReturn(Optional.of(MovieManagerImplTest.TestDataProvider.getOhnDoeEntity()));
        // when
        Movie actual = service.readById(MovieManagerImplTest.TestDataProvider.getOhnDoe().getId());
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readyByIdMovieNotFoundException() throws MovieNotFoundException {
        // given
        when(movieRepository.findById(MovieManagerImplTest.TestDataProvider.unknownId))
                .thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> service.readById(MovieManagerImplTest.TestDataProvider.unknownId))
                .isInstanceOf(MovieNotFoundException.class)
                .hasMessageContaining(String.valueOf(MovieManagerImplTest.TestDataProvider.unknownId));
    }

    @Test
    void readAllHappyPath() {
        // given
        List<MovieEntity> movieEntities = List.of(
                MovieManagerImplTest.TestDataProvider.getOhnDoeEntity(),
                MovieManagerImplTest.TestDataProvider.getMikeOxlongEntity()
        );
        Page<MovieEntity> page = new PageImpl<>(movieEntities);
        Collection<Movie> expectedMovies = List.of(
                MovieManagerImplTest.TestDataProvider.getOhnDoe(),
                MovieManagerImplTest.TestDataProvider.getMikeOxlong()
        );
        when(movieRepository.findAll(isA(Pageable.class))).thenReturn(page);
        // when
        Collection<Movie> actualMovie = service.readAll();
        // then
        assertThat(actualMovie)
                .usingRecursiveComparison()
                .isEqualTo(expectedMovies);
    }

    @Test
    void modifyMovieHappyPath() {
        // given
        Movie movie = MovieManagerImplTest.TestDataProvider.getOhnDoe();
        MovieEntity movieEntity = MovieManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(movieRepository.save(movieEntity)).thenReturn(movieEntity);
        // when
        Movie actual = service.modify(movie);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(movie);
    }

    private static class TestDataProvider {

        public static final int unknownId = -1;

        public static final Movie testMovie = new Movie(1,"InterSpar",1982,2.233f);
        public static final MovieEntity testMovieEntity = MovieEntity.builder()
                .id(testMovie.getId())
                .name(testMovie.getName())
                .year(testMovie.getYear())
                .rank(testMovie.getRank())
                .build();


        public static Movie getOhnDoe() {
            return testMovie;
        }

        public static MovieEntity getOhnDoeEntity() {
            return MovieEntity.builder()
                    .id(testMovie.getId())
                    .name(testMovie.getName())
                    .year(testMovie.getYear())
                    .rank(testMovie.getRank())
                    .build();
        }

        public static final Movie testMovie1 = new Movie(2,"InterSpar1",1983,2.223f);
        public static final MovieEntity testMovieEntity1 = MovieEntity.builder()
                .id(testMovie1.getId())
                .name(testMovie1.getName())
                .year(testMovie1.getYear())
                .rank(testMovie1.getRank())
                .build();

        public static Movie getMikeOxlong() {
            return testMovie1;
        }

        public static MovieEntity getMikeOxlongEntity() {
            return MovieEntity.builder()
                    .id(testMovie1.getId())
                    .name(testMovie1.getName())
                    .year(testMovie1.getYear())
                    .rank(testMovie1.getRank())
                    .build();
        }
    }
}