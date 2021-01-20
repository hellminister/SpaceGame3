package spacegame3.gamedata.player.currencies;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import spacegame3.gamedata.GameState;

/**
 * This class represents a loan
 * The interest is a rate on 100000  ie: 1% = 1000 per period decided in the AutoPayment
 * This is a loan, should not ever get a negative amount
 */
public class Loan {

    private final int interestRate;
    private final IntegerBinding interest;
    private final IntegerProperty amount;

    private final long interval;        // duration between payments
    private long nextPayment;           // time/date when to pay next payment

    public Loan(int interestRate, int amount, long interval, GameState gameState) {
        this.interestRate = interestRate;
        this.amount = new SimpleIntegerProperty(Math.max(amount, 0));
        interest = this.amount.multiply(interestRate / 100000);

        this.interval = interval;
        nextPayment = gameState.getCurrentTime().getTime() + interval;
    }

    /**
     * @return the interests to be paid
     */
    public int getInterest(){
        return interest.get();
    }

    /**
     * @return the interest rate on a 100000 base (1% = 1000)
     */
    public int getInterestRate() {
        return interestRate;
    }

    /**
     * Makes the given payment to the loan
     * A negative payment actually adds to the loan
     * @param payment the amount paid
     * @return the amount not used (will usually occur if the amount paid is more then the amount owed)
     */
    public int pay(int payment) {
        var newAmount = amount.get() - payment;
        amount.set(Math.max(newAmount, 0));
        return Math.abs(Math.min(newAmount, 0));
    }

    public void payInterest(GameState gameState, Currency currency){
        long dueIn = (gameState.getCurrentTime().getTime() - nextPayment);
        if (dueIn >= 0) {

            // number of interval that passed since last payment
            boolean exact = (dueIn % interval) == 0;
            long nbInterval = (dueIn / interval) + (exact ? 0 : 1);
            int interestDue = Math.toIntExact(getInterest() * nbInterval);

            int old = currency.value();
            currency.subtract(interestDue, gameState);
            int unpaid = old - currency.value();
            pay((-1) * unpaid);

            nextPayment += interval * (nbInterval + (exact ? 1 : 0));
        }
    }

    public boolean fullyPaid(){
        return amount.get() <= 0;
    }

    public ReadOnlyIntegerProperty amountProperty(){
        return amount;
    }
}
