package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.ActorDto.ActorDtoBuilder;
import hu.uni.eku.tzs.controller.dto.MovieDto.MovieDtoBuilder;
import hu.uni.eku.tzs.controller.dto.RoleDto.RoleDtoBuilder;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDto role2RoleDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDtoBuilder roleDto = RoleDto.builder();

        roleDto.actor( actorToActorDto( role.getActor() ) );
        roleDto.movie( movieToMovieDto( role.getMovie() ) );
        roleDto.role( role.getRole() );

        return roleDto.build();
    }

    @Override
    public Role roleDto2Role(RoleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setActor( actorDtoToActor( dto.getActor() ) );
        role.setMovie( movieDtoToMovie( dto.getMovie() ) );
        role.setRole( dto.getRole() );

        return role;
    }

    protected ActorDto actorToActorDto(Actor actor) {
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

    protected MovieDto movieToMovieDto(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieDtoBuilder movieDto = MovieDto.builder();

        movieDto.id( movie.getId() );
        movieDto.name( movie.getName() );
        movieDto.year( movie.getYear() );
        movieDto.rank( movie.getRank() );

        return movieDto.build();
    }

    protected Actor actorDtoToActor(ActorDto actorDto) {
        if ( actorDto == null ) {
            return null;
        }

        Actor actor = new Actor();

        actor.setId( actorDto.getId() );
        actor.setFirstname( actorDto.getFirstname() );
        actor.setLastname( actorDto.getLastname() );
        actor.setGender( String.valueOf( actorDto.getGender() ) );

        return actor;
    }

    protected Movie movieDtoToMovie(MovieDto movieDto) {
        if ( movieDto == null ) {
            return null;
        }

        Movie movie = new Movie();

        movie.setId( movieDto.getId() );
        movie.setName( movieDto.getName() );
        movie.setYear( movieDto.getYear() );
        movie.setRank( movieDto.getRank() );

        return movie;
    }
}
