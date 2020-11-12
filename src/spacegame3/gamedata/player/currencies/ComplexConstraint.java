package spacegame3.gamedata.player.currencies;

import spacegame3.gamedata.GameState;

/**
 * TODO implement this once a scripting language has been decided
 * This class represents complex constraint like when dynamic thresholds are used
 */
public class ComplexConstraint extends CurrencyConstraint{


    private GameState gameState;

    /**
     *
     * @param next              constraint to be evaluated after this one
     * @param constraintString  A string the shows the constraint (format and parsing not yet decided)
     */
    public ComplexConstraint(CurrencyConstraint next, String constraintString) {
        super(next);

        // TODO the parsing code here to generate the constraint
    }

    @Override
    protected int valid(int old, int newValue, GameState gameState) {
        return 0;
    }
}
