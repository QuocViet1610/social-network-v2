package org.example.securityservice.mapper;

import org.example.securityservice.dto.request.RoleRequest;
import org.example.securityservice.dto.response.RoleResponse;
import org.example.securityservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
