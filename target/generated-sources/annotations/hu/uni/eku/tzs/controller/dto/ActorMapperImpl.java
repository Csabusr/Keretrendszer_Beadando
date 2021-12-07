package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.ActorDto.ActorDtoBuilder;
import hu.uni.eku.tzs.model.Actor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class ActorMapperImpl implements ActorMapper {

    @Override
    public ActorDto actor2ActorDto(Actor actor) {
        if ( actor == null ) {
            return null;
        }

        ActorDtoBuilder actorDto = ActorDto.builder();

        actorDto.id( actor.getId() );
        actorDto.firstname( actor.getFirstname() );
        actorDto.lastname( actor.getLastname() );
        if ( actor.getGender() != null ) {
            actorDto.gender( actor.getGender().charAt( 0 ) );
        }

        return actorDto.build();
    }

    @Override
    public Actor actorDto2Actor(ActorDto dto) {
        if ( dto == null ) {
            return null;
        }

        Actor actor = new Actor();

        actor.setId( dto.getId() );
        actor.setFirstname( dto.getFirstname() );
        actor.setLastname( dto.getLastname() );
        actor.setGender( String.valueOf( dto.getGender() ) );

        return actor;
    }
}
