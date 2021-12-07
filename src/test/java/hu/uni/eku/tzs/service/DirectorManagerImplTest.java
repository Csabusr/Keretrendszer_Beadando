package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.DirectorGenereRepository;
import hu.uni.eku.tzs.dao.DirectorRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorGenereEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenere;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
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
class DirectorManagerImplTest {

    @Mock
    DirectorRepository directorRepository;

    @InjectMocks
    DirectorManagerImpl service;

    @Test
    void recordDirectorHappyPath() throws DirectorAlreadyExistsException {
        // given
        Director director = DirectorManagerImplTest.TestDataProvider.getOhnDoe();
        DirectorEntity directorEntity = DirectorManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(directorRepository.findById(any())).thenReturn(Optional.empty());
        when(directorRepository.save(any())).thenReturn(directorEntity);
        // when
        Director actual = service.record(director);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(director);
    }

    @Test
    void recordDirectorAlreadyExistsException() throws DirectorAlreadyExistsException {
        //given
        Director director = DirectorManagerImplTest.TestDataProvider.getOhnDoe();
        DirectorEntity directorEntity = DirectorManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(directorRepository.findById(DirectorManagerImplTest.TestDataProvider.getOhnDoe().getId()))
                .thenReturn(Optional.ofNullable(directorEntity));
        // when then
        assertThatThrownBy(() -> service.record(director))
                .isInstanceOf(DirectorAlreadyExistsException.class);

    }
    @Test
    void readByIdHappyPath() throws DirectorNotFoundException {
        // given
        Director expected = DirectorManagerImplTest.TestDataProvider.getOhnDoe();
        when(directorRepository.findById(DirectorManagerImplTest.TestDataProvider.getOhnDoe().getId()))
                .thenReturn(Optional.of(DirectorManagerImplTest.TestDataProvider.getOhnDoeEntity()));
        // when
        Director actual = service.readById(DirectorManagerImplTest.TestDataProvider.getOhnDoe().getId());
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readyByIdDirectorNotFoundException() throws DirectorNotFoundException {
        // given
        when(directorRepository.findById(DirectorManagerImplTest.TestDataProvider.unknownId))
                .thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> service.readById(DirectorManagerImplTest.TestDataProvider.unknownId))
                .isInstanceOf(DirectorNotFoundException.class)
                .hasMessageContaining(String.valueOf(DirectorManagerImplTest.TestDataProvider.unknownId));
    }

    @Test
    void readAllHappyPath() {
        // given
        List<DirectorEntity> directorEntities = List.of(
                DirectorManagerImplTest.TestDataProvider.getOhnDoeEntity(),
                DirectorManagerImplTest.TestDataProvider.getMikeOxlongEntity()
        );
        Page<DirectorEntity> page = new PageImpl<>(directorEntities);
        Collection<Director> expectedCustomers = List.of(
                DirectorManagerImplTest.TestDataProvider.getOhnDoe(),
                DirectorManagerImplTest.TestDataProvider.getMikeOxlong()
        );
        when(directorRepository.findAll(isA(Pageable.class))).thenReturn(page);
        // when
        Collection<Director> actualDirectors = service.readAll();
        // then
        assertThat(actualDirectors)
                .usingRecursiveComparison()
                .isEqualTo(expectedCustomers);
    }

    @Test
    void modifyDirectorHappyPath() {
        // given
        Director director = DirectorManagerImplTest.TestDataProvider.getOhnDoe();
        DirectorEntity directorEntity = DirectorManagerImplTest.TestDataProvider.getOhnDoeEntity();
        when(directorRepository.save(directorEntity)).thenReturn(directorEntity);
        // when
        Director actual = service.modify(director);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(director);
    }

    private static class TestDataProvider {

        public static final int unknownId = -1;

        public static final Director testDirector = new Director(1,"Teszt","Elek");
        public static final DirectorEntity testDirectorEntity = DirectorEntity.builder()
                .id(testDirector.getId())
                .firstname(testDirector.getFirstname())
                .lastname(testDirector.getLastname())
                .build();


        public static Director getOhnDoe() {
            return testDirector;
        }

        public static DirectorEntity getOhnDoeEntity() {
            return DirectorEntity.builder()
                    .id(testDirector.getId())
                    .firstname(testDirector.getFirstname())
                    .lastname(testDirector.getLastname())
                    .build();
        }

        public static final Director testDirector1 = new Director(2,"Mike","Oxlong");
        public static final DirectorEntity testDirectorEntity1 = DirectorEntity.builder()
                .id(testDirector1.getId())
                .firstname(testDirector1.getFirstname())
                .lastname(testDirector1.getLastname())
                .build();

        public static Director getMikeOxlong() {
            return testDirector1;
        }

        public static DirectorEntity getMikeOxlongEntity() {
            return DirectorEntity.builder()
                    .id(testDirector1.getId())
                    .firstname(testDirector1.getFirstname())
                    .lastname(testDirector1.getLastname())
                    .build();
        }
    }
}