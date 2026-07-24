package sn.ecole221.cashback;

import java.time.LocalDate;

public record Transaction(
        Client client,
        int amount,
        LocalDate transactionDate,
        boolean isForeign,
        Category category
) {
}
