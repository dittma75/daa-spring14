package daa_brute_force;

import java.util.Scanner;
/**Parses a file from System.in for use in the
 * evaluation of a CNF formula.
 * 
 * @author Kevin Dittmar
 */
public class Parser 
{
    private int numberOfVariables;
    private int numberOfClauses;
    private Scanner scanner;
    
    /**
     * @param Scanner scanner should have
     * input in the proper format for CNF
     * evaluation.
     */
    public Parser(Scanner scanner)
    {
        this.scanner = scanner;
    }
    
    /**
     * @return number of clauses as given by the
     * parsed file
     */
    public int getNumberOfVariables()
    {
        return numberOfVariables;
    }
    
    /**
     * @return number of clauses as given by the
     * parsed file.
     */
    public int getNumberOfClauses()
    {
        return numberOfClauses;
    }
    
    /**Parse the file on the Scanner as
     * input for CNF evaluation.
     */
    void parseFile()
    {
        String nextLine = "";
                
        //O(n^2)
        while (scanner.hasNextLine())
        {
            nextLine = scanner.nextLine();
            nextLine = formatInput(nextLine);
            parseInput(nextLine);
        }
    }
    
    /* @return given String with no duplicate or
     * leading spaces.  This is the correct format
     * for parsing.
     */
    private String formatInput(String nextLine)
    {
        //Replace all cases with two or more spaces with one space
        nextLine = nextLine.replaceAll("   *", " ");
        //If the line starts with a space, remove it
        if (nextLine.startsWith(" "))
        {
            nextLine = nextLine.replaceFirst(" ", "");
        }
        return nextLine;
    }
    
    /* Determines the meaning of each part of
     * the input.
     * Starts with c:       comment to be ignored.
     * Starts with p cnf:   clause and variable information line.
     * Otherwise:           data to be used to build clauses.
     */
    private void parseInput(String nextLine)
    {
        String[] splitLine;
        Clause newClause = new Clause();
        if (nextLine.startsWith("c"))
        {
            //This is a comment; do nothing.
        }
        //The next line contains the number of clauses and number of variables.
        else if (nextLine.startsWith("p cnf "))
        {
            //Remove data input descriptor.
            nextLine = nextLine.replace("p cnf ", "");
            splitLine = nextLine.split(" ");
            numberOfVariables = Integer.parseInt(splitLine[0]);
            numberOfClauses = Integer.parseInt(splitLine[1]);
        }
        //The next line contains variable assignments.
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
                            Clause.NEGATED);
                }
                else
                {
                    /*Adds the variable as true at its position (which is
                     *the first char of the String) in the ArrayList.
                     */
                    newClause.addVariable(
                            Integer.parseInt(splitLine[i]), 
                            Clause.POSITIVE);
                }
            }
        }
    }
}
