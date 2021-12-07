package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.MovieDto;
import hu.uni.eku.tzs.controller.dto.MovieMapper;
import hu.uni.eku.tzs.model.Movie;
import hu.uni.eku.tzs.service.MovieManager;
import hu.uni.eku.tzs.service.exceptions.MovieAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieNotFoundException;
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

@Api(tags = "Movies")
@RequestMapping("/movies")
@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieManager movieManager;

    private final MovieMapper movieMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<MovieDto> readAllDirectors() {
        return movieManager.readAll()
                .stream()
                .map(movieMapper::movie2MovieDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public MovieDto create(@Valid @RequestBody MovieDto recordRequestDto) {
        Movie movie = movieMapper.movieDto2Movie(recordRequestDto);
        try {
            Movie recordedMovie = movieManager.record(movie);
            return movieMapper.movie2MovieDto(recordedMovie);
        } catch (MovieAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public MovieDto update(@Valid @RequestBody MovieDto updateRequestDto) {
        Movie movie = movieMapper.movieDto2Movie(updateRequestDto);
        Movie updatedMovie = movieManager.modify(movie);
        return movieMapper.movie2MovieDto(updatedMovie);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam int id) {
        try {
            movieManager.delete(movieManager.readById(id));
        } catch (MovieNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{id}"})
    public void deleteBasedOnPath(@PathVariable int id) {
        this.delete(id);
    }
}
