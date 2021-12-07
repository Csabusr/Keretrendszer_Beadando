package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorRepository;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorManagerImpl implements DirectorManager {

    private final DirectorRepository directorRepository;

    @Override
    public Collection<Director> readAll() {
        return directorRepository.findAll(PageRequest.of(0, 100)).stream()
                .map(DirectorManagerImpl::convertDirectorEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Director readById(int id) throws DirectorNotFoundException {
        Optional<DirectorEntity> entity = directorRepository.findById(id);
        if (entity.isEmpty()) {
            throw new DirectorNotFoundException(String.format("Cannot find Director with this id %s", id));
        }

        return convertDirectorEntity2Model(entity.get());
    }

    @Override
    public Director modify(Director director) {
        DirectorEntity entity = convertDirectorModel2Entity(director);
        return convertDirectorEntity2Model(directorRepository.save(entity));
    }

    @Override
    public void delete(Director director) throws DirectorNotFoundException {
        directorRepository.delete(convertDirectorModel2Entity(director));
    }

    @Override
    public Director record(Director director) throws DirectorAlreadyExistsException {
        if (directorRepository.findById(director.getId()).isPresent()) {
            throw new DirectorAlreadyExistsException();
        }
        DirectorEntity directorEntity = directorRepository.save(
                DirectorEntity.builder()
                        .id(director.getId())
                        .firstname(director.getFirstname())
                        .lastname(director.getLastname())
                        .build()
        );
        return convertDirectorEntity2Model(directorEntity);
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
}
