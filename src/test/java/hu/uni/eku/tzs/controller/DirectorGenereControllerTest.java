package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.DirectorDto;
import hu.uni.eku.tzs.controller.dto.DirectorGenereDto;
import hu.uni.eku.tzs.controller.dto.DirectorGenereMapper;
import hu.uni.eku.tzs.controller.dto.DirectorMapper;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorGenereEntity;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenere;
import hu.uni.eku.tzs.service.DirectorGenereManager;
import hu.uni.eku.tzs.service.DirectorManager;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereNotFoundException;
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
class DirectorGenereControllerTest {

    @Mock
    private DirectorGenereManager directorGenereManager;

    @Mock
    private DirectorGenereMapper directorGenereMapper;

    @InjectMocks
    private DirectorGenereController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(directorGenereManager.readAll()).thenReturn(List.of(DirectorGenereControllerTest.TestDataProvider.getJohnDoe()));
        when(directorGenereMapper.directorGenere2DirectorGenereDto(any())).thenReturn(DirectorGenereControllerTest.TestDataProvider.getJohnDoeDto());
        Collection<DirectorGenereDto> expected = List.of(DirectorGenereControllerTest.TestDataProvider.getJohnDoeDto());
        // when
        Collection<DirectorGenereDto> actual = controller.readAllDirectors();
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createDirectorGenereHappyPath() throws DirectorGenereAlreadyExistsException {
        // given
        DirectorGenere johnDoe = DirectorGenereControllerTest.TestDataProvider.getJohnDoe();
        DirectorGenereDto johnDoeDto = DirectorGenereControllerTest.TestDataProvider.getJohnDoeDto();
        when(directorGenereMapper.directorGenereDto2DirectorGenere(johnDoeDto)).thenReturn(johnDoe);
        when(directorGenereManager.record(johnDoe)).thenReturn(johnDoe);
        when(directorGenereMapper.directorGenere2DirectorGenereDto(johnDoe)).thenReturn(johnDoeDto);
        // when
        DirectorGenereDto actual = controller.create(johnDoeDto);
        // then
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(johnDoeDto);
    }

    @Test
    void createDirectorGenereThrowsCustomerAlreadyExistsException() throws DirectorGenereAlreadyExistsException {
        // given
        DirectorGenere johnDoe = DirectorGenereControllerTest.TestDataProvider.getJohnDoe();
        DirectorGenereDto johnDoeDto = DirectorGenereControllerTest.TestDataProvider.getJohnDoeDto();
        when(directorGenereMapper.directorGenereDto2DirectorGenere(johnDoeDto)).thenReturn(johnDoe);
        when(directorGenereManager.record(johnDoe)).thenThrow(new DirectorGenereAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> controller.create(johnDoeDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws DirectorGenereNotFoundException {
        // given
        DirectorGenere johnDoe = DirectorGenereControllerTest.TestDataProvider.getJohnDoe();
        when(directorGenereManager.readById(DirectorGenereControllerTest.TestDataProvider.getJohnDoe().getDirector().getId())).thenReturn(johnDoe);
        doNothing().when(directorGenereManager).delete(johnDoe);
        // when
        controller.delete(DirectorGenereControllerTest.TestDataProvider.getJohnDoe().getDirector().getId());
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenCustomerNotFound() throws DirectorGenereNotFoundException {
        // given
        final int notFoundCustomerId = DirectorGenereControllerTest.TestDataProvider.unknownId;
        doThrow(new DirectorGenereNotFoundException(String.format("Cannot find director with ID %d", notFoundCustomerId)))
                .when(directorGenereManager).readById(notFoundCustomerId);
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

        public static final DirectorGenere testDirectorGenre = new DirectorGenere(testDirector, "Drama", 2.4f);
        public static final DirectorGenereDto testDirectorGenereDto = DirectorGenereDto.builder()
                .director(testDirectorDto)
                .genre(testDirectorGenre.getGenre())
                .prob(testDirectorGenre.getProb())
                .build();

        public static DirectorGenere getJohnDoe() {
            return testDirectorGenre;
        }

        public static DirectorGenereDto getJohnDoeDto() {
            return DirectorGenereDto.builder()
                    .director(testDirectorDto)
                    .genre(testDirectorGenre.getGenre())
                    .prob(testDirectorGenre.getProb())
                    .build();
        }

        public static final Director testDirector1 = new Director(2,"Teszt1","Elek1");
        public static final DirectorDto testDirectorDto1 = DirectorDto.builder()
                .id(testDirector1.getId())
                .firstname(testDirector1.getFirstname())
                .lastname(testDirector1.getLastname())
                .build();

        public static final DirectorGenere testDirectorGenre1 = new DirectorGenere(testDirector1, "Drama1", 2.5f);
        public static final DirectorGenereDto testDirectorGenereEntity1 = DirectorGenereDto.builder()
                .director(testDirectorDto1)
                .genre(testDirectorGenre1.getGenre())
                .prob(testDirectorGenre1.getProb())
                .build();

        public static DirectorGenere getJaneDoe() {
            return testDirectorGenre1;
        }

        public static DirectorGenereDto getJaneDoeDto() {
            return DirectorGenereDto.builder()
                    .director(testDirectorDto1)
                    .genre(testDirectorGenre1.getGenre())
                    .prob(testDirectorGenre1.getProb())
                    .build();
        }
    }
}