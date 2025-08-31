package org.example.securityservice.mapper;

import org.example.securityservice.dto.request.ProfileCreationRequest;
import org.example.securityservice.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);

}
