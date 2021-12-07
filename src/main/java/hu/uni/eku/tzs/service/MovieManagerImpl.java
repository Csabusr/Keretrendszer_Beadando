package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.MovieRepository;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieManagerImpl implements MovieManager {

    private final MovieRepository movieRepository;

    @Override
    public Collection<Movie> readAll() {
        return movieRepository.findAll(PageRequest.of(0, 100)).stream()
                .map(MovieManagerImpl::convertMovieEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Movie readById(int id) throws MovieNotFoundException {
        Optional<MovieEntity> entity = movieRepository.findById(id);
        if (entity.isEmpty()) {
            throw new MovieNotFoundException(String.format("Cannot find Movie with this id %s", id));
        }

        return convertMovieEntity2Model(entity.get());
    }

    @Override
    public Movie modify(Movie movie) {
        MovieEntity entity = convertMovieModel2Entity(movie);
        return convertMovieEntity2Model(movieRepository.save(entity));
    }

    @Override
    public void delete(Movie movie) throws MovieNotFoundException {
        movieRepository.delete(convertMovieModel2Entity(movie));
    }

    @Override
    public Movie record(Movie movie) throws MovieAlreadyExistsException {
        if (movieRepository.findById(movie.getId()).isPresent()) {
            throw new MovieAlreadyExistsException();
        }
        MovieEntity movieEntity = movieRepository.save(
                MovieEntity.builder()
                        .id(movie.getId())
                        .name(movie.getName())
                        .year(movie.getYear())
                        .rank(movie.getRank())
                        .build()
        );
        return convertMovieEntity2Model(movieEntity);
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
