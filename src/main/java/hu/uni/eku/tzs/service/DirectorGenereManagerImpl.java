package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorGenereRepository;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorGenereEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenere;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorGenereManagerImpl implements DirectorGenereManager {

    private final DirectorGenereRepository directorGenereRepository;

    @Override
    public Collection<DirectorGenere> readAll() {

        return directorGenereRepository.findAll(PageRequest.of(0, 100)).stream()
                .map(DirectorGenereManagerImpl::convertDirectorGenereEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public DirectorGenere readById(int id) throws DirectorGenereNotFoundException {
        Optional<DirectorGenereEntity> entity = directorGenereRepository.findById(id);
        if (entity.isEmpty()) {
            throw new DirectorGenereNotFoundException(String.format("Cannot find DirectorGenre with this id %s", id));
        }

        return convertDirectorGenereEntity2Model(entity.get());
    }

    @Override
    public DirectorGenere modify(DirectorGenere directorGenere) {
        DirectorGenereEntity entity = convertDirectorGenereModel2Entity(directorGenere);
        return convertDirectorGenereEntity2Model(directorGenereRepository.save(entity));

    }

    @Override
    public void delete(DirectorGenere directorGenere) throws DirectorGenereNotFoundException {
        directorGenereRepository.delete(convertDirectorGenereModel2Entity(directorGenere));
    }

    @Override
    public DirectorGenere record(DirectorGenere directorGenere) throws DirectorGenereAlreadyExistsException {
        if (directorGenereRepository.findById(directorGenere.getDirector().getId()).isPresent()) {
            throw new DirectorGenereAlreadyExistsException();
        }
        DirectorGenereEntity directorGenereEntity = directorGenereRepository.save(
                DirectorGenereEntity.builder()
                        .director(convertDirectorModel2Entity(directorGenere.getDirector()))
                        .genere(directorGenere.getGenre())
                        .prob(directorGenere.getProb())
                        .build()
        );
        return convertDirectorGenereEntity2Model(directorGenereEntity);
    }

    private static Director convertDirectorEntity2Model(DirectorEntity directorEntity) {
        return new Director(
                directorEntity.getId(),
                directorEntity.getFirstname(),
                directorEntity.getLastname()
        );
    }

    private static DirectorEntity convertDirectorModel2Entity(Director director) {
        return DirectorEntity.builder()
                .id(director.getId())
                .firstname(director.getFirstname())
                .lastname(director.getLastname())
                .build();
    }

    private static DirectorGenere convertDirectorGenereEntity2Model(DirectorGenereEntity entity) {
        return new DirectorGenere(
                convertDirectorEntity2Model(entity.getDirector()),
                entity.getGenere(),
                entity.getProb());
    }

    private static DirectorGenereEntity convertDirectorGenereModel2Entity(DirectorGenere directorGenere) {
        return DirectorGenereEntity.builder()
                .director(convertDirectorModel2Entity(directorGenere.getDirector()))
                .genere(directorGenere.getGenre())
                .prob(directorGenere.getProb())
                .build();
    }
}
