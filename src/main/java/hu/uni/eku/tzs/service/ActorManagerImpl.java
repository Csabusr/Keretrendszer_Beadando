package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActorManagerImpl implements ActorManager {

    private final ActorRepository actorRepository;

    @Override
    public Collection<Actor> readAll() {
        return actorRepository.findAll(PageRequest.of(0, 100)).stream().map(ActorManagerImpl::convertActorEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Actor readById(int id) throws ActorNotFoundException {
        Optional<ActorEntity> entity = actorRepository.findById(id);
        if (entity.isEmpty()) {
            throw new ActorNotFoundException(String.format("Cannot find Actor with this id %s", id));
        }

        return convertActorEntity2Model(entity.get());
    }

    @Override
    public Actor modify(Actor actor) {
        ActorEntity entity = convertActorModel2Entity(actor);
        return convertActorEntity2Model(actorRepository.save(entity));
    }

    @Override
    public void delete(Actor actor) {
        actorRepository.delete(convertActorModel2Entity(actor));
    }

    @Override
    public Actor record(Actor actor) throws ActorAlreadyExistsException {
        if (actorRepository.findById(actor.getId()).isPresent()) {
            throw new ActorAlreadyExistsException();
        }
        ActorEntity actorEntity = actorRepository.save(
                ActorEntity.builder()
                        .id(actor.getId())
                        .firstname(actor.getFirstname())
                        .lastname(actor.getLastname())
                        .gender(actor.getGender())
                        .build()
        );
        return convertActorEntity2Model(actorEntity);
    }

    private static Actor convertActorEntity2Model(ActorEntity actorEntity) {
        return new Actor(
                actorEntity.getId(),
                actorEntity.getFirstname(),
                actorEntity.getLastname(),
                actorEntity.getGender()
        );
    }

    private static ActorEntity convertActorModel2Entity(Actor actor) {
        return ActorEntity.builder()
                .id(actor.getId())
                .firstname(actor.getFirstname())
                .lastname(actor.getLastname())
                .gender(actor.getGender())
                .build();
    }

}
