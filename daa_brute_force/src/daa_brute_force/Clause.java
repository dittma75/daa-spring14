package daa_brute_force;

//import java.util.HashMap;
//import java.util.Set;
import java.util.ArrayList;
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
    //private HashMap<Integer, String> variables;
    private ArrayList<Variable> variables;
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
        variables = new ArrayList<Variable>();
    }
    
    /**
     * Adds a variable to the disjunction Clause.
     * @param index the numeric identifier for the variable.
     * @param positivity determines whether the variable is negated or not.
     */
    void addVariable(Variable variable)
    {
        variables.add(variable);
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
        for (Variable variable : variables)
        {
            
            boolean value = CNFEval.getTruthValue(truthvals, variable.getID());
            if ((variable.getPositivity() == true) && (value == true) || 
                (variable.getPositivity() == false && (value == false))) 
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
        return variables.toString();
    }
}
