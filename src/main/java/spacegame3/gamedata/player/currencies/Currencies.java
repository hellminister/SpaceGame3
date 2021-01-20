package spacegame3.gamedata.player.currencies;


import java.util.Map;
import javafx.beans.property.ReadOnlyIntegerProperty;
import spacegame3.gamedata.GameState;


/**
 * This class contains all the type of currencies and their amount that the player possesses
 *
 * This is not limited to money.  It also includes things like reputation, experience points even hit points if the story calls for that.
 *
 * All currencies are Integer type
 *
 */
public class Currencies {

    private final Map<String, Currency> currencies;

    public Currencies(Map<String, Currency> currencies){
        this.currencies = currencies;
    }

    /**
     * Adds a certain amount to the given currency
     * @param type      the name of the currency
     * @param amount    the amount to add
     * @param gameState the state of the game
     */
    public void add(String type, int amount, GameState gameState){
        currencies.get(type).add(amount, gameState);
    }

    /**
     * Adds a certain amount to the given currency
     * @param type      the name of the currency
     * @param amount    the amount to subtract
     * @param gameState the state of the game
     */
    public void subtract(String type, int amount, GameState gameState){
        currencies.get(type).subtract(amount, gameState);
    }

    /**
     * Adds a certain amount to the given currency
     * @param type      the name of the currency
     * @param value    the new value
     * @param gameState the state of the game
     */
    public void set(String type, int value, GameState gameState) {
        currencies.get(type).set(value, gameState);
    }

    /**
     * @param type the name of the currency
     * @return     how much is own in the asked currency
     */
    public int balance(String type){
        return currencies.get(type).value();
    }

    /**
     * @param type the name of the currency
     * @return     a read only view on the value of the currency
     */
    public ReadOnlyIntegerProperty property(String type){
        return currencies.get(type).property();
    }

}
