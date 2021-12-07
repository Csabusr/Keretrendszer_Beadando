package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.RoleDto;
import hu.uni.eku.tzs.controller.dto.RoleMapper;
import hu.uni.eku.tzs.model.Role;
import hu.uni.eku.tzs.service.RoleManager;
import hu.uni.eku.tzs.service.exceptions.RoleAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.RoleNotFoundException;
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

@Api(tags = "Roles")
@RequestMapping("/roles")
@RestController
@RequiredArgsConstructor
public class RoleController {

    private final RoleManager roleManager;

    private final RoleMapper roleMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<RoleDto> readAllDirectors() {
        return roleManager.readAll()
                .stream()
                .map(roleMapper::role2RoleDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public RoleDto create(@Valid @RequestBody RoleDto recordRequestDto) {
        Role role = roleMapper.roleDto2Role(recordRequestDto);
        try {
            Role recordedRole = roleManager.record(role);
            return roleMapper.role2RoleDto(recordedRole);
        } catch (RoleAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public RoleDto update(@Valid @RequestBody RoleDto updateRequestDto) {
        Role role = roleMapper.roleDto2Role(updateRequestDto);
        Role updatedRole = roleManager.modify(role);
        return roleMapper.role2RoleDto(updatedRole);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam int id) {
        try {
            roleManager.delete(roleManager.readById(id));
        } catch (RoleNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{id}"})
    public void deleteBasedOnPath(@PathVariable int id) {
        this.delete(id);
    }
}
