package daa_brute_force;

import java.util.ArrayList;

/**A clause is a disjunction of
 * variables.
 *
 * @author Kevin Dittmar
 */
public class Clause 
{
    //private String clause;
    private ArrayList<Boolean> variables;
    public Clause()
    {
        variables = new ArrayList<Boolean>();
    }
    
    //May not be needed.
    public Clause(ArrayList<Boolean> variables)
    {
        this.variables = variables;
    }
    
    public void addVariable(int index, Boolean variable)
    {
        variables.add(index, variable);
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
