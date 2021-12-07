package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;

import java.util.Collection;

public interface MovieManager {
    Collection<Movie> readAll();

    hu.uni.eku.tzs.model.Movie readById(int id) throws MovieNotFoundException;

    hu.uni.eku.tzs.model.Movie modify(hu.uni.eku.tzs.model.Movie movie);

    void delete(hu.uni.eku.tzs.model.Movie movie) throws MovieNotFoundException;

    hu.uni.eku.tzs.model.Movie record(hu.uni.eku.tzs.model.Movie movie)
            throws MovieAlreadyExistsException;
}
