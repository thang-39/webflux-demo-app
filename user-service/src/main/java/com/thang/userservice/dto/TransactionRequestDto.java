package com.thang.userservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class TransactionRequestDto {

    private Integer userId;
    private Integer amount;
}
