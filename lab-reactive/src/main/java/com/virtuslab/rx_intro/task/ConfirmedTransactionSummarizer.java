package com.virtuslab.rx_intro.task;


import io.reactivex.Observable;
import io.reactivex.Single;

import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * ConfirmedTransactionSummarizer is responsible for calculation of total confirmed transactions value.
 * HINT:
 * - Use zip operator to match transactions with confirmations. They will appear in order
 * - Filter only confirmed
 * - Aggregate value of confirmed transactions using reduce operator
 *
 * HINT2:
 * - add error handling which will wrap an error into SummarizationException
 *
 */
class ConfirmedTransactionSummarizer {

    private final Supplier<Observable<Transaction>> transactions;
    private final Supplier<Observable<Confirmation>> confirmations;

    ConfirmedTransactionSummarizer(Supplier<Observable<Transaction>> transactions,
                                   Supplier<Observable<Confirmation>> confirmations) {
        this.transactions = transactions;
        this.confirmations = confirmations;
    }

    Single<BigDecimal> summarizeConfirmedTransactions() {
        Observable<Transaction> transactionObservable = transactions.get();
        Observable<Confirmation> confirmationObservable = confirmations.get();

        return transactionObservable
                .zipWith(confirmationObservable, Pair::new)
                .filter(pair -> pair.successor.isConfirmed)
                .reduce(BigDecimal.ZERO, (result, pair) -> result.add(pair.predecessor.value))
                .onErrorResumeNext(ex -> Single.error(new SummarizationException(ex.getMessage())));
    }

    static class SummarizationException extends RuntimeException {
        public SummarizationException(String message) {
            super(message);
        }
    }
}
