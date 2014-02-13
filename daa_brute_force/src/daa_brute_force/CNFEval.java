package daa_brute_force;

import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author Kevin Dittmar
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
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser(scanner);
        parser.parseFile();
        
        for (int i = 0; i < clauses.size(); i++)
        {
            System.out.println(clauses.get(i));
        }
    }
}
