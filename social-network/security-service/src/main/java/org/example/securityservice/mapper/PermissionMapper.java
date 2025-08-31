package org.example.securityservice.mapper;


import org.example.securityservice.dto.request.PermissionRequest;
import org.example.securityservice.dto.response.PermissionResponse;
import org.example.securityservice.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
