package spacegame3.gamedata.player.currencies;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import spacegame3.gamedata.GameState;

import java.util.LinkedList;
import java.util.List;

/**
 * represents a numerical (integer) attribute of the player
 * This value can be constrained is needed
 */
public class Currency {

    private final IntegerProperty value;
    private final CurrencyConstraint constraint;
    private final List<Loan> loans;

    /**
     * @param transferLimits the constraint on this value
     */
    public Currency(CurrencyConstraint transferLimits){
        constraint = transferLimits;
        value = new SimpleIntegerProperty(0);
        loans = new LinkedList<>();
    }

    /**
     * Sets the value of this currency to the given value respective to the constraints
     * @param amount    the new value
     * @param gameState the state of the game
     */
    public void set(int amount, GameState gameState){
        value.set(constraint.validate(value(), amount, gameState));
    }

    /**
     * Adds the given amount to this currency
     * @param amount    the amount to add
     * @param gameState the state of the game
     */
    public void add(int amount, GameState gameState) {
        var old = value();
        value.set(constraint.validate(old, old + amount, gameState));
    }

    public boolean addLoan(int interestRate, int amount, long interval, GameState gameState){
        if (acceptableAdd(amount, gameState)) {
            Loan loan = new Loan(interestRate, amount, interval, gameState);
            loans.add(loan);
            add(amount, gameState);
        }
        return false;
    }

    public List<Loan> getLoans(){
        return loans;
    }

    public void treatLoans(GameState gameState){
        loans.forEach(loan -> loan.payInterest(gameState, this));
        loans.removeIf(Loan::fullyPaid);
    }

    /**
     * Subtracts the given amount to this currency
     * @param amount    the amount to subtract
     * @param gameState the state of the game
     */
    public void subtract(int amount, GameState gameState) {
        var old = value();
        value.set(constraint.validate(old, old - amount, gameState));
    }

    /**
     * @return how much is own in this currency
     */
    public int value() {
        return value.get();
    }

    /**
     * @return a read only view on the value of the currency
     */
    public ReadOnlyIntegerProperty property() {
        return value;
    }

    /**
     * Verify if subtracting this amount keeps the currency within acceptable range
     * @param amount    The amount to subtract
     * @param gameState The state of the game
     * @return if the resulting amount is within acceptable range
     */
    public boolean acceptableSubtract(int amount, GameState gameState) {
        return constraint.withinLimits(value() - amount, gameState);
    }

    /**
     * Verify if adding this amount keeps the currency within acceptable range
     * @param amount    The amount to add
     * @param gameState The state of the game
     * @return if the resulting amount is within acceptable range
     */
    public boolean acceptableAdd(int amount, GameState gameState) {
        return constraint.withinLimits(value() + amount, gameState);
    }

    /**
     * Verify if this amount is within acceptable range
     * @param amount    The amount
     * @param gameState The state of the game
     * @return if the amount is within acceptable range
     */
    public boolean acceptable(int amount, GameState gameState) {
        return constraint.withinLimits(amount, gameState);
    }
}
