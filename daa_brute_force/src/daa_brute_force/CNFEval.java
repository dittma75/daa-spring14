package daa_brute_force;

import java.util.Scanner;
import java.util.ArrayList;
/**CNFEval takes a file as input, parses it
 * with the Parser class into Clauses, and
 * tests the Clauses for satisfiability.
 *
 * @author Kevin Dittmar
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
    
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser(scanner);
        parser.parseFile();
        
        for (int i = 0; i < clauses.size(); i++)
        {
            System.out.println(clauses.get(i));
        }
    }
}
