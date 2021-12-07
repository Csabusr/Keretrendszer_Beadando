package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.DirectorDto;
import hu.uni.eku.tzs.controller.dto.DirectorMapper;
import hu.uni.eku.tzs.model.Director;
import hu.uni.eku.tzs.service.DirectorManager;
import hu.uni.eku.tzs.service.exceptions.DirectorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.DirectorNotFoundException;
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

@Api(tags = "Directors")
@RequestMapping("/directors")
@RestController
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorManager directorManager;

    private final DirectorMapper directorMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<DirectorDto> readAllDirectors() {
        return directorManager.readAll()
                .stream()
                .map(directorMapper::director2DirectorDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public DirectorDto create(@Valid @RequestBody DirectorDto recordRequestDto) {
        Director director = directorMapper.directorDto2Director(recordRequestDto);
        try {
            Director recordedDirector = directorManager.record(director);
            return directorMapper.director2DirectorDto(recordedDirector);
        } catch (DirectorAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public DirectorDto update(@Valid @RequestBody DirectorDto updateRequestDto) {
        Director director = directorMapper.directorDto2Director(updateRequestDto);
        Director updatedDirector = directorManager.modify(director);
        return directorMapper.director2DirectorDto(updatedDirector);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam int id) {
        try {
            directorManager.delete(directorManager.readById(id));
        } catch (DirectorNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{id}"})
    public void deleteBasedOnPath(@PathVariable int id) {
        this.delete(id);
    }
}
