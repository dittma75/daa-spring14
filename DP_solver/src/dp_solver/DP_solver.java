package dp_solver;

import java.io.File;
import java.util.Arrays;

/**
 *
 * @author Kevin Dittmar
 */
public class DP_solver
{
    Formula formula;
    
    /**
     * Takes fileName, makes in input file with the given fileName
     * and then parses the input. 
     * @param fileName Filename given at command line
     */
    void readFormula ( String fileName ) 
    {
        File input = new File(fileName);
        Parser parser = new Parser(input);
        formula = parser.parseFile();		
    }

    /**
     * Returns true if the formula has an empty clause, false otherwise.
     * @param f is the formula passed to be checked
     * @return true if it contains an empty clause, false otherwise.
     */
    boolean hasEmptyClause ( Formula f ) 
    {
        return f.hasDeadEndClause();
    }

    /**
     * Returns true if the formula has no clauses left, false otherwise.
     * @param f is the formula passed to be checked
     * @return true if all clauses in formula are satisfied. False otherwise.
     */
    boolean isEmpty ( Formula f ) 
    {
        return f.isEmpty();
    }

    // Return branch variable.
    int selectBranchVar ( Formula f ) 
    {
        // Stub
        return 0;
    }

    /**
     * Set given variable to given true/false value.
     * Variable value may be positive or negative.
     * @param var the variable to be set
     * @param f the given formula
     * @param tf the boolean value to be set
     */
    void setVar ( int var, Formula f, boolean tf) 
    {
        f.setTruthValue(var, tf);		
    }

    /**
     * Set given variable to "unassigned" in the given formula.
     * @param var the variable to be unassigned
     * @param f the given formula
     */
    void unset ( int var, Formula f) 
    {
        f.unsetTruthValue(var);

    }

    /**
     * Prints out Formula is satisfiable
     * @param f the satisfiable formula
     */
    void success (Formula f) {
        // Stub		
        System.out.println ( "Formula is satisfiable");

        // Print satisfying assignment
    }

    /**
     * Formula is unsatisfiable
     * @param f the unsatisfiable formula
     */
    void failure (Formula f) {
        // Stub		
        System.out.println ("Formula is unsatisfiable");

    }

    /**
     * 
     * @param fileName 
     */
    public void solve ( String fileName ) 
    {
        readFormula ( fileName );

        System.out.println(formula);
        System.out.println(Arrays.toString(formula.truth_values));
        if (dp ( formula ) )
            success ( formula );
        else
            failure ( formula );

    }

    /**
     * Recursive backtracking solution
     * @param formula the given formula
     * @return false if branch does not work
     */
    boolean dp ( Formula formula ) 
    {
        if (isEmpty(formula)) // First base case: solution found
            return true;
        else if (hasEmptyClause(formula)) // Second base case: dead end found
            return false;
        else {
            // Pick a branch variable
            int var = selectBranchVar ( formula );

            // Try to set var = false in the formula

            setVar ( var, formula, false );

            if (dp(formula)) 
                return true;
            else 
            {
                // Unset var in the formula 
                unset ( var, formula );

                // Setting var to false did not work. 
                // Now try var = true

                setVar ( var, formula, true );

                if (dp (formula))
                    return true;
                else {
                    // Neither true nor false worked, so unset the branch 
                    // variable and head back
                    unset ( var, formula );
                    return false;			
                }
            }
        }	
    }


    public static void main(String[] args) 
    {

        if (args.length < 1) {
            System.err.println ("Usage: java DP_solver cnf-formula");
            System.exit(0);
        }

        // Insert timing code here...
        new DP_solver().solve ( args[0] );

    }
}
