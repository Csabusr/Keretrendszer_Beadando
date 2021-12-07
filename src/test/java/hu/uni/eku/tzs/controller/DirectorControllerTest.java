package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ActorMapper;
import hu.uni.eku.tzs.controller.dto.DirectorDto;
import hu.uni.eku.tzs.controller.dto.DirectorMapper;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.ActorManager;
import hu.uni.eku.tzs.service.DirectorManager;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectorControllerTest {

    @Mock
    private DirectorManager directorManager;

    @Mock
    private DirectorMapper directorMapper;

    @InjectMocks
    private DirectorController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(directorManager.readAll()).thenReturn(List.of(TestDataProvider.getOhnDoe()));
        when(directorMapper.director2DirectorDto(any())).thenReturn(TestDataProvider.getOhnDoeDto());
        Collection<DirectorDto> expected = List.of(TestDataProvider.getOhnDoeDto());
        // when
        Collection<DirectorDto> actual = controller.readAllDirectors();
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createDirectorHappyPath() throws DirectorAlreadyExistsException {
        // given
        Director johnDoe = DirectorControllerTest.TestDataProvider.getOhnDoe();
        DirectorDto johnDoeDto = DirectorControllerTest.TestDataProvider.getOhnDoeDto();
        when(directorMapper.directorDto2Director(johnDoeDto)).thenReturn(johnDoe);
        when(directorManager.record(johnDoe)).thenReturn(johnDoe);
        when(directorMapper.director2DirectorDto(johnDoe)).thenReturn(johnDoeDto);
        // when
        DirectorDto actual = controller.create(johnDoeDto);
        // then
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(johnDoeDto);
    }

    @Test
    void createDirectorThrowsCustomerAlreadyExistsException() throws DirectorAlreadyExistsException {
        // given
        Director johnDoe = DirectorControllerTest.TestDataProvider.getOhnDoe();
        DirectorDto johnDoeDto = DirectorControllerTest.TestDataProvider.getOhnDoeDto();
        when(directorMapper.directorDto2Director(johnDoeDto)).thenReturn(johnDoe);
        when(directorManager.record(johnDoe)).thenThrow(new DirectorAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> controller.create(johnDoeDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws DirectorNotFoundException {
        // given
        Director johnDoe = DirectorControllerTest.TestDataProvider.getOhnDoe();
        when(directorManager.readById(TestDataProvider.getOhnDoe().getId())).thenReturn(johnDoe);
        doNothing().when(directorManager).delete(johnDoe);
        // when
        controller.delete(TestDataProvider.getOhnDoe().getId());
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenCustomerNotFound() throws DirectorNotFoundException {
        // given
        final int notFoundCustomerId = TestDataProvider.unknownId;
        doThrow(new DirectorNotFoundException(String.format("Cannot find director with ID %d", notFoundCustomerId)))
                .when(directorManager).readById(notFoundCustomerId);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundCustomerId))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final int unknownId = -1;

        public static final Director testDirector = new Director(1,"Teszt","Elek");
        public static final DirectorDto testDirectorDto = DirectorDto.builder()
                .id(testDirector.getId())
                .firstname(testDirector.getFirstname())
                .lastname(testDirector.getLastname())
                .build();


        public static Director getOhnDoe() {
            return testDirector;
        }

        public static DirectorDto getOhnDoeDto() {
            return DirectorDto.builder()
                    .id(testDirector.getId())
                    .firstname(testDirector.getFirstname())
                    .lastname(testDirector.getLastname())
                    .build();
        }

        public static final Director testDirector1 = new Director(2,"Mike","Oxlong");
        public static final DirectorDto testDirectorDto1 = DirectorDto.builder()
                .id(testDirector1.getId())
                .firstname(testDirector1.getFirstname())
                .lastname(testDirector1.getLastname())
                .build();

        public static Director getMikeOxlong() {
            return testDirector1;
        }

        public static DirectorDto getMikeOxlongDto() {
            return DirectorDto.builder()
                    .id(testDirector1.getId())
                    .firstname(testDirector1.getFirstname())
                    .lastname(testDirector1.getLastname())
                    .build();
        }
    }
}