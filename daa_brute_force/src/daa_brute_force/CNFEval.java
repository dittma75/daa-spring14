package daa_brute_force;

import java.util.ArrayList;
/**
 *
 * @author Kevin
 */
public class CNFEval 
{
    /**
     * @param args the command line arguments
     */
    
    private static ArrayList<Clause> clauses = new ArrayList<Clause>();
    
    public static void addClause(Clause clause)
    {
        clauses.add(clause);
    }
    
    public static void main(String[] args) 
    {
        Parser parser = new Parser();
        parser.parseFile();
        
        for (int i = 0; i < clauses.size(); i++)
        {
            System.out.println(clauses.get(i));
        }
    }
}
