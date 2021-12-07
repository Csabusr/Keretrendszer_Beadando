package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    ActorDto actor2ActorDto(Actor actor);

    Actor actorDto2Actor(ActorDto dto);
}
