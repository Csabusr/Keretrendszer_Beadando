package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.service.exceptions.DirectorGenereAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereNotFoundException;

import java.util.Collection;

public interface DirectorGenereManager {

    Collection<hu.uni.eku.tzs.model.DirectorGenere> readAll();

    hu.uni.eku.tzs.model.DirectorGenere readById(int id) throws DirectorGenereNotFoundException;

    hu.uni.eku.tzs.model.DirectorGenere modify(hu.uni.eku.tzs.model.DirectorGenere directorGenere);

    void delete(hu.uni.eku.tzs.model.DirectorGenere directorGenere) throws DirectorGenereNotFoundException;

    hu.uni.eku.tzs.model.DirectorGenere record(hu.uni.eku.tzs.model.DirectorGenere directorGenere)
            throws DirectorGenereAlreadyExistsException;
}
