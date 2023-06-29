package com.thang.orderservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserDto {

    private Integer id;
    private String name;
    private Integer balance;
}
