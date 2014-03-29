package dp_solver;

import java.util.Arrays;

/**
 *
 * @author Kevin Dittmar
 */
public class Formula
{        
    private int[][] formula;
    int truth_values[];
    private int current_truth_value;
    private static final int UNSET = -1;
    private static final int FALSE = 0;
    private static final int TRUE = 1;
    
    public Formula()
    {
        current_truth_value = 0;
    }
    
    /**
     * Tests all of the clauses in the formula to see if they are satisfied.
     * @return true if all clauses are satisfied.
     */
    boolean isEmpty()
    {
        for (int clause = 0; clause < formula.length; clause++)
        {
            if (!clauseSatisfied(clause))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Tests a clause to see if it is satisfied.  A satisfied clause will have
     * one variable set to TRUE (1).  Variables can be UNSET (-1).
     * @param clause the index of the clause in formula to be tested.
     * @return true if the clause is satisfied, which means it has at least
     * one true variable.
     */
    private boolean clauseSatisfied(int clause)
    {
        for (int var = 0; var < formula[clause].length; var++)
        {
            if ((truth_values[var] == TRUE && formula[clause][var] > 0) ||
                (truth_values[var] == FALSE && formula[clause][var] < 0))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Tests each clause in the formula to see if it's a dead end.  If one
     * clause is a dead end, then the formula has a dead-end clause.
     * @return true if the formula has a dead-end clause.
     */
    boolean hasDeadEndClause()
    {
        for (int clause = 0; clause < formula.length; clause++)
        {
            if (clauseDeadEnd(clause))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Tests a clause to see if it is a dead end.  An dead-end clause will have
     * all of its variables set to FALSE (0).  None of its variables can be
     * TRUE (1) or UNSET (-1).
     * @param clause the index of the clause in formula to be tested.
     * @return true if all variables in a clause are FALSE (0). 
     */
    private boolean clauseDeadEnd(int clause)
    {
        for (int var = 0; var < formula[clause].length; var++)
        {
            if ((truth_values[var] == TRUE && formula[clause][var] > 0) ||
                (truth_values[var] == FALSE && formula[clause][var] < 0) ||
                (truth_values[var] == UNSET))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Initializes the formula with the number of clauses.  The second
     * dimension of the array will be variable length based on the number
     * of variables in each clause.
     * @param clauses the number of clauses that make up this formula.
     */
    void intializeFormula(int clauses)
    {
        formula = new int[clauses][];
    }
    
    /**
     * Adds an int array that represents a clause in the formula to be 
     * considered when testing the satisfiability.
     * @param index the index of the clause in the formula array.
     * @param clause the Clause to be added.
     */
    void addClause(int index, int[] clause)
    {
        formula[index] = clause;
    }
    
    /**
     * Takes number of variables and sizes the array 'truth_values'
     * accordingly and assigns them to 'UNSET'.
     * @param num_variables number of variables in clause 
     */
    void makeTruthValues(int num_variables)
    {
        truth_values = new int[num_variables];
        Arrays.fill(truth_values, UNSET);
        
    }
    /**
     * Changes the truth value of particular variable to be
     * unset.
     * @param variable the truth value to be unset
     */
    void unsetTruthValue(int variable)
    {
        truth_values[variable] = UNSET;
        current_truth_value--;
    }
    /**
     * Takes a boolean variable and if 'true', then it
     * sets the truth value of that variable to 'true'.
     * Otherwise 'false'.
     * @param variable the variable to set
     * @param value the boolean value to set
     */
    void setTruthValue(int variable, boolean value)
    {
        if (value)
        {
            truth_values[variable] = TRUE;
        }
        else
        {
            truth_values[variable] = FALSE;
        }
        current_truth_value++;
    }
    
    @Override
    public String toString()
    {
        String result = "";
        for (int i = 0; i < formula.length; i++)
        {
            result += "[";
            for (int j = 0; j < formula[i].length; j++)
            {
                result += " " + formula[i][j] + " ";
            }
            result += "]\n";
        }
        return result;
    }
}
