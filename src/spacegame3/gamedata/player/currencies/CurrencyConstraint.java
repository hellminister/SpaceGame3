package spacegame3.gamedata.player.currencies;

import spacegame3.gamedata.GameState;

public abstract class CurrencyConstraint {

    protected CurrencyConstraint next = null;

    public CurrencyConstraint(CurrencyConstraint next){
        this.next = next;
    }


    /**
     * verifies if the given amount is valid and returns the result
     * @param old       the old value
     * @param newValue  the amount to validate
     * @param gameState the state of the game
     * @return the corrected amount
     */
    public final int validate(int old, int newValue, GameState gameState){
        if (next == null){
            return valid(old, newValue, gameState);
        } else {
            return next.validate(valid(old, newValue, gameState), newValue, gameState);
        }
    }

    protected abstract int valid(int old, int newValue, GameState gameState);




}
