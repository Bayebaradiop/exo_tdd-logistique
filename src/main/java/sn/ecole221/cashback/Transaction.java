package sn.ecole221.cashback;

import java.time.LocalDate;

public record Transaction(
        String userId,
        int amount,
        LocalDate transactionDate,
        LocalDate birthDate,
        boolean isForeign,
        String category
) {
}
