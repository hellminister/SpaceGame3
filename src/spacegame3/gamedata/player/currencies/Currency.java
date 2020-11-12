package spacegame3.gamedata.player.currencies;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import spacegame3.gamedata.GameState;

/**
 * represents a numerical (integer) attribute of the player
 * This value can be constrained is needed
 */
public class Currency {

    private final IntegerProperty value;
    private final CurrencyConstraint constraint;

    /**
     * @param transferLimits the constraint on this value
     */
    public Currency(CurrencyConstraint transferLimits){
        constraint = transferLimits;
        value = new SimpleIntegerProperty(0);
    }

    /**
     * Sets the value of this currency to the given value respective to the constraints
     * @param amount    the new value
     * @param gameState the state of the game
     */
    public void set(int amount, GameState gameState){
        value.set(constraint.validate(value.get(), amount, gameState));
    }

    /**
     * Adds the given amount to this currency
     * @param amount    the amount to add
     * @param gameState the state of the game
     */
    public void add(int amount, GameState gameState) {
        var old = value.get();
        value.set(constraint.validate(old, old + amount, gameState));
    }

    /**
     * Subtracts the given amount to this currency
     * @param amount    the amount to subtract
     * @param gameState the state of the game
     */
    public void subtract(int amount, GameState gameState) {
        var old = value.get();
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
}
