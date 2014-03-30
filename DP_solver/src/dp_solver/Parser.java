package dp_solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Parses a file for use in the evaluation of a CNF formula.
 * 
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * @author Andrew Genova
 */
class Parser 
{
    private Scanner scanner;
    private int clauseCounter = 0;
    private Formula formula;
    /**
     * Initialize the Parser with an input File.  That File will be
     * read by a Scanner to parse the contents.
     * @param input should be a valid .cnf file.
     */
    Parser(File input)
    {
        try 
        {
            scanner = new Scanner(input);
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    /**
     * Parse the File on the Scanner as input for CNF evaluation.
     * @return a valid formula to be used as input for the DP_solver.
     */
    Formula parseFile()
    {
        String file_string = "";
        while (scanner.hasNextLine())
        {
            file_string += formatInput(scanner.nextLine());
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
        /* If the line is a comment, ignore it and return a space to add
         * to the file_string.
         */
        if (nextLine.startsWith("c"))
        {
            return " ";
        }
        
        /* If the line starts with p cnf, it is in the format
         * p cnf number_of_variables number_of_clauses, so extract those
         * initialize the formula.
         */
        else if (nextLine.startsWith("p cnf "))
        {
            //Remove data input descriptor.
            nextLine = nextLine.replace("p cnf ", "");
            String[] split_line = nextLine.split(" ");
            int numberOfVariables = Integer.parseInt(split_line[0]);
            int numberOfClauses = Integer.parseInt(split_line[1]);
            formula = new Formula(numberOfClauses, numberOfVariables);
            return " ";
        }
        
        /* If it hasn't returned yet, return the line as it stands with
         * a space appended to keep it and the next line from running together.
         */
        return nextLine + " ";
    }
    
    /**
     * Parse the data in file_string as clauses to add to the Formula.
     * @param file_string is a String of integers delimited by spaces that 
     * represents the clauses of the formula, where each clause is terminated
     * by a 0.
     */
    private void parseInput(String file_string)
    {
        /* Store the current clause in an ArrayList so it can be whatever
         * size is necessary.
         */
        ArrayList<Integer> temp_clause = new ArrayList<Integer>();
        scanner = new Scanner(file_string);
        
        while (scanner.hasNextInt())
        {
            int next_int = scanner.nextInt();
            /* If the next integer is a 0, then the clause is complete and
             * is ready to be added to the Formula.
             */
            if (next_int == 0)
            {
                //Copy the clause contents in the ArrayList to an array format.
                int[] new_clause = new int [temp_clause.size()];
                for (int i = 0; i < temp_clause.size(); i++)
                {
                    new_clause[i] = temp_clause.get(i);
                }
                temp_clause = new ArrayList<Integer>();
                formula.addClause(clauseCounter, new_clause);
                /* Increment the clauseCounter so the next clause will be
                 * stored in the correct place.
                 */
                clauseCounter++;
            }
            //Add another variable to this clause.
            else
            {
                temp_clause.add(next_int);
            }
        }
    }
}