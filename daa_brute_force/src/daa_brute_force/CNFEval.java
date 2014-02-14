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
    /**
     * @param args the command line arguments
     */
    
    private static ArrayList<Clause> clauses = new ArrayList<Clause>();
    private static BigInteger truthValues;
    private static Component parent;
    
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
        boolean satisfiable = evaluate(truthValues);
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
    
    /**
     * Evaluates the current CNF
     * @param truthvals a map of the current truth values 
     * @return true if CNF evaluates to true, false otherwise
     */
    private static boolean evaluate(BigInteger truthValues)
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
    /* Information on getting the nth bit from an integer.
     * http://stackoverflow.com/questions/14145733/how-can-one-read-an-integer-bit-by-bit-in-java
     */
    public static boolean getTruthValue(BigInteger truthValues, int variableNumber)
    {
        return truthValues.testBit(variableNumber - 1);
    }
    
    private static BigInteger makeTruthValues(int variableNumber)
    {
        String values = "";
        for (int i = 0; i < variableNumber; i++)
        {
            values += "1";
        }
        return new BigInteger(values);
    }
}
