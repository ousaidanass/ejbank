package com.ejbank.dto.transaction;

import liquibase.datatype.core.TinyIntType;

import java.math.BigDecimal;

public record TransactionDto(long id,
                             String date,
                             String source,
                             String destination,
                             String destination_user,
                             BigDecimal amount,
                             String author,
                             TinyIntType state) {
}
