package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.controller.dto.BookDto.BookDtoBuilder;
import hu.uni.eku.tzs.model.Book;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Autowired
    private AuthorMapper authorMapper;

    @Override
    public BookDto book2bookDto(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDtoBuilder bookDto = BookDto.builder();

        bookDto.isbn( book.getIsbn() );
        bookDto.author( authorMapper.author2AuthorDto( book.getAuthor() ) );
        bookDto.title( book.getTitle() );
        bookDto.language( book.getLanguage() );

        return bookDto.build();
    }

    @Override
    public Book bookDto2Book(BookDto dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setIsbn( dto.getIsbn() );
        book.setAuthor( authorMapper.authorDto2Author( dto.getAuthor() ) );
        book.setTitle( dto.getTitle() );
        book.setLanguage( dto.getLanguage() );

        return book;
    }
}
