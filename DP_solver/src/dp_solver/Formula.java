package dp_solver;

import static java.lang.Math.abs;
import java.util.Arrays;

/**
 *
 * @author Kevin Dittmar
 */
public class Formula
{        
    private int[][] formula;
    private int truth_values[];
    private int current_variable;
    private int[] satisfied_clauses;
    private static final int UNSET = -1;
    private static final int FALSE = 0;
    private static final int TRUE = 1;
    
    public Formula()
    {
        current_variable = 0;
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
     * Initializes the formula with the number of clauses.  The second
     * dimension of the array will be variable length based on the number
     * of variables in each clause.
     * @param clauses the number of clauses that make up this formula.
     */
    void intializeFormula(int clauses, int variables)
    {
        formula = new int[clauses][];
        truth_values = new int[variables];
        Arrays.fill(truth_values, UNSET);
        satisfied_clauses = new int[clauses];
        Arrays.fill(satisfied_clauses, 0);
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
     * Changes the truth value of particular variable to be
     * unset.
     * @param variable the truth value to be unset
     */
    void unsetTruthValue(int variable)
    {
        for (int clause = 0; clause < formula.length; clause++)
        {
            if (satisfied_clauses[clause] == variable + 1)
            {
                satisfied_clauses[clause] = 0;
            }
        }
        truth_values[variable] = UNSET;
        current_variable--;
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
        current_variable++;
    }
    
    int getNextVariable()
    {
        return current_variable;
    }
    
    /**
     * Pre:  formula must be solved for solution to be correct.
     * @return 
     */
    public String getSolution()
    {
        String result = "[";
        for (int i = 0; i < truth_values.length; i++)
        {
            result += (i + 1) + "=";
            if (truth_values[i] == 1)
            {
                result += "TRUE";
            }
            else
            {
                result += "FALSE";
            }
            if (i < truth_values.length - 1)
            {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }
    
    /**
     * Prints the Formula in this format:
     * [-1 2 3]
     * [3 1 4]
     * [-2 -4 3]
     * @return the Formula, as a String
     */
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
    
    private int getVariableNumber(int clause, int var_index)
    {
        return formula[clause][var_index];
    }
    
    /**Get the truth value for the corresponding variable.
     * The variable may or may not be negative, and the value will be
     * one greater than its index in the truth_values array.  This is
     * because truth_value indices start at 0, and variable numbers start at 1.
     * 
     * @param variable
     * @return 
     */
    private int getTruthValue(int variable)
    {
        return truth_values[abs(variable) - 1];
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
        if (clauseHasBeenSatisfied(clause))
        {
            return true;
        }
        for (int var_index = 0; var_index < formula[clause].length; var_index++)
        {
            int variable = getVariableNumber(clause, var_index);
            if ((getTruthValue(variable) == TRUE && variable > 0) ||
                (getTruthValue(variable) == FALSE && variable < 0))
            {
                satisfied_clauses[clause] = abs(variable);
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
        if (clauseHasBeenSatisfied(clause))
        {
            return false;
        }
        for (int var_index = 0; var_index < formula[clause].length; var_index++)
        {           
            int variable = getVariableNumber(clause, var_index);
            if ((getTruthValue(variable) == TRUE && variable > 0) ||
                (getTruthValue(variable) == FALSE && variable < 0) ||
                (getTruthValue(variable) == UNSET))
            {
                return false;
            }
        }
        return true;
    }
    
    private boolean clauseHasBeenSatisfied(int clause)
    {
        return (satisfied_clauses[clause] != 0);
    }
}
