/* Kevin Dittmar, Jonathan Frederickson, and Andrew Genova
 * Design and Analysis of Algorithms Brute Force */
package daa_brute_force;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import java.math.BigInteger;
/**
 * CNFEval takes a file as input, parses it with the Parser class into
 * Clauses, and tests the Clauses for satisfiability.
 * The parsed formula must be in Conjunctive Normal Form (CNF)
 *
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * @author Andrew Genova
 */
public class CNFEval 
{
    private static BigInteger truthValues;
    private static int formula[][];
    
    /**
     * Evaluates the CNF formula given according to the clauses and variables
     * parsed from the given file.  If all disjunctive clauses are true, 
     * the CNF formula is satisfiable.
     * This method prints the time that it took to run in milliseconds as
     * standard output.
     * @param input the file containing evaluation input in CNF format.
     * @return true if CNF formula is satisfiable, false otherwise.
     */
    public static boolean evaluateCNF(File input)
    {
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(input);
        } 
        catch (FileNotFoundException e)
        {
            Logger.getLogger(CNFEval.class.getName()).log(Level.SEVERE,null,e);
        }
        
        Parser parser = new Parser(scanner);
        parser.parseFile();
        
        long startTime = System.currentTimeMillis();
        /*truthValues represents the truth values for all variables in the CNF
         * when it is interpreted as a binary integer.
         */
        for (BigInteger i = truthValues; 
             i.compareTo(BigInteger.ZERO) > 0;
             i = i.subtract(BigInteger.ONE))
        {
            if (evaluateDisjunctions(i) == true)
            {
                System.out.println(System.currentTimeMillis() - startTime);
                return true;
            }
        }
        System.out.println(System.currentTimeMillis() - startTime);
        return false;
    }
    
    /**
     * Initializes the formula with the number of clauses.  The second
     * dimension of the array will be variable length based on the number
     * of variables in each clause.
     * @param clauses the number of clauses that make up this formula.
     */
    static void intializeFormula(int clauses)
    {
        formula = new int[clauses][];
    }
    
    /**
     * Adds an int array that represents a clause in the formula to be 
     * considered when testing the satisfiability.
     * @param index the index of the clause in the formula array.
     * @param clause the Clause to be added.
     */
    static void addClause(int index, int[] clause)
    {
        formula[index] = clause;
    }
    
    /** 
     * Initializes truthValues to a BigInteger that represents the truth values
     * of all variables in the CNF when interpreted as a binary integer.
     * truthValues starts with all variables being true.
     * @param numVariables the number of variables in the CNF.
     */
    static void makeTruthValues(int numVariables)
    {
        truthValues = 
                new BigInteger("2").pow(numVariables).subtract(BigInteger.ONE);
    }
    
    /** 
     * Evaluates each clause until a false clause is found.
     * If no false clause is found, all disjunctions are true.
     * @param truthValues the current truthValue to use for evaluation.
     */
    private static boolean evaluateDisjunctions(BigInteger truthValues)
    {
        boolean clauseSatisfied;
        //Iterates through all clauses in formula
        for (int i = 0; i < formula.length; i++)
        {
            clauseSatisfied = evaluateClause(i, truthValues);
            if (!(clauseSatisfied))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Evaluate each variable of a clause until a variable is
     * positive and true, a variable is negated and false, or no variable
     * in the clause fits either of these criteria.
     * @param clauseNumber the index of the clause in the formula to be
     * evaluated.
     * @param truthValues the current truth values
     * @return true if this disjunction clause evaluates to true.
     */
    private static boolean evaluateClause(int clauseNumber,
            BigInteger truthValues)
    {
        //Iterates through all variables in clause
        for (int i = 0; i < formula[clauseNumber].length; i++)
        {
            /*The variable is not negated and has a "true" truth value or
             *the variable is negated and has a "false" truth value, so
             *break out of this loop early.
             */
            if ((formula[clauseNumber][i] > 0 &&
                 getTruthValue(truthValues, formula[clauseNumber][i])) ||
                (formula[clauseNumber][i] < 0 &&
                !(getTruthValue(truthValues,negate(formula[clauseNumber][i]))))
               )
            {
                return true;
            }
        }
        return false;
    }
    
    /**Gets the truth value for a specified variable from truthValues,
     * which represents the truth value of all variables when interpreted as
     * a binary integer.
     * @param truthValues a BigInteger that represents the current truth 
     * values for all of the CNF's variables when interpreted as a binary 
     * integer (0 represents false, and 1 represents true).
     * @param variableNumber the variable whose truth value is to be returned.
     * @return true if the nth variable is true, which corresponds to the
     * (n - 1)th bit from the right being set.
     */
    private static boolean getTruthValue(BigInteger truthValues,
            int variableNumber)
    {
        return truthValues.testBit(variableNumber - 1);
    }
    
    /**
     * Use bitwise negation to change a negative value to a positive value
     * quickly by using one's complement negation and adding 1.
     * This operation was faster than Math.abs() in test runs.  It is
     * applicable because this class only requires negative numbers to
     * be turned into positive numbers of an equivalent magnitude for array
     * indexing purposes.
     * Pre:  Value must be a negative number to be negated correctly.
     * 
     * @param value the negative value to be made positive.
     * @return the positive value of equivalent magnitude to value.
     */
    private static int negate(int value)
    {
        return ~value + 1;
    }
       
    /**
     * Tests the chosen file, which should be CNF input, to see if
     * the CNF formula described is satisfiable.
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("You chose to open this file: "
                    + chooser.getSelectedFile().getName());
        }
        File file = chooser.getSelectedFile();
        boolean satisfiable = evaluateCNF(file);
        if (satisfiable)
        {
            System.out.println("satisfiable");
        }
        else
        {
            System.out.println("unsatisfiable");
        }
    }
}