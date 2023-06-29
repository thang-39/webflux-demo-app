package com.thang.userservice.controller;

import com.thang.userservice.dto.TransactionRequestDto;
import com.thang.userservice.dto.TransactionResponseDto;
import com.thang.userservice.entity.UserTransaction;
import com.thang.userservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user/transaction")
@RequiredArgsConstructor
public class UserTransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Mono<TransactionResponseDto> createTransaction(@RequestBody Mono<TransactionRequestDto> requestDtoMono) {
        return requestDtoMono.flatMap(transactionService::createTransaction);
    }

    @GetMapping
    public Flux<UserTransaction> getTransactionsByUserId(@RequestParam("userId") int userId) {
        return transactionService.getTransactionsByUserId(userId);
    }
}
