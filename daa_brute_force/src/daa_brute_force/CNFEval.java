package daa_brute_force;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import java.math.BigInteger;
/**CNFEval takes a file as input, parses it
 * with the Parser class into Clauses, and
 * tests the Clauses for satisfiability.
 *
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * 
 */
public class CNFEval 
{
    private static ArrayList<Clause> clauses = new ArrayList<Clause>();
    private static BigInteger truthValues;
    private static Component parent;
    
    /**Parses a file, evaluates the data given as variables and clauses
     * for a CNF, and evaluates the CNF for satisfiability.
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
	JFileChooser chooser = new JFileChooser();
	int returnVal = chooser.showOpenDialog(parent);
	if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
	    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
	}
 
	File file = chooser.getSelectedFile();
	Scanner scanner = null;
	try
        {
	    scanner = new Scanner(file);
	} 
        catch (FileNotFoundException ex)
        {
	    Logger.getLogger(CNFEval.class.getName()).log(Level.SEVERE, null, ex);
	}
	
        Parser parser = new Parser(scanner);
        parser.parseFile();
        truthValues = makeTruthValues(parser.getNumberOfVariables());
        boolean satisfiable = evaluateCNF(truthValues);
        System.out.println(satisfiable);
    }
    
    /**
     * Adds a Clause to be considered when testing
     * the satisfiability.
     * @param clause the Clause to be added.
     */
    public static void addClause(Clause clause)
    {
        clauses.add(clause);
    }
    
    /*
     * Evaluates the CNF given according to the clauses and variables
     * parsed from the given file.  If all DNF clauses are true, the CNF is
     * also true.
     * @param truthValues the truth values for all variables in the CNF
     * to be interpreted as a binary integer.
     * @return true if CNF evaluates to true, false otherwise.
     */
    //O(n^3)
    private static boolean evaluateCNF(BigInteger truthValues)
    {
        for (BigInteger i = truthValues; 
             i.compareTo(BigInteger.ZERO) > 0;
             i = i.subtract(BigInteger.ONE))
        {
            if (evaluateDNFs(i) == true)
            {
                return true;
            }
        }
        return false;
    }
    
    /* Evaluates each clause until a false clause is found.
     * If no false clause is found, all DNFs are true.
     */
    //O(n^2)
    private static boolean evaluateDNFs(BigInteger truthValues)
    {
        for(Clause clause : clauses)
        {
            if(clause.evaluate(truthValues) == false)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * @param truthValues a BigInteger that represents the current truth values
     * for all of the CNF's variables when interpreted as a binary integer.
     * @param variableNumber the variable whose truth value is to be returned.
     * @return true if the nth variable is true, which corresponds to the
     * (n - 1)th bit from the right being set.
     */
    public static boolean getTruthValue(BigInteger truthValues,
                                        int variableNumber)
    {
        return truthValues.testBit(variableNumber - 1);
    }
    
    /* Makes a BigInteger that represents the truth values of all variables
     * in the CNF when interpreted as a binary integer.
     * @param numberOfVariables the number of variables in the CNF.
     * @return a BigInteger that represents the all of the variables being true.
     */
    private static BigInteger makeTruthValues(int numberOfVariables)
    {
        return new BigInteger("2").pow(numberOfVariables).subtract(BigInteger.ONE);
    }
}
