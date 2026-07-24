package sn.ecole221.cashback;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CashbackRewardEngineTest {

    @Test
    void shouldReturnOnePercentStandardCashback() {
        Transaction transaction = new Transaction("USER-001", 10_000);

        CashbackRewardEngine engine = new CashbackRewardEngine();

        int cashback = engine.calculate(transaction);

        assertEquals(100, cashback);
    }
}
