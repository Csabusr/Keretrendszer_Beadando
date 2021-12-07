package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;

import java.util.Collection;

public interface DirectorManager {

    Collection<Director> readAll();

    Director readById(int id) throws DirectorNotFoundException;

    Director modify(Director director);

    void delete(Director director) throws DirectorNotFoundException;

    Director record(Director director) throws DirectorAlreadyExistsException;
}
