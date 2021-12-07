package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class ActorManagerImplTest {

    @Mock
    ActorRepository actorRepository;

    @InjectMocks
    ActorManagerImpl service;

    @Test
    void recordActorHappyPath() throws ActorAlreadyExistsException {
        // given
        Actor actor = TestDataProvider.getJohnDoe();
        ActorEntity actorEntity = TestDataProvider.getJohnDoeEntity();
        when(actorRepository.findById(any())).thenReturn(Optional.empty());
        when(actorRepository.save(any())).thenReturn(actorEntity);
        // when
        Actor actual = service.record(actor);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(actor);
    }

    @Test
    void recordActorAlreadyExistsException() throws ActorAlreadyExistsException {
        //given
        Actor actor = TestDataProvider.getJohnDoe();
        ActorEntity actorEntity = TestDataProvider.getJohnDoeEntity();
        when(actorRepository.findById(TestDataProvider.johnDoeId))
                .thenReturn(Optional.ofNullable(actorEntity));
        // when then
        assertThatThrownBy(() -> service.record(actor))
                .isInstanceOf(ActorAlreadyExistsException.class);

    }
    @Test
    void readByIdHappyPath() throws ActorNotFoundException {
        // given
        Actor expected = TestDataProvider.getJohnDoe();
        when(actorRepository.findById(TestDataProvider.johnDoeId))
                .thenReturn(Optional.of(TestDataProvider.getJohnDoeEntity()));
        // when
        Actor actual = service.readById(TestDataProvider.johnDoeId);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readyByIdActorNotFoundException() throws ActorNotFoundException {
        // given
        when(actorRepository.findById(TestDataProvider.unknownId))
                .thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> service.readById(TestDataProvider.unknownId))
                .isInstanceOf(ActorNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProvider.unknownId));
    }

    @Test
    void readAllHappyPath() {
        // given
        List<ActorEntity> actorEntities = List.of(
                TestDataProvider.getJohnDoeEntity(),
                TestDataProvider.getJaneDoeEntity()
        );
        Page<ActorEntity> page = new PageImpl<>(actorEntities);
        Collection<Actor> expectedCustomers = List.of(
                TestDataProvider.getJohnDoe(),
                TestDataProvider.getJaneDoe()
        );
        when(actorRepository.findAll(isA(Pageable.class))).thenReturn(page);
        // when
        Collection<Actor> actualCustomers = service.readAll();
        // then
        assertThat(actualCustomers)
                .usingRecursiveComparison()
                .isEqualTo(expectedCustomers);
    }

    @Test
    void modifyActorHappyPath() {
        // given
        Actor customer = TestDataProvider.getJohnDoe();
        ActorEntity actorEntity = TestDataProvider.getJohnDoeEntity();
        when(actorRepository.save(actorEntity)).thenReturn(actorEntity);
        // when
        Actor actual = service.modify(customer);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(customer);
    }

    private static class TestDataProvider {

        public static final int johnDoeId = 1;

        public static final int janeDoeId = 2;

        public static final int unknownId = -1;

        public static Actor getJohnDoe() {
            return new Actor(johnDoeId, "John", "Doe","M");
        }

        public static ActorEntity getJohnDoeEntity() {
            return ActorEntity.builder()
                    .id(johnDoeId)
                    .firstname("John")
                    .lastname("Doe")
                    .gender("M")
                    .build();
        }

        public static Actor getJaneDoe() {
            return new Actor(janeDoeId, "Jane", "Doe","M");
        }

        public static ActorEntity getJaneDoeEntity() {
            return ActorEntity.builder()
                    .id(janeDoeId)
                    .firstname("Jane")
                    .lastname("Doe")
                    .gender("M")
                    .build();
        }

    }
}