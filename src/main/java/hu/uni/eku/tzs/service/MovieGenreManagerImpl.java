package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.MovieGenreRepository;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.dao.entity.MovieGenreEntity;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.MovieGenre;
import hu.uni.eku.tzs.service.exceptions.MovieGenreAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieGenreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieGenreManagerImpl implements MovieGenreManager {

    private final MovieGenreRepository movieGenreRepository;

    @Override
    public Collection<MovieGenre> readAll() {
        return movieGenreRepository.findAll(PageRequest.of(0, 100)).stream()
                .map(MovieGenreManagerImpl::convertMovieGenreEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public MovieGenre readById(int id) throws MovieGenreNotFoundException {
        Optional<MovieGenreEntity> entity = movieGenreRepository.findById(id);
        if (entity.isEmpty()) {
            throw new MovieGenreNotFoundException(String.format("Cannot find MovieGenre with this id %s", id));
        }

        return convertMovieGenreEntity2Model(entity.get());
    }

    @Override
    public MovieGenre modify(MovieGenre movieGenre) {
        MovieGenreEntity entity = convertMovieGenreModel2Entity(movieGenre);
        return convertMovieGenreEntity2Model(movieGenreRepository.save(entity));
    }

    @Override
    public void delete(MovieGenre movieGenre) throws MovieGenreNotFoundException {
        movieGenreRepository.delete(convertMovieGenreModel2Entity(movieGenre));
    }

    @Override
    public MovieGenre record(MovieGenre movieGenre) throws MovieGenreAlreadyExistsException {
        if (movieGenreRepository.findById(movieGenre.getMovie().getId()).isPresent()) {
            throw new MovieGenreAlreadyExistsException();
        }
        MovieGenreEntity movieGenreEntity = movieGenreRepository.save(
                MovieGenreEntity.builder()
                        .movie(convertMovieModel2Entity(movieGenre.getMovie()))
                        .genre(movieGenre.getGenre())
                        .build()
        );
        return convertMovieGenreEntity2Model(movieGenreEntity);
    }

    private static MovieGenre convertMovieGenreEntity2Model(MovieGenreEntity movieGenreEntity) {
        return new MovieGenre(
                convertMovieEntity2Model(movieGenreEntity.getMovie()),
                movieGenreEntity.getGenre()
        );
    }

    private static MovieGenreEntity convertMovieGenreModel2Entity(MovieGenre movieGenre) {
        return MovieGenreEntity.builder()
                .movie(convertMovieModel2Entity(movieGenre.getMovie()))
                .genre(movieGenre.getGenre())
                .build();
    }

    private static Movie convertMovieEntity2Model(MovieEntity movieEntity) {
        return new Movie(
                movieEntity.getId(),
                movieEntity.getName(),
                movieEntity.getYear(),
                movieEntity.getRank()
                );
    }

    private static MovieEntity convertMovieModel2Entity(Movie movie) {
        return MovieEntity.builder()
                .id(movie.getId())
                .name(movie.getName())
                .year(movie.getYear())
                .rank(movie.getRank())
                .build();
    }
}
