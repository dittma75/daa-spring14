package daa_brute_force;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**A clause is a disjunction of
 * variables.
 *
 * @author Kevin Dittmar
 */
public class Clause 
{
    //private String clause;
    private HashMap<Integer, String> variables;
    public Clause()
    {
        variables = new HashMap<Integer, String>();
    }
    
//    //May not be needed.
//    public Clause(ArrayList<Boolean> variables)
//    {
//        this.variables = variables;
//    }
    
    public void addVariable(int index, String variable)
    {
        variables.put(index, variable);
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
     * this clause delimited by commas
     */
    public String toString()
    {
        return variables.values().toString();
    }
}
