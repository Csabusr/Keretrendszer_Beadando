package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ActorDto;
import hu.uni.eku.tzs.controller.dto.ActorMapper;
import hu.uni.eku.tzs.model.Actor;
import hu.uni.eku.tzs.service.ActorManager;
import hu.uni.eku.tzs.service.exceptions.ActorAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ActorNotFoundException;
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

@Api(tags = "Actors")
@RequestMapping("/actors")
@RestController
@RequiredArgsConstructor
public class ActorController {

    private final ActorManager actorManager;

    private final ActorMapper actorMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<ActorDto> readAllActors() {
        return actorManager.readAll()
                .stream()
                .map(actorMapper::actor2ActorDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public ActorDto create(@Valid @RequestBody ActorDto recordRequestDto) {
        Actor actor = actorMapper.actorDto2Actor(recordRequestDto);
        try {
            Actor recordedActor = actorManager.record(actor);
            return actorMapper.actor2ActorDto(recordedActor);
        } catch (ActorAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public ActorDto update(@Valid @RequestBody ActorDto updateRequestDto) {
        Actor actor = actorMapper.actorDto2Actor(updateRequestDto);
        Actor updatedActor = actorManager.modify(actor);
        return actorMapper.actor2ActorDto(updatedActor);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam int id) {
        try {
            actorManager.delete(actorManager.readById(id));
        } catch (ActorNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{id}"})
    public void deleteBasedOnPath(@PathVariable int id) {
        this.delete(id);
    }

}
