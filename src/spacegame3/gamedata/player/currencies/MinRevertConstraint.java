package spacegame3.gamedata.player.currencies;

import spacegame3.gamedata.GameState;

/**
 * constrains with a max value
 * Returns the old value if newValue < min else the new value
 */
public class MinRevertConstraint extends CurrencyConstraint{

    protected final int min;

    public MinRevertConstraint(CurrencyConstraint next, int min) {
        super(next);
        this.min = min;
    }

    /**
     * @param old       the previous value
     * @param newValue  the new value
     * @param gameState
     * @return          the old value if newValue < min else the new value
     */
    @Override
    protected int valid(int old, int newValue, GameState gameState) {
        return newValue < min ? old : newValue;
    }
}
