package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.MovieGenreDto;
import hu.uni.eku.tzs.controller.dto.MovieGenreMapper;
import hu.uni.eku.tzs.model.MovieGenre;
import hu.uni.eku.tzs.service.MovieGenreManager;
import hu.uni.eku.tzs.service.exceptions.MovieGenreAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.MovieGenreNotFoundException;
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

@Api(tags = "Movies_Genres")
@RequestMapping("/movies_genres")
@RestController
@RequiredArgsConstructor
public class MovieGenreContorller {

    private final MovieGenreManager movieGenreManager;

    private final MovieGenreMapper movieGenreMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<MovieGenreDto> readAllDirectors() {
        return movieGenreManager.readAll()
                .stream()
                .map(movieGenreMapper::movieGenre2MovieGenreDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public MovieGenreDto create(@Valid @RequestBody MovieGenreDto recordRequestDto) {
        MovieGenre movieGenre = movieGenreMapper.movieGenreDto2MovieGenre(recordRequestDto);
        try {
            MovieGenre recordedMovie = movieGenreManager.record(movieGenre);
            return movieGenreMapper.movieGenre2MovieGenreDto(recordedMovie);
        } catch (MovieGenreAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public MovieGenreDto update(@Valid @RequestBody MovieGenreDto updateRequestDto) {
        MovieGenre movieGenre = movieGenreMapper.movieGenreDto2MovieGenre(updateRequestDto);
        MovieGenre updatedMovieGenre = movieGenreManager.modify(movieGenre);
        return movieGenreMapper.movieGenre2MovieGenreDto(updatedMovieGenre);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam int id) {
        try {
            movieGenreManager.delete(movieGenreManager.readById(id));
        } catch (MovieGenreNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{id}"})
    public void deleteBasedOnPath(@PathVariable int id) {
        this.delete(id);
    }
}
