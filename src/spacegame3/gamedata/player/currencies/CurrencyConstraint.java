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

    /**
     * Verify if the given amount respects this constraint
     * @param newValue  The amount to verify
     * @param gameState The state of the game
     * @return if the given amount respects this constraint
     */
    public final boolean withinLimits(int newValue, GameState gameState){
        if (respectConstraint(newValue, gameState)){
            return next == null || next.respectConstraint(newValue, gameState);
        }
        return false;
    }

    protected abstract boolean respectConstraint(int newValue, GameState gameState);

    protected abstract int valid(int old, int newValue, GameState gameState);




}
