package sn.ecole221.cashback;

public class CashbackRewardEngine {

    private static final int STANDARD_CASHBACK_RATE_PERCENT = 1;

    public int calculate(Transaction transaction) {
        return transaction.amount() * STANDARD_CASHBACK_RATE_PERCENT / 100;
    }
}
