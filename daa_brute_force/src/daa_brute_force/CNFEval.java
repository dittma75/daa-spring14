package daa_brute_force;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
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
//    private static ArrayList<Clause> clauses = new ArrayList<Clause>();
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
    //O(n^3)
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
        truthValues = makeTruthValues(parser.getNumberOfVariables());
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
     * Adds a Clause to be considered when testing
     * the satisfiability.
     * @param clause the Clause to be added.
     */
    static void addClause(int i, int[] clause)
    {
        formula[i] = clause;
    }
    
    static void intializeFormula(int clauses)
    {
        formula = new int[clauses][];
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
    static boolean getTruthValue(BigInteger truthValues,
                                        int variableNumber)
    {
        return truthValues.testBit(variableNumber - 1);
    }
    
    /** 
     * Evaluates each clause until a false clause is found.
     * If no false clause is found, all disjunctions are true.
     */
    //O(n^2)
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
//        for(Clause clause : clauses)
//        {
//            if(clause.evaluate(truthValues) == false)
//            {
//                return false;
//            }
//        }
//        return true;
    }
    
    private static boolean evaluateClause(int clauseNumber, BigInteger truthValues)
    {
        //Iterates through all variables in clause
        for (int i = 0; i < formula[clauseNumber].length; i++)
        {
            /*The variable is not negated and has a "true" truth value or
             *the variable is negated and has a "false" truth value, so
             *break out of this loop early.
             */
            if ((formula[clauseNumber][i] > 0 && getTruthValue(truthValues, formula[clauseNumber][i])) ||
                (formula[clauseNumber][i] < 0 && !(getTruthValue(truthValues, negate(formula[clauseNumber][i])))))
            {
                return true;
            }
        }
        return false;
    }
    
    private static int negate(int value)
    {
        return ~value + 1;
    }
    /** 
     * Makes a BigInteger that represents the truth values of all variables
     * in the CNF when interpreted as a binary integer.
     * @param numVariables the number of variables in the CNF.
     * @return a BigInteger that represents the all of the variables
     * being true.
     */
    private static BigInteger makeTruthValues(int numVariables)
    {
        return new BigInteger("2").pow(numVariables).subtract(BigInteger.ONE);
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
        System.out.println(satisfiable);
    }
}
