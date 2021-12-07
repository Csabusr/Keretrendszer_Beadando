package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ActorDto;
import hu.uni.eku.tzs.controller.dto.ActorMapper;
import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.ActorManager;
import hu.uni.eku.tzs.service.ActorManagerImpl;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    @Mock
    private ActorManager actorManager;

    @Mock
    private ActorMapper actorMapper;

    @InjectMocks
    private ActorController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(actorManager.readAll()).thenReturn(List.of(TestDataProvider.getJohnDoe()));
        when(actorMapper.actor2ActorDto(any())).thenReturn(TestDataProvider.getJohnDoeDto());
        Collection<ActorDto> expected = List.of(TestDataProvider.getJohnDoeDto());
        // when
        Collection<ActorDto> actual = controller.readAllActors();
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createActorHappyPath() throws ActorAlreadyExistsException {
        // given
        Actor johnDoe = ActorControllerTest.TestDataProvider.getJohnDoe();
        ActorDto johnDoeDto = ActorControllerTest.TestDataProvider.getJohnDoeDto();
        when(actorMapper.actorDto2Actor(johnDoeDto)).thenReturn(johnDoe);
        when(actorManager.record(johnDoe)).thenReturn(johnDoe);
        when(actorMapper.actor2ActorDto(johnDoe)).thenReturn(johnDoeDto);
        // when
        ActorDto actual = controller.create(johnDoeDto);
        // then
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(johnDoeDto);
    }

    @Test
    void createActorThrowsCustomerAlreadyExistsException() throws ActorAlreadyExistsException {
        // given
        Actor johnDoe = ActorControllerTest.TestDataProvider.getJohnDoe();
        ActorDto johnDoeDto = ActorControllerTest.TestDataProvider.getJohnDoeDto();
        when(actorMapper.actorDto2Actor(johnDoeDto)).thenReturn(johnDoe);
        when(actorManager.record(johnDoe)).thenThrow(new ActorAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> controller.create(johnDoeDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws ActorNotFoundException {
        // given
        Actor johnDoe = ActorControllerTest.TestDataProvider.getJohnDoe();
        when(actorManager.readById(TestDataProvider.getJaneDoe().getId())).thenReturn(johnDoe);
        doNothing().when(actorManager).delete(johnDoe);
        // when
        controller.delete(TestDataProvider.getJaneDoe().getId());
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenActorNotFound() throws ActorNotFoundException {
        // given
        final int notFoundCustomerId = TestDataProvider.unknownId;
        doThrow(new ActorNotFoundException(String.format("Cannot find Actor with ID %d", notFoundCustomerId)))
                .when(actorManager).readById(notFoundCustomerId);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundCustomerId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int johnDoeId = 1;

        public static final int janeDoeId = 2;

        public static final int unknownId = -1;

        public static Actor getJohnDoe() {
            return new Actor(johnDoeId, "John", "Doe","M");
        }

        public static ActorDto getJohnDoeDto() {
            return ActorDto.builder()
                    .id(johnDoeId)
                    .firstname("John")
                    .lastname("Doe")
                    .gender('M')
                    .build();
        }

        public static Actor getJaneDoe() {
            return new Actor(janeDoeId, "Jane", "Doe","M");
        }

        public static ActorDto getJaneDoeDto() {
            return ActorDto.builder()
                    .id(janeDoeId)
                    .firstname("Jane")
                    .lastname("Doe")
                    .gender('M')
                    .build();
        }
    }
}