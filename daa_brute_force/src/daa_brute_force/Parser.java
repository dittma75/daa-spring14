package daa_brute_force;

import java.util.Scanner;
/**Parses a file for use in the
 * evaluation of a cnf formula.
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
        while (scanner.hasNextLine())
        {
            nextLine = scanner.nextLine();
            if (nextLine.charAt(0) == 'c')
            {
                //This is a comment; do nothing.
            }
            if (nextLine.startsWith("p cnf"))
            {
                splitLine = nextLine.split(" ");
                numberOfVariables = Integer.parseInt(splitLine[2]);
                numberOfClauses = Integer.parseInt(splitLine[3]);
            }
            //The line is variable assignments.
            else
            {
                splitLine = nextLine.split(" ");
                for (int i = 0; i < splitLine.length; i++)
                {
                    if (splitLine[i].charAt(0) == '-')
                    {
                        //negated variable
                    }
                    else
                    {
                        //positive variable
                    }
                }
            }
        }
    }
}
