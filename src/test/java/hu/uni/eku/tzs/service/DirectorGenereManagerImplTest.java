package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ActorRepository;
import hu.uni.eku.tzs.dao.DirectorGenereRepository;
import hu.uni.eku.tzs.dao.entity.ActorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorEntity;
import hu.uni.eku.tzs.dao.entity.DirectorGenereEntity;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.model.DirectorGenere;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereNotFoundException;
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
class DirectorGenereManagerImplTest {

    @Mock
    DirectorGenereRepository directorGenereRepository;

    @InjectMocks
    DirectorGenereManagerImpl service;

        @Test
        void recordDirectorGenereHappyPath() throws DirectorGenereAlreadyExistsException {
            // given
            DirectorGenere directorGenere = TestDataProvider.getJohnDoe();
            DirectorGenereEntity directorGenereEntity = TestDataProvider.getJohnDoeEntity();
            when(directorGenereRepository.findById(any())).thenReturn(Optional.empty());
            when(directorGenereRepository.save(any())).thenReturn(directorGenereEntity);
            // when
            DirectorGenere actual = service.record(directorGenere);
            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(directorGenere);
        }

        @Test
        void recordDirectorGenereAlreadyExistsException() throws DirectorGenereAlreadyExistsException {
            //given
            DirectorGenere directorGenere = TestDataProvider.getJohnDoe();
            DirectorGenereEntity directorGenereEntity = TestDataProvider.getJohnDoeEntity();
            when(directorGenereRepository.findById(TestDataProvider.getJohnDoe().getDirector().getId()))
                    .thenReturn(Optional.ofNullable(directorGenereEntity));
            // when then
            assertThatThrownBy(() -> service.record(directorGenere))
                    .isInstanceOf(DirectorGenereAlreadyExistsException.class);

        }

        @Test
        void readByIdHappyPath() throws DirectorGenereNotFoundException {
            // given
            DirectorGenere expected = TestDataProvider.getJohnDoe();
            when(directorGenereRepository.findById(TestDataProvider.getJohnDoe().getDirector().getId()))
                    .thenReturn(Optional.of(TestDataProvider.getJohnDoeEntity()));
            // when
            DirectorGenere actual = service.readById(TestDataProvider.getJohnDoe().getDirector().getId());
            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        void readyByIdDirectorGenreNotFoundException() throws DirectorGenereNotFoundException {
            // given
            when(directorGenereRepository.findById(TestDataProvider.unknownId))
                    .thenReturn(Optional.empty());
            // when then
            assertThatThrownBy(() -> service.readById(TestDataProvider.unknownId))
                    .isInstanceOf(DirectorGenereNotFoundException.class)
                    .hasMessageContaining(String.valueOf(TestDataProvider.unknownId));
        }

        @Test
        void readAllHappyPath() {
            // given
            List<DirectorGenereEntity> customerEntities = List.of(
                    TestDataProvider.getJohnDoeEntity(),
                    TestDataProvider.getJaneDoeEntity()
            );
            Page<DirectorGenereEntity> page = new PageImpl<>(customerEntities);
            Collection<DirectorGenere> expectedCustomers = List.of(
                    TestDataProvider.getJohnDoe(),
                    TestDataProvider.getJaneDoe()
            );
            when(directorGenereRepository.findAll(isA(Pageable.class))).thenReturn(page);
            // when
            Collection<DirectorGenere> actualCustomers = service.readAll();
            // then
            assertThat(actualCustomers)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedCustomers);
        }

        @Test
        void modifyCustomerHappyPath() {
            // given
            DirectorGenere directorGenere = TestDataProvider.getJohnDoe();
            DirectorGenereEntity directorGenereEntity = TestDataProvider.getJohnDoeEntity();
            when(directorGenereRepository.save(directorGenereEntity)).thenReturn(directorGenereEntity);
            // when
            DirectorGenere actual = service.modify(directorGenere);
            // then
            assertThat(actual).usingRecursiveComparison()
                    .isEqualTo(directorGenere);
        }

    private static class TestDataProvider {

        public static final int unknownId = -1;

        public static final Director testDirector = new Director(1,"Teszt","Elek");
        public static final DirectorEntity testDirectorEntity = DirectorEntity.builder()
                .id(testDirector.getId())
                .firstname(testDirector.getFirstname())
                .lastname(testDirector.getLastname())
                .build();

        public static final DirectorGenere testDirectorGenre = new DirectorGenere(testDirector, "Drama", 2.4f);
        public static final DirectorGenereEntity testDirectorGenereEntity = DirectorGenereEntity.builder()
                .director(testDirectorEntity)
                .genere(testDirectorGenre.getGenre())
                .prob(testDirectorGenre.getProb())
                .build();

        public static DirectorGenere getJohnDoe() {
            return testDirectorGenre;
        }

        public static DirectorGenereEntity getJohnDoeEntity() {
            return DirectorGenereEntity.builder()
                    .director(testDirectorEntity)
                    .genere(testDirectorGenre.getGenre())
                    .prob(testDirectorGenre.getProb())
                    .build();
        }

        public static final Director testDirector1 = new Director(2,"Teszt1","Elek1");
        public static final DirectorEntity testDirectorEntity1 = DirectorEntity.builder()
                .id(testDirector1.getId())
                .firstname(testDirector1.getFirstname())
                .lastname(testDirector1.getLastname())
                .build();

        public static final DirectorGenere testDirectorGenre1 = new DirectorGenere(testDirector1, "Drama1", 2.5f);
        public static final DirectorGenereEntity testDirectorGenereEntity1 = DirectorGenereEntity.builder()
                .director(testDirectorEntity1)
                .genere(testDirectorGenre1.getGenre())
                .prob(testDirectorGenre1.getProb())
                .build();

        public static DirectorGenere getJaneDoe() {
            return testDirectorGenre1;
        }

        public static DirectorGenereEntity getJaneDoeEntity() {
            return DirectorGenereEntity.builder()
                    .director(testDirectorEntity1)
                    .genere(testDirectorGenre1.getGenre())
                    .prob(testDirectorGenre1.getProb())
                    .build();
        }
    }
}