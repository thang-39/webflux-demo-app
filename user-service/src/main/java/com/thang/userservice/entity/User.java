package com.thang.userservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@NoArgsConstructor
@Table("users")
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer balance;
}
