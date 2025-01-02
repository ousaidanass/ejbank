package com.ejbank.dto.transaction;

import com.ejbank.dto.TransactionDispatchDto;

import java.math.BigDecimal;

public record TransactionDto(long id,
                             String date,
                             String source,
                             String destination,
                             String destination_user,
                             BigDecimal amount,
                             String author,
                             TransactionState state) implements TransactionDispatchDto {
}
