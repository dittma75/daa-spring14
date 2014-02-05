package daa_brute_force;

import java.util.HashMap;

/**A clause is a disjunction of
 * variables.
 *
 * @author Kevin Dittmar
 */
public class Clause 
{
    private String clause;
    private String[] variables;
    public Clause(String clause)
    {
        this.clause = clause;
    }
    
    /**
     * @return true if this clause evaluates
     * to true when all variables are 
     */
    public boolean evaluate()
    {
        //stub
        return false;
    }
}
