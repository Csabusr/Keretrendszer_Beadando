package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.BookInstance;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-28T18:34:34+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 15.0.2 (Oracle Corporation)"
)
@Component
public class BookInstanceMapperImpl implements BookInstanceMapper {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public BookInstanceDto bookInstance2BookInstanceDto(BookInstance bookInstance) {
        if ( bookInstance == null ) {
            return null;
        }

        BookInstanceDto bookInstanceDto = new BookInstanceDto();

        bookInstanceDto.setInventoryNo( bookInstance.getInventoryNo() );
        bookInstanceDto.setBook( bookMapper.book2bookDto( bookInstance.getBook() ) );
        if ( bookInstance.getState() != null ) {
            bookInstanceDto.setState( bookInstance.getState().name() );
        }

        return bookInstanceDto;
    }
}
