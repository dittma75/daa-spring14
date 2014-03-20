package dp_solver;
import java.util.LinkedList;
/**
 *
 * @author Kevin Dittmar
 */
public class Formula
{        
    private LinkedList<Clause> clauses;
    public Formula()
    {
        clauses = new LinkedList<Clause>();
    }
    
    boolean isEmpty()
    {
        return clauses.isEmpty();
    }
    
    boolean hasEmptyClause()
    {
        for (Clause clause : clauses)
        {
            if (clause.isEmpty())
            {
                return true;
            }
        }
        return false;
    }
    
    public void addClause(Clause clause)
    {
        clauses.add(clause);
    }
    
    public boolean removeClause(Clause clause)
    {
        return clauses.remove(clause);
    }
}
