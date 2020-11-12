package spacegame3.gamedata.player.currencies;

import spacegame3.gamedata.GameState;

/**
 * constrains with a max value
 * Returns the old value if newValue > max else the new value
 */
public class MaxRevertConstraint extends CurrencyConstraint{
    
    protected final int max;
    
    public MaxRevertConstraint(CurrencyConstraint next, int max) {
        super(next);
        this.max = max;
    }

    /**
     * @param old       the previous value
     * @param newValue  the new value
     * @param gameState
     * @return          the old value if newValue > max else the new value
     */
    @Override
    protected int valid(int old, int newValue, GameState gameState) {
        return newValue > max ? old : newValue;
    }
}
