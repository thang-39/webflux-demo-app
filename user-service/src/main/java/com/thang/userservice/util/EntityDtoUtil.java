package com.thang.userservice.util;

import com.thang.userservice.dto.UserDto;
import com.thang.userservice.entity.User;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(user,dto);
        return dto;
    }

    public static User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return user;
    }
}
