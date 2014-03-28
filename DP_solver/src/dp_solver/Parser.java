package dp_solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Parses a file from System.in for use in the
 * evaluation of a CNF formula.
 * 
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * @author Andrew Genova
 */
class Parser 
{
    private static Scanner scanner;
    private int clauseCounter = 0;
    private static Formula formula;
    /**
     * @param scanner should have input in the proper format for CNF
     * evaluation.
     */
    Parser(File input)
    {
        try 
        {
            scanner = new Scanner(input);
            formula = new Formula();
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    /**
     * Parse the file on the Scanner as
     * input for CNF evaluation.
     */
    Formula parseFile()
    {
        String nextLine = "";
                
        while (scanner.hasNextLine())
        {
            nextLine = scanner.nextLine();
            nextLine = formatInput(nextLine);
            parseInput(nextLine);
        }
        return formula;
    }
    
    /**Puts the file data in the correct format for
     * CNF parsing.
     * @return given String with no duplicate or
     * leading spaces.
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
    
    /**
     * Determines the meaning of each part of
     * the input.
     * Lines starting with c are comments to be ignored.
     * A line starting with p cnf identifies the file as CNFEval input
     * and will provide the number of clauses and number of variables.
     * All other lines specify a disjunctive clause to be added.
     */
    private void parseInput(String nextLine)
    {
        String[] splitLine;
        int[] newClause;

        if (nextLine.startsWith("c"))
        {
            //This is a comment; do nothing.
        }
        /*The next line contains the number of clauses 
         *and number of variables.
         */
        else if (nextLine.startsWith("p cnf "))
        {
            //Remove data input descriptor.
            nextLine = nextLine.replace("p cnf ", "");
            splitLine = nextLine.split(" ");
            int numberOfVariables = Integer.parseInt(splitLine[0]);
            int numberOfClauses = Integer.parseInt(splitLine[1]);
            formula.intializeFormula(numberOfClauses);
            formula.makeTruthValues(numberOfVariables);
        }
        //The next line contains variable assignments.
        else
        {
            splitLine = nextLine.split(" ");
            //O(n)
            newClause = new int[splitLine.length - 1];
            for (int i = 0; i < splitLine.length; i++)
            {
                if (Integer.parseInt(splitLine[i]) == 0)
                {
                    formula.addClause(clauseCounter, newClause);
                    clauseCounter++;
                }
                else
                {
                    newClause[i] = Integer.parseInt(splitLine[i]);
                }
            }
        }
    }
}
