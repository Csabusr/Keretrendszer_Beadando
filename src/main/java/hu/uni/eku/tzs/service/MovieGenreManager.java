package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.MovieGenre;
import hu.uni.eku.tzs.service.exceptions.MovieGenreAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieGenreNotFoundException;

import java.util.Collection;

public interface MovieGenreManager {

    Collection<MovieGenre> readAll();

    MovieGenre readById(int id) throws MovieGenreNotFoundException;

    MovieGenre modify(MovieGenre movieGenre);

    void delete(MovieGenre movieGenre) throws MovieGenreNotFoundException;

    MovieGenre record(MovieGenre movieGenre) throws MovieGenreAlreadyExistsException;
}
