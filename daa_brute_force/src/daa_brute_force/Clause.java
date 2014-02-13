package daa_brute_force;

import java.util.HashMap;
/**A clause is a disjunction of
 * variables.
 *
 * @author Kevin Dittmar
 */
public class Clause 
{
    //private String clause;
    private HashMap<Integer, String> variables;
    public static final String NEGATED = "-";
    public static final String POSITIVE = "+";
    /**
     * Initializes a HashMap of variables for the Clause.
     * Each variable has an Integer index mapped to a
     * String containing information as to whether the
     * variable is negated or not.
     */
    public Clause()
    {
        variables = new HashMap<Integer, String>();
    }
    
//    //May not be needed.
//    public Clause(ArrayList<Boolean> variables)
//    {
//        this.variables = variables;
//    }
    
    /**
     * Adds a variable to the disjunction Clause.
     * @param index the numeric identifier for the variable.
     * @param positivity determines whether the variable is negated or not.
     */
    public void addVariable(int index, String positivity)
    {
        variables.put(index, positivity);
    }
    
    /**
     * @return true if this clause evaluates
     * to true.
     */
    public boolean evaluate()
    {
        return false;
//        if (variables.containsValue(Boolean.TRUE))
//        {
//            return true;
//        }
//        return false;
    }
    
    /**
     * @return the values of the variables in
     * this clause delimited by commas.
     */
    public String toString()
    {
        return variables.entrySet().toString();
    }
}
