package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;

import java.util.Collection;

public interface ActorManager {

    Collection<Actor> readAll();

    Actor readById(int id) throws ActorNotFoundException;

    Actor modify(Actor actor);

    void delete(Actor actor) throws ActorNotFoundException;

    Actor record(Actor actor) throws ActorAlreadyExistsException;

}
