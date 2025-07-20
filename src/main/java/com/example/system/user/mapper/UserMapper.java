package com.example.system.user.mapper;

import com.example.system.user.dtos.RegisterRequest;
import com.example.system.user.dtos.UserResponse;
import com.example.system.user.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequest dto);

    UserResponse toResponse(User entity);

    @Mapping(target = "password", ignore = true)
// ไม่ทับ hash เดิม
    void updateUser(RegisterRequest dto, @MappingTarget User entity);
}
