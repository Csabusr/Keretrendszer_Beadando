package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.MovieRepository;
import hu.uni.eku.tzs.dao.RoleRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.dao.entity.MovieEntity;
import hu.uni.eku.tzs.dao.entity.RoleEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.service.exceptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class RoleManagerImplTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleManagerImpl service;

    @Test
    void recordRoleHappyPath() throws RoleAlreadyExistsException {
        // given
        Role role = RoleManagerImplTest.TestDataProvider.getOhnDoe();
        RoleEntity roleEntity = RoleManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(roleRepository.findById(any())).thenReturn(Optional.empty());
        when(roleRepository.save(any())).thenReturn(roleEntity);
        // when
        Role actual = service.record(role);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(role);
    }

    @Test
    void recordRoleAlreadyExistsException() throws RoleAlreadyExistsException {
        //given
        Role role = RoleManagerImplTest.TestDataProvider.getOhnDoe();
        RoleEntity roleEntity = RoleManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(roleRepository.findById(RoleManagerImplTest.TestDataProvider.getOhnDoe().getMovie().getId()))
                .thenReturn(Optional.ofNullable(roleEntity));
        // when then
        assertThatThrownBy(() -> service.record(role))
                .isInstanceOf(RoleAlreadyExistsException.class);

    }
    @Test
    void readByIdHappyPath() throws RoleNotFoundException {
        // given
        Role expected = RoleManagerImplTest.TestDataProvider.getOhnDoe();
        when(roleRepository.findById(RoleManagerImplTest.TestDataProvider.getOhnDoe().getMovie().getId()))
                .thenReturn(Optional.of(RoleManagerImplTest.TestDataProvider.getOhnDoeEntity()));
        // when
        Role actual = service.readById(RoleManagerImplTest.TestDataProvider.getOhnDoe().getMovie().getId());
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readyByIdRoleNotFoundException() throws RoleNotFoundException {
        // given
        when(roleRepository.findById(RoleManagerImplTest.TestDataProvider.unknownId))
                .thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> service.readById(RoleManagerImplTest.TestDataProvider.unknownId))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessageContaining(String.valueOf(RoleManagerImplTest.TestDataProvider.unknownId));
    }

    @Test
    void readAllHappyPath() {
        // given
        List<RoleEntity> roleEntities = List.of(
                RoleManagerImplTest.TestDataProvider.getOhnDoeEntity(),
                RoleManagerImplTest.TestDataProvider.getMikeOxlongEntity()
        );
        Page<RoleEntity> page = new PageImpl<>(roleEntities);
        Collection<Role> expectedMovies = List.of(
                RoleManagerImplTest.TestDataProvider.getOhnDoe(),
                RoleManagerImplTest.TestDataProvider.getMikeOxlong()
        );
        when(roleRepository.findAll(isA(Pageable.class))).thenReturn(page);
        // when
        Collection<Role> actualRole = service.readAll();
        // then
        assertThat(actualRole)
                .usingRecursiveComparison()
                .isEqualTo(expectedMovies);
    }

    @Test
    void modifyMovieHappyPath() {
        // given
        Role role = RoleManagerImplTest.TestDataProvider.getOhnDoe();
        RoleEntity roleEntity = RoleManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);
        // when
        Role actual = service.modify(role);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(role);
    }

    private static class TestDataProvider {

        public static final int unknownId = -1;

        public static final Movie testMovie = new Movie(1,"InterSpar",1982,2.233f);
        public static final MovieEntity testMovieEntity = MovieEntity.builder()
                .id(testMovie.getId())
                .name(testMovie.getName())
                .year(testMovie.getYear())
                .rank(testMovie.getRank())
                .build();

        public static final Movie testMovie1 = new Movie(2,"InterSpar1",1983,2.223f);
        public static final MovieEntity testMovieEntity1 = MovieEntity.builder()
                .id(testMovie1.getId())
                .name(testMovie1.getName())
                .year(testMovie1.getYear())
                .rank(testMovie1.getRank())
                .build();

        public static final Actor testActor = new Actor(1,"Ohn","Doe","M");
        public static final ActorEntity testActorEntity = ActorEntity.builder()
                .id(testActor.getId())
                .firstname(testActor.getFirstname())
                .lastname(testActor.getLastname())
                .gender(testActor.getGender())
                .build();

        public static final Actor testActor1 = new Actor(2,"Mike","Oxlong","M");
        public static final ActorEntity testActorEntity1 = ActorEntity.builder()
                .id(testActor1.getId())
                .firstname(testActor1.getFirstname())
                .lastname(testActor1.getLastname())
                .gender(testActor1.getGender())
                .build();

        public static final Role testRole = new Role(testActor,testMovie,"Role Szoveg");
        public static final RoleEntity testRoleEntity = RoleEntity.builder()
                .actor(testActorEntity)
                .movie(testMovieEntity)
                .role(testRole.getRole())
                .build();


        public static Role getOhnDoe() {
            return testRole;
        }

        public static RoleEntity getOhnDoeEntity() {
            return RoleEntity.builder()
                    .actor(testActorEntity)
                    .movie(testMovieEntity)
                    .role(testRole.getRole())
                    .build();
        }

        public static final Role testRole1 = new Role(testActor1,testMovie1,"Role Szoveg");
        public static final RoleEntity testRoleEntity1 = RoleEntity.builder()
                .actor(testActorEntity1)
                .movie(testMovieEntity1)
                .role(testRole1.getRole())
                .build();


        public static Role getMikeOxlong() {
            return testRole1;
        }

        public static RoleEntity getMikeOxlongEntity() {
            return RoleEntity.builder()
                    .actor(testActorEntity1)
                    .movie(testMovieEntity1)
                    .role(testRole1.getRole())
                    .build();
        }
    }
}