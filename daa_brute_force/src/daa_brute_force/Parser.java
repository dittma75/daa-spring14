package daa_brute_force;

import java.util.Scanner;
/**Parses a file for use in the
 * evaluation of a cnf formula.
 * This parser has not been tested, and
 * it may not work at all.  I just wanted
 * to get some ideas out there.
 * 
 * @author Kevin Dittmar
 */
public class Parser 
{
    private int numberOfVariables;
    private int numberOfClauses;
    private String[] clauses;
    private Scanner scanner;
    
    public Parser()
    {
        scanner = new Scanner(System.in);
    }
    
    void parseFile()
    {
        String nextLine = "";
        String[] splitLine;
        
        //O(n^2)
        while (scanner.hasNextLine())
        {
            nextLine = scanner.nextLine();
            Clause newClause = new Clause();
            
            if (nextLine.charAt(0) == 'c')
            {
                //This is a comment; do nothing.
            }
            else if (nextLine.startsWith("p cnf"))
            {
                splitLine = nextLine.split(" ");
                numberOfVariables = Integer.parseInt(splitLine[2]);
                numberOfClauses = Integer.parseInt(splitLine[3]);
            }
            //The line is variable assignments.
            else
            {
                splitLine = nextLine.split(" ");
                //O(n)
                for (int i = 0; i < splitLine.length; i++)
                {
                    if (Integer.parseInt(splitLine[i]) == 0)
                    {
                        CNFEval.addClause(newClause);
                    }
                    else if (splitLine[i].startsWith("-"))
                    {
                        /*Adds the variable as false at its position (which is
                         *the second char of the String) in the ArrayList.
                         */
                        newClause.addVariable(
                                Math.abs(Integer.parseInt(splitLine[i])),
                                "NEGATED");
                    }
                    else
                    {
                        /*Adds the variable as true at its position (which is
                         *the first char of the String) in the ArrayList.
                         */
                        newClause.addVariable(
                                Integer.parseInt(splitLine[i]), 
                                "POSITIVE");
                    }
                }
            }
        }
    }
}
