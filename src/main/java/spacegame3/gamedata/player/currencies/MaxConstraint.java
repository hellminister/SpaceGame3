package spacegame3.gamedata.player.currencies;

import spacegame3.gamedata.GameState;

/**
 * constrains with a max value
 * Returns the min between maxValue and newValue
 */
public class MaxConstraint extends CurrencyConstraint{

    protected final int max;

    public MaxConstraint(CurrencyConstraint next, int max) {
        super(next);
        this.max = max;
    }

    @Override
    protected boolean respectConstraint(int newValue, GameState gameState) {
        return newValue < max;
    }

    /**
     * @param old       the previous value
     * @param newValue  the new value
     * @param gameState the state of the game
     * @return          the min between maxValue and newValue
     */
    @Override
    protected int valid(int old, int newValue, GameState gameState) {
        return Math.min(newValue, max);
    }
}
