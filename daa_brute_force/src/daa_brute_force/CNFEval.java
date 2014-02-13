package daa_brute_force;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**CNFEval takes a file as input, parses it
 * with the Parser class into Clauses, and
 * tests the Clauses for satisfiability.
 *
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * 
 */
public class CNFEval 
{
    /**
     * @param args the command line arguments
     */
    
    private static ArrayList<Clause> clauses = new ArrayList<Clause>();
    
    /**
     * Adds a Clause to be considered when testing
     * the satisfiability.
     * @param clause the Clause to be added.
     */
    public static void addClause(Clause clause)
    {
        clauses.add(clause);
    }
    
    /**
     * Evaluates the current CNF
     * @param truthvals a map of the current truth values 
     * @return true if CNF evaluates to true, false otherwise
     */
    public static boolean evaluate(HashMap<Integer, Boolean> truthvals)
    {
        Set<Integer> keys = truthvals.keySet();
        for(Clause clause : clauses)
        {
            if(clause.evaluate(truthvals) == false) return false;
        }
        
        return true;
    }
    /**
     * Test method for testing without the parser
     */
    private static void mainTest()
    {
        Clause c1 = new Clause();
        c1.addVariable(1, Clause.POSITIVE);
        c1.addVariable(2, Clause.NEGATED);
        clauses.add(c1);
        
        Clause c2 = new Clause();
        c2.addVariable(1, Clause.NEGATED);
        c2.addVariable(2, Clause.NEGATED);
        clauses.add(c2);
        
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        map.put(1, true);
        map.put(2, false);
        System.out.println(evaluate(map));
    }
    
    public static void main(String[] args) 
    {
//        Scanner scanner = new Scanner(System.in);
//        Parser parser = new Parser(scanner);
//        parser.parseFile();
        
        mainTest();
        
        for (int i = 0; i < clauses.size(); i++)
        {
            System.out.println(clauses.get(i));
        }
    }
}
