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
                LocalDate.of(1990, 8, 20),
                false,
                "Standard"
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
                LocalDate.of(1990, 5, 3),
                false,
                "Standard"
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
                true,
                "Standard"
        );

        CashbackRewardEngine engine = new CashbackRewardEngine();

        int cashback = engine.calculate(transaction);

        assertEquals(500, cashback);
    }

    @Test
    void shouldNeverReturnNegativeCashbackWhenForeignFeeExceedsCashback() {
        Transaction transaction = new Transaction(
                "USER-004",
                10_000,
                LocalDate.of(2026, 3, 10),
                LocalDate.of(1990, 8, 20),
                true,
                "Standard"
        );

        CashbackRewardEngine engine = new CashbackRewardEngine();

        int cashback = engine.calculate(transaction);

        assertEquals(0, cashback);
    }

    @Test
    void shouldCapSupermarketCashbackAtOneThousandEvenDuringBirthdayMonth() {
        Transaction transaction = new Transaction(
                "USER-005",
                100_000,
                LocalDate.of(2026, 5, 15),
                LocalDate.of(1990, 5, 3),
                false,
                "Supermarché"
        );

        CashbackRewardEngine engine = new CashbackRewardEngine();

        int cashback = engine.calculate(transaction);

        assertEquals(1000, cashback);
    }
}
