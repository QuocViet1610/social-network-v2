package org.example.securityservice.mapper;


import org.example.securityservice.dto.request.UserCreationRequest;
import org.example.securityservice.dto.request.UserUpdateRequest;
import org.example.securityservice.dto.response.UserResponse;
import org.example.securityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
