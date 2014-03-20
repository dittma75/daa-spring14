/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dp_solver;
import java.util.ArrayList;
/**
 *
 * @author Kevin
 */
public class Clause 
{
    private ArrayList<Integer> variables;
    
    public Clause()
    {
        variables = new ArrayList<Integer>();
    }
    
    public boolean isEmpty()
    {
        return variables.isEmpty();
    }
    
    public void addVariable(Integer variable)
    {
        variables.add(variable);
    }
    
    public boolean removeVariable(Integer variable)
    {
        return variables.remove(variable);
    }
}
