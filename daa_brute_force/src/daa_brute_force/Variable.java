package daa_brute_force;

/**Represents a Variable in a Clause.
 * It has an id and a positivity.
 * @author Kevin Dittmar
 */
class Variable 
{
    private int id;
    private boolean positive;
    
    /**
     * Creates a positive or negative Variable with an id
     * @param id the number of the variable.
     * @param positivity whether the variable is negated or not.
     */
    Variable(int id, boolean positive)
    {
        this.id = id;
        this.positive = positive;
    }
    
    /**Get the identification number of the variable.
     * @return the number of the variable. 
     */
    public int getID()
    {
        return id;
    }
    
    /**Get the positivity of the variable.
     * @return true if the variable is positive, false if it is negated. 
     */
    public boolean getPositivity()
    {
        return positive;
    }
    
    /**
     * Get the identification of the variable and its positivity.
     * @return a String representation of the variable. 
     */
    public String toString()
    {
        return id + "=" + positive;
    }
}
