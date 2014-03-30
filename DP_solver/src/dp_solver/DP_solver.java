package dp_solver;

import java.io.File;

/**DP_solver attempts to satisfy a Conjunctive Normal Form formula
 * with the Davis-Putnam backtracking algorithm.
 * 
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * @author Andrew Genova
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

    /** 
     * Select the next variable to be set.
     * @param f is the formula whose variable is to be selected.
     * @return branch variable.
     */
    int selectBranchVar ( Formula f ) 
    {
        return f.getNextVariable();
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
     * Prints that Formula is satisfiable, along with its solution
     * @param f the satisfiable formula
     */
    void success (Formula f) 
    {	
        System.out.println("Formula is satisfiable");
        System.out.println(f.getSolution());
    }

    /**
     * Formula is unsatisfiable
     * @param f the unsatisfiable formula
     */
    void failure (Formula f) 
    {	
        System.out.println ("Formula is unsatisfiable");
    }

    /**
     * Calls dp() to solve and prints success or failure status
     * @param fileName 
     */
    public void solve ( String fileName ) 
    {
        readFormula ( fileName );

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
        else 
        {
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
                else 
                {
                    // Neither true nor false worked, so unset the branch 
                    // variable and head back
                    unset ( var, formula );
                    return false;			
                }
            }
        }	
    }

    /**
     * Main method - solves the file passed to it as a parameter
     * @param args contains the file with the cnf-formula in index 0
     */
    public static void main(String[] args) 
    {

        if (args.length < 1) 
        {
            System.err.println ("Usage: java DP_solver cnf-formula");
            System.exit(0);
        }

        long start_time = System.currentTimeMillis();
        new DP_solver().solve ( args[0] );
        System.out.println(System.currentTimeMillis() - start_time);
    }
}
