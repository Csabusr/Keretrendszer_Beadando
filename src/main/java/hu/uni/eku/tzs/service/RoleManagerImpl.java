package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.RoleRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.dao.entity.RoleEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.service.exceptions.RoleAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleManagerImpl implements RoleManager {

    private final RoleRepository roleRepository;

    @Override
    public Collection<Role> readAll() {
        return roleRepository.findAll(PageRequest.of(0, 100)).stream()
                .map(RoleManagerImpl::convertRoleEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Role readById(int id) throws RoleNotFoundException {
        Optional<RoleEntity> entity = roleRepository.findById(id);
        if (entity.isEmpty()) {
            throw new RoleNotFoundException(String.format("Cannot find Role with this id %s", id));
        }

        return convertRoleEntity2Model(entity.get());
    }

    @Override
    public Role modify(Role role) {
        RoleEntity entity = convertRoleModel2Entity(role);
        return convertRoleEntity2Model(roleRepository.save(entity));
    }

    @Override
    public void delete(Role role) throws RoleNotFoundException {
        roleRepository.delete(convertRoleModel2Entity(role));
    }

    @Override
    public Role record(Role role) throws RoleAlreadyExistsException {
        if (roleRepository.findById(role.getActor().getId()).isPresent()) {
            throw new RoleAlreadyExistsException();
        }
        RoleEntity roleEntity = roleRepository.save(
                RoleEntity.builder()
                        .actor(convertActorModel2Entity(role.getActor()))
                        .movie(convertMovieModel2Entity(role.getMovie()))
                        .role(role.getRole())
                        .build()
        );
        return convertRoleEntity2Model(roleEntity);
    }

    private static Role convertRoleEntity2Model(RoleEntity entity) {
        return new Role(
                convertActorEntity2Model(entity.getActor()),
                convertMovieEntity2Model(entity.getMovie()),
                entity.getRole());
    }

    private static RoleEntity convertRoleModel2Entity(Role role) {
        return RoleEntity.builder()
                .actor(convertActorModel2Entity(role.getActor()))
                .movie(convertMovieModel2Entity(role.getMovie()))
                .role(role.getRole())
                .build();
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
