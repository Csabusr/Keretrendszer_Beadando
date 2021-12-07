package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.DirectorGenereDto;
import hu.uni.eku.tzs.controller.dto.DirectorGenereMapper;
import hu.uni.eku.tzs.model.DirectorGenere;
import hu.uni.eku.tzs.service.DirectorGenereManager;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorGenereNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "Directors_Generes")
@RequestMapping("/directors_generes")
@RestController
@RequiredArgsConstructor
public class DirectorGenereController {

    private final DirectorGenereManager directorGenereManager;

    private final DirectorGenereMapper directorGenereMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<DirectorGenereDto> readAllDirectors() {
        return directorGenereManager.readAll()
                .stream()
                .map(directorGenereMapper::directorGenere2DirectorGenereDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public DirectorGenereDto create(@Valid @RequestBody DirectorGenereDto recordRequestDto) {
        DirectorGenere directorGenere = directorGenereMapper.directorGenereDto2DirectorGenere(recordRequestDto);
        try {
            DirectorGenere recordedDirectorGenere = directorGenereManager.record(directorGenere);
            return directorGenereMapper.directorGenere2DirectorGenereDto(recordedDirectorGenere);
        } catch (DirectorGenereAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public DirectorGenereDto update(@Valid @RequestBody DirectorGenereDto updateRequestDto) {
        DirectorGenere directorGenere = directorGenereMapper.directorGenereDto2DirectorGenere(updateRequestDto);
        DirectorGenere updatedDirectorGenere = directorGenereManager.modify(directorGenere);
        return directorGenereMapper.directorGenere2DirectorGenereDto(updatedDirectorGenere);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam int id) {
        try {
            directorGenereManager.delete(directorGenereManager.readById(id));
        } catch (DirectorGenereNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{id}"})
    public void deleteBasedOnPath(@PathVariable int id) {
        this.delete(id);
    }
}
