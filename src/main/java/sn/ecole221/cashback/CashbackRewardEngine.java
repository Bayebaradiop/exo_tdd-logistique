package sn.ecole221.cashback;

public class CashbackRewardEngine {

    private static final int STANDARD_CASHBACK_RATE_PERCENT = 1;
    private static final int BIRTHDAY_CASHBACK_RATE_PERCENT = 2;
    private static final int FOREIGN_TRANSACTION_FEE = 500;
    private static final int SUPERMARKET_CASHBACK_CAP = 1000;
    private static final int MONTHLY_CASHBACK_CAP = 10_000;

    private final MonthlyLimitPort monthlyLimitPort;

    public CashbackRewardEngine(MonthlyLimitPort monthlyLimitPort) {
        this.monthlyLimitPort = monthlyLimitPort;
    }

    public int calculate(Transaction transaction) {
        int ratePercent = isBirthdayMonth(transaction)
                ? BIRTHDAY_CASHBACK_RATE_PERCENT
                : STANDARD_CASHBACK_RATE_PERCENT;

        int cashback = transaction.amount() * ratePercent / 100;

        if (isSupermarket(transaction)) {
            cashback = Math.min(cashback, SUPERMARKET_CASHBACK_CAP);
        }

        if (transaction.isForeign()) {
            cashback = Math.max(0, cashback - FOREIGN_TRANSACTION_FEE);
        }

        int remainingMonthlyRoom = MONTHLY_CASHBACK_CAP
                - monthlyLimitPort.accumulatedCashbackThisMonth(transaction.client().userId());
        cashback = Math.max(0, Math.min(cashback, remainingMonthlyRoom));

        return cashback;
    }

    private boolean isBirthdayMonth(Transaction transaction) {
        return transaction.transactionDate().getMonth() == transaction.client().birthDate().getMonth();
    }

    private boolean isSupermarket(Transaction transaction) {
        return transaction.category() == Category.SUPERMARKET;
    }
}
