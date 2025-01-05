package com.ejbank.dto.transaction;


import java.math.BigDecimal;

public record TransactionResponseDto(long id,
                             String date,
                             String source,
                             String destination,
                             String destination_user,
                             BigDecimal amount,
                             String author,
                             String comment,
                             String state) {
    @Override
    public String toString() {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"id\":").append(id).append(",");
        jsonBuilder.append("\"date\":\"").append(date).append("\",");
        jsonBuilder.append("\"source\":\"").append(source).append("\",");
        jsonBuilder.append("\"destination\":\"").append(destination).append("\",");
        jsonBuilder.append("\"destination_user\":\"").append(destination_user).append("\",");
        jsonBuilder.append("\"amount\":").append(amount).append(",");
        jsonBuilder.append("\"author\":\"").append(author).append("\",");
        if (comment != null) {
            jsonBuilder.append("\"comment\":\"").append(comment).append("\",");
        }
        jsonBuilder.append("\"state\":\"").append(state).append("\"");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
