package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto role2RoleDto(Role role);

    Role roleDto2Role(RoleDto dto);
}
