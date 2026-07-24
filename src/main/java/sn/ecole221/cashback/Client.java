package sn.ecole221.cashback;

import java.time.LocalDate;

public record Client(
        String userId,
        LocalDate birthDate
) {
}
