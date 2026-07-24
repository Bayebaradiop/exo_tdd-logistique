package sn.ecole221.cashback;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashbackRewardEngineTest {

    @Mock
    private MonthlyLimitPort monthlyLimitPort;

    @Test
    void shouldReturnOnePercentStandardCashback() {
        Client client = new Client("USER-001", LocalDate.of(1990, 8, 20));
        Transaction transaction = new Transaction(
                client,
                10_000,
                LocalDate.of(2026, 3, 10),
                false,
                Category.STANDARD
        );

        CashbackRewardEngine engine = new CashbackRewardEngine(monthlyLimitPort);

        int cashback = engine.calculate(transaction);

        assertEquals(100, cashback);
    }

    @Test
    void shouldReturnTwoPercentCashbackDuringBirthdayMonth() {
        Client client = new Client("USER-002", LocalDate.of(1990, 5, 3));
        Transaction transaction = new Transaction(
                client,
                10_000,
                LocalDate.of(2026, 5, 15),
                false,
                Category.STANDARD
        );

        CashbackRewardEngine engine = new CashbackRewardEngine(monthlyLimitPort);

        int cashback = engine.calculate(transaction);

        assertEquals(200, cashback);
    }

    @Test
    void shouldDeductForeignFeeFromCashback() {
        Client client = new Client("USER-003", LocalDate.of(1990, 8, 20));
        Transaction transaction = new Transaction(
                client,
                100_000,
                LocalDate.of(2026, 3, 10),
                true,
                Category.STANDARD
        );

        CashbackRewardEngine engine = new CashbackRewardEngine(monthlyLimitPort);

        int cashback = engine.calculate(transaction);

        assertEquals(500, cashback);
    }

    @Test
    void shouldNeverReturnNegativeCashbackWhenForeignFeeExceedsCashback() {
        Client client = new Client("USER-004", LocalDate.of(1990, 8, 20));
        Transaction transaction = new Transaction(
                client,
                10_000,
                LocalDate.of(2026, 3, 10),
                true,
                Category.STANDARD
        );

        CashbackRewardEngine engine = new CashbackRewardEngine(monthlyLimitPort);

        int cashback = engine.calculate(transaction);

        assertEquals(0, cashback);
    }

    @Test
    void shouldCapSupermarketCashbackAtOneThousandEvenDuringBirthdayMonth() {
        Client client = new Client("USER-005", LocalDate.of(1990, 5, 3));
        Transaction transaction = new Transaction(
                client,
                100_000,
                LocalDate.of(2026, 5, 15),
                false,
                Category.SUPERMARKET
        );

        CashbackRewardEngine engine = new CashbackRewardEngine(monthlyLimitPort);

        int cashback = engine.calculate(transaction);

        assertEquals(1000, cashback);
    }

    @Test
    void shouldCapCashbackByRemainingMonthlyLimit() {
        Client client = new Client("USER-006", LocalDate.of(1990, 8, 20));
        Transaction transaction = new Transaction(
                client,
                50_000,
                LocalDate.of(2026, 3, 10),
                false,
                Category.STANDARD
        );
        when(monthlyLimitPort.accumulatedCashbackThisMonth("USER-006")).thenReturn(9_800);

        CashbackRewardEngine engine = new CashbackRewardEngine(monthlyLimitPort);

        int cashback = engine.calculate(transaction);

        assertEquals(200, cashback);
    }
}
