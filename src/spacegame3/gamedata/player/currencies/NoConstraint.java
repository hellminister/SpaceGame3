package spacegame3.gamedata.player.currencies;

import spacegame3.gamedata.GameState;

/**
 * When there's no constraint
 */
public class NoConstraint extends CurrencyConstraint{

    public NoConstraint() {
        super(null);
    }

    @Override
    protected boolean respectConstraint(int newValue, GameState gameState) {
        return true;
    }

    /**
     * @param old       the previous value
     * @param newValue  the new value
     * @param gameState the state of the game
     * @return always the new Value
     */
    @Override
    protected int valid(int old, int newValue, GameState gameState) {
        return newValue;
    }
}
