package dp_solver;

import static java.lang.Math.abs;
import java.util.Arrays;

/**
 * Formula represents a Conjunctive Normal Form formula to be tested for 
 * satisfiability.
 *
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * @author Andrew Genova
 */
public class Formula
{        
    /**Stores the formula.*/
    private int[][] formula;
    /**Stores the current list of truth values.*/
    private int truth_values[];
    /**The next variable to be assigned.*/
    private int current_variable;
    /**Stores the clauses that are already satisfied.*/
    private int[] satisfied_clauses;
    /**Named constant for unset truth values.*/
    private static final int UNSET = -1;
    /**Named constant for false truth values.*/
    private static final int FALSE = 0;
    /**Named constant for true truth values.*/
    private static final int TRUE = 1;
    
    /**
     * Initializes the formula with the given number of clauses and variables. 
     * The second dimension of the array will be variable length based
     * on the number of variables in each clause.
     * @param clauses the number of clauses that make up this formula.
     * @param variables the number of variables that are present in this 
     * formula.
     */
    public Formula(int clauses, int variables)
    {
        this.formula = new int[clauses][];
        this.truth_values = new int[variables];
        Arrays.fill(this.truth_values, UNSET);
        this.satisfied_clauses = new int[clauses];
        Arrays.fill(this.satisfied_clauses, 0);
        this.current_variable = 0;
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
     * Adds an int array that represents a clause in the formula to be 
     * considered when testing the satisfiability.
     * @param index the index of the clause in the formula array.
     * @param clause the clause to be added.
     */
    void addClause(int index, int[] clause)
    {
        formula[index] = clause;
    }
    
    /**
     * Changes the truth value of particular variable to be unset.  When a
     * variable is unset, the clauses satisfied by that variable are reset
     * to 0 in the satisfaction array, which means that the clause is not
     * satisfied.
     * Note:  The var_index corresponding to the truth_values array is 1 less
     * than the actual number of the variable.
     * @param var_index the truth value to be unset, where the index is the
     * variable number - 1.
     */
    void unsetTruthValue(int var_index)
    {
        for (int clause = 0; clause < formula.length; clause++)
        {
            if (satisfied_clauses[clause] == var_index + 1)
            {
                satisfied_clauses[clause] = 0;
            }
        }
        truth_values[var_index] = UNSET;
        current_variable--;
    }
    /**
     * Sets the truth value of a variable to true or false
     * according to the boolean value.
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
        /* Move to the next variable so this method can be called
         * again and again.
         */
        current_variable++;
    }
    
    /**
     * Get the next variable to be set to true or false in the backtracking
     * algorithm.
     * @return the next variable to be assigned a truth value. 
     */
    int getNextVariable()
    {
        return current_variable;
    }
    
    /**
     * Get the String representation of the determined solution to the Formula.
     * Pre:  formula must be satisfiable and solved for solution to be correct.
     * @return the solution to the formula in the format:  
     * [variable=TRUE/FALSE, variable=TRUE/FALSE, ... ]
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
    
    /**Get the variable for a particular clause at the specified index.
     * A negative return value means that the variable is negated.
     * 
     * @param clause the clause the variable is being taken from
     * @param var_index the index of the variable in the clause
     * @return the variable in the specified clause at the specified index.
     */
    private int getVariable(int clause, int var_index)
    {
        return formula[clause][var_index];
    }
    
    /**
     * Get the truth value for the corresponding variable.
     * The variable may or may not be negative, and the value will be
     * one greater than its index in the truth_values array.  This is
     * because truth_value indices start at 0, and variable numbers start at 1.
     * 
     * @param variable the variable whose truth value is to be returned.
     * @return the truth value for the given variable.
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
            int variable = getVariable(clause, var_index);
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
            int variable = getVariable(clause, var_index);
            if ((getTruthValue(variable) == TRUE && variable > 0) ||
                (getTruthValue(variable) == FALSE && variable < 0) ||
                (getTruthValue(variable) == UNSET))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Determine whether the given clause has been satisfied by a previous
     * variable assignment or not.
     * @param clause the clause to be tested for satisfaction.
     * @return true if the clause has been satisfied.
     */
    private boolean clauseHasBeenSatisfied(int clause)
    {
        return (satisfied_clauses[clause] != 0);
    }
}