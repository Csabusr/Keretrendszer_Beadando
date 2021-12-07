package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.AuthorDto.AuthorDtoBuilder;
import hu.uni.eku.tzs.model.Author;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto author2AuthorDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDtoBuilder authorDto = AuthorDto.builder();

        authorDto.id( author.getId() );
        authorDto.firstName( author.getFirstName() );
        authorDto.lastName( author.getLastName() );
        authorDto.nationality( author.getNationality() );

        return authorDto.build();
    }

    @Override
    public Author authorDto2Author(AuthorDto dto) {
        if ( dto == null ) {
            return null;
        }

        Author author = new Author();

        author.setId( dto.getId() );
        author.setFirstName( dto.getFirstName() );
        author.setLastName( dto.getLastName() );
        author.setNationality( dto.getNationality() );

        return author;
    }
}
