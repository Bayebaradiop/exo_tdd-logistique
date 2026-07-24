package sn.ecole221.cashback;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CashbackRewardEngineTest {

    @Test
    void shouldReturnOnePercentStandardCashback() {
        Transaction transaction = new Transaction(
                "USER-001",
                10_000,
                LocalDate.of(2026, 3, 10),
                LocalDate.of(1990, 8, 20)
        );

        CashbackRewardEngine engine = new CashbackRewardEngine();

        int cashback = engine.calculate(transaction);

        assertEquals(100, cashback);
    }

    @Test
    void shouldReturnTwoPercentCashbackDuringBirthdayMonth() {
        Transaction transaction = new Transaction(
                "USER-002",
                10_000,
                LocalDate.of(2026, 5, 15),
                LocalDate.of(1990, 5, 3)
        );

        CashbackRewardEngine engine = new CashbackRewardEngine();

        int cashback = engine.calculate(transaction);

        assertEquals(200, cashback);
    }

    @Test
    void shouldDeductForeignFeeFromCashback() {
        Transaction transaction = new Transaction(
                "USER-003",
                100_000,
                LocalDate.of(2026, 3, 10),
                LocalDate.of(1990, 8, 20),
                true
        );

        CashbackRewardEngine engine = new CashbackRewardEngine();

        int cashback = engine.calculate(transaction);

        assertEquals(500, cashback);
    }
}
