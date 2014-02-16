package daa_brute_force;

import java.util.HashMap;
import java.util.Set;
import java.math.BigInteger;

/**
 * A clause is a disjunction of
 * variables.
 *
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * @author Andrew Genova
 */
class Clause 
{
    private HashMap<Integer, String> variables;
    static final String NEGATED = "-";
    static final String POSITIVE = "+";
    /**
     * Initializes a HashMap of variables for the Clause.
     * Each variable has an Integer index mapped to a
     * String containing information as to whether the
     * variable is negated or not.
     */
    Clause()
    {
        variables = new HashMap<Integer, String>();
    }
    
    /**
     * Adds a variable to the disjunction Clause.
     * @param index the numeric identifier for the variable.
     * @param positivity determines whether the variable is negated or not.
     */
    void addVariable(int index, String positivity)
    {
        variables.put(index, positivity);
    }
    
    /**
     * Evaluate each variable of a clause until a variable is
     * positive and true, a variable is negated and false, or no variable
     * in the clause fits either of these criteria.
     * @param truthvals the current truth values
     * @return true if this disjunction clause evaluates to true.
     */
    //O(n)
    boolean evaluate(BigInteger truthvals)
    {
        Set<Integer> keys = variables.keySet();
        for (Integer key : keys)
        {
            String variable = variables.get(key);
            boolean value = CNFEval.getTruthValue(truthvals, key.intValue());
            if ((variable.equals("+") && (value)) || 
                (variable.equals("-") && (!value))) 
            {
                
                return true;
            }
        }
        return false;
    }
    
    /**Returns a String representing the variables in this clause and
     * their corresponding positivity values (POSITIVE or NEGATED).
     * @return the String representation of the values of the variables 
     * in this clause.
     */
    public String toString()
    {
        return variables.entrySet().toString();
    }
}
