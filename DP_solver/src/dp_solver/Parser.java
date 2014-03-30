package dp_solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

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
        String file_string = "";
        while (scanner.hasNextLine())
        {
            nextLine = scanner.nextLine();
            file_string += formatInput(nextLine);
        }
        file_string = file_string.replaceAll("  *", " ");
        parseInput(file_string);
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
        if (nextLine.startsWith("c"))
        {
            return " ";
        }
        else if (nextLine.startsWith("p cnf "))
        {
            //Remove data input descriptor.
            nextLine = nextLine.replace("p cnf ", "");
            String[] split_line = nextLine.split(" ");
            int numberOfVariables = Integer.parseInt(split_line[0]);
            int numberOfClauses = Integer.parseInt(split_line[1]);
            formula.intializeFormula(numberOfClauses);
            formula.makeTruthValues(numberOfVariables);
            return " ";
        }
        return nextLine + " ";
    }
    
    /**
     * Determines the meaning of each part of
     * the input.
     * Lines starting with c are comments to be ignored.
     * A line starting with p cnf identifies the file as CNFEval input
     * and will provide the number of clauses and number of variables.
     * All other lines specify a disjunctive clause to be added.
     */
    private void parseInput(String file_string)
    {
        ArrayList<Integer> temp_clause = new ArrayList<Integer>();
        scanner = new Scanner(file_string);
        
        while (scanner.hasNextInt())
        {
            int next_int = scanner.nextInt();
            if (next_int == 0)
            {
                int[] new_clause = new int [temp_clause.size()];
                for (int i = 0; i < temp_clause.size(); i++)
                {
                    new_clause[i] = temp_clause.get(i);
                }
                temp_clause = new ArrayList<Integer>();
                formula.addClause(clauseCounter, new_clause);
                clauseCounter++;
            }
            else
            {
                temp_clause.add(next_int);
            }
        }
    }
}
