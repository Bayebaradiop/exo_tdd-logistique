package sn.ecole221.cashback;

public class CashbackRewardEngine {

    private static final int STANDARD_CASHBACK_RATE_PERCENT = 1;
    private static final int BIRTHDAY_CASHBACK_RATE_PERCENT = 2;

    public int calculate(Transaction transaction) {
        int ratePercent = isBirthdayMonth(transaction)
                ? BIRTHDAY_CASHBACK_RATE_PERCENT
                : STANDARD_CASHBACK_RATE_PERCENT;

        return transaction.amount() * ratePercent / 100;
    }

    private boolean isBirthdayMonth(Transaction transaction) {
        return transaction.transactionDate().getMonth() == transaction.birthDate().getMonth();
    }
}
