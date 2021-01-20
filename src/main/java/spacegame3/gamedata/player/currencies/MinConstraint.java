package spacegame3.gamedata.player.currencies;

import spacegame3.gamedata.GameState;

/**
 * constrains with a min value
 * Returns the max between minValue and newValue
 */
public class MinConstraint extends CurrencyConstraint{

    protected final int min;

    public MinConstraint(CurrencyConstraint next, int min) {
        super(next);
        this.min = min;
    }

    @Override
    protected boolean respectConstraint(int newValue, GameState gameState) {
        return newValue >= min;
    }

    /**
     * @param old       the previous value
     * @param newValue  the new value
     * @param gameState
     * @return          the max between minValue and newValue
     */
    @Override
    protected int valid(int old, int newValue, GameState gameState) {
        return Math.max(newValue, min);
    }
}
