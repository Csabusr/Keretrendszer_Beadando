package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.MovieGenreRepository;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorGenereEntity;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.dao.entity.MovieGenreEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenere;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.MovieGenre;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import hu.uni.eku.tzs.service.exceptions.MovieGenreAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieGenreNotFoundException;
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
class MovieGenreManagerImplTest {

    @Mock
    MovieGenreRepository movieGenreRepository;

    @InjectMocks
    MovieGenreManagerImpl service;

    @Test
    void recordMovieGenreHappyPath() throws MovieGenreAlreadyExistsException {
        // given
        MovieGenre movieGenre = MovieGenreManagerImplTest.TestDataProvider.getOhnDoe();
        MovieGenreEntity movieGenreEntity = MovieGenreManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(movieGenreRepository.findById(any())).thenReturn(Optional.empty());
        when(movieGenreRepository.save(any())).thenReturn(movieGenreEntity);
        // when
        MovieGenre actual = service.record(movieGenre);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(movieGenre);
    }

    @Test
    void recordMovieGenreAlreadyExistsException() throws MovieGenreAlreadyExistsException {
        //given
        MovieGenre movieGenre = MovieGenreManagerImplTest.TestDataProvider.getOhnDoe();
        MovieGenreEntity movieGenreEntity = MovieGenreManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(movieGenreRepository.findById(MovieGenreManagerImplTest.TestDataProvider.getOhnDoe().getMovie().getId()))
                .thenReturn(Optional.ofNullable(movieGenreEntity));
        // when then
        assertThatThrownBy(() -> service.record(movieGenre))
                .isInstanceOf(MovieGenreAlreadyExistsException.class);

    }
    @Test
    void readByIdHappyPath() throws MovieGenreNotFoundException {
        // given
        MovieGenre expected = MovieGenreManagerImplTest.TestDataProvider.getOhnDoe();
        when(movieGenreRepository.findById(MovieGenreManagerImplTest.TestDataProvider.getOhnDoe().getMovie().getId()))
                .thenReturn(Optional.of(MovieGenreManagerImplTest.TestDataProvider.getOhnDoeEntity()));
        // when
        MovieGenre actual = service.readById(MovieGenreManagerImplTest.TestDataProvider.getOhnDoe().getMovie().getId());
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readyByIdMovieGenreNotFoundException() throws MovieGenreNotFoundException {
        // given
        when(movieGenreRepository.findById(MovieGenreManagerImplTest.TestDataProvider.unknownId))
                .thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> service.readById(MovieGenreManagerImplTest.TestDataProvider.unknownId))
                .isInstanceOf(MovieGenreNotFoundException.class)
                .hasMessageContaining(String.valueOf(MovieGenreManagerImplTest.TestDataProvider.unknownId));
    }

    @Test
    void readAllHappyPath() {
        // given
        List<MovieGenreEntity> directorEntities = List.of(
                MovieGenreManagerImplTest.TestDataProvider.getOhnDoeEntity(),
                MovieGenreManagerImplTest.TestDataProvider.getMikeOxlongEntity()
        );
        Page<MovieGenreEntity> page = new PageImpl<>(directorEntities);
        Collection<MovieGenre> expectedCustomers = List.of(
                MovieGenreManagerImplTest.TestDataProvider.getOhnDoe(),
                MovieGenreManagerImplTest.TestDataProvider.getMikeOxlong()
        );
        when(movieGenreRepository.findAll(isA(Pageable.class))).thenReturn(page);
        // when
        Collection<MovieGenre> actualDirectors = service.readAll();
        // then
        assertThat(actualDirectors)
                .usingRecursiveComparison()
                .isEqualTo(expectedCustomers);
    }

    @Test
    void modifyMovieGenreHappyPath() {
        // given
        MovieGenre movieGenre = MovieGenreManagerImplTest.TestDataProvider.getOhnDoe();
        MovieGenreEntity movieGenreEntity = MovieGenreManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(movieGenreRepository.save(movieGenreEntity)).thenReturn(movieGenreEntity);
        // when
        MovieGenre actual = service.modify(movieGenre);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(movieGenre);
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

        public static final MovieGenre testMovieGenre = new MovieGenre(testMovie,"XXX");
        public static final MovieGenreEntity testMovieGenreEntity = MovieGenreEntity.builder()
                .movie(testMovieEntity)
                .genre(testMovieGenre.getGenre())
                .build();

        public static MovieGenre getOhnDoe() {
            return testMovieGenre;
        }

        public static MovieGenreEntity getOhnDoeEntity() {
            return MovieGenreEntity.builder()
                    .movie(testMovieEntity)
                    .genre(testMovieGenre.getGenre())
                    .build();
        }

        public static final Movie testMovie1 = new Movie(2,"InterSpar1",1983,2.223f);
        public static final MovieEntity testMovieEntity1 = MovieEntity.builder()
                .id(testMovie1.getId())
                .name(testMovie1.getName())
                .year(testMovie1.getYear())
                .rank(testMovie1.getRank())
                .build();

        public static final MovieGenre testMovieGenre1 = new MovieGenre(testMovie1,"Document");
        public static final MovieGenreEntity testMovieGenreEntity1 = MovieGenreEntity.builder()
                .movie(testMovieEntity1)
                .genre(testMovieGenre1.getGenre())
                .build();

        public static MovieGenre getMikeOxlong() {
            return testMovieGenre1;
        }

        public static MovieGenreEntity getMikeOxlongEntity() {
            return MovieGenreEntity.builder()
                    .movie(testMovieEntity1)
                    .genre(testMovieGenre1.getGenre())
                    .build();
        }
    }
}