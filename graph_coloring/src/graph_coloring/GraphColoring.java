package graph_coloring;

import java.io.File;
import org.sat4j.specs.IProblem;

/**
 * Solves the graph coloring problem by transforming it into a satisfiability
 * problem.
 *
 * @author Kevin Dittmar
 * @author Jonathan Frederickson
 * @author Andrew Genova
 */
public class GraphColoring
{
    private GraphParser parser;
    private int[] solution;
    private int number_of_colors;
    
    public GraphColoring()
    {
        
    }

    /**
     * Reads a graph from the file with the given file_name and solves
     * for the minimum number of colors required to color the graph.
     *
     * @param file_name the name of the file describing the graph to solve
     */
    public void colorGraph(String file_name)
    {
        readGraph(file_name);
        solve();
    }

    /**
     * Takes file_name, gets an input file with the given file_name and then
     * parses the input.
     *
     * @param file_name the name of the file containing the formula.
     */
    void readGraph(String file_name)
    {
        File input = new File(file_name);
        parser = new GraphParser(input);
    }

    /**
     * Finds the minimum number of colors that can be used to solve
     * the current graph and stores the current_solution in current_solution
     * @return an int array containing the values that satisfy the graph's
     * SAT clauses.
     */
    void solve()
    {
        int lower_bound = 2;
        int upper_bound = parser.getNumberOfVertices();
        
        //Next number of colors to try.
        int current_colors = upper_bound;
        //Last number of colors tried.
        int last_colors = 0;
        
        //Minimum number of colors to solve.
        int minimum_colors = upper_bound;
        /* int array that stores the assignments for the SAT variables that
         * colors the graph with the fewest colors possible.
         */
        int[] minimum_solution = null;
        do
        {
            try
            {
                IProblem problem = parser.parseGraph(current_colors);
                int[] current_solution = problem.findModel();
                if (current_solution != null)
                {
                    minimum_solution = current_solution;
                    minimum_colors = current_colors;
                    upper_bound = current_colors;
                    last_colors = current_colors;
                }
                else
                {
                    lower_bound = current_colors;
                    last_colors = current_colors;
                }
                current_colors = (lower_bound + upper_bound) / 2;
            }
            catch (org.sat4j.specs.TimeoutException e)
            {
                System.out.println("Timeout, sorry!");
            }
        } while (last_colors != current_colors);
        setSolution(parseSolution(minimum_solution, minimum_colors));
        setColors(minimum_colors);
    }

    /**
     * Turn the satisfying SAT assignment into a satisfactory assignment of
     * colors to vertices.
     * There are k SAT variables for each vertex in the graph, where k is the
     * number of colors used to color the graph, and these SAT variables are
     * grouped according to the vertex to which they belong.
     * To find the color assigned to a vertex, the array of SAT variables is
     * visualized as n subarrays each of size k, where n is the number of
     * vertices and k is the number of colors.  There is exactly one positive
     * element in each subarray, so the index that has the positive element
     * within each subarray n corresponds to the color for the nth vertex.
     *
     * @param solution the minimum coloring current_solution to the graph.
     * @param colors the number of colors that were used.
     * @return an array with an element for each vertex corresponding to the
     * number of the color that it was assigned.
     */
    int[] parseSolution(int[] solution, int colors)
    {
        /* Each vertex has a number of variables associated with it in solution
         * equivalent to the number of colors used.
         */
        int[] color_assignments = new int[solution.length / colors];
        int vertex = 0;
        int sat_variable = 0;
        while (sat_variable < solution.length)
        {
            //This is the next vertex color assignment.
            if (solution[sat_variable] > 0)
            {
                color_assignments[vertex] = sat_variable % colors;
                vertex++;
                //Set counter to the start of the next subarray.
                sat_variable = colors * vertex;
            }
            //The truth value for the current vertex hasn't been found yet. 
            else
            {
                sat_variable++;
            }
        }
        return color_assignments;
    }

    /**
     * Get a String representation of the vertex color assignments for the
     * minimum coloring solution for the graph parsed.
     * @return the minimum solution color assignments for each vertex.
     */
    String printSolution()
    {
        String assignments = "Minimum number of color needed:  " + 
                             number_of_colors + 
                             "\nVertex Color Assignments:\n";
        for (int i = 0; i < solution.length; i++)
        {
            assignments += "Vertex " + i + " has Color " + solution[i] + "\n";
        }
        return assignments;
    }
    
    /**
     * Sets the minimum coloring solution for the parsed graph.
     * @param solution the solution for the parsed graph.
     */
    void setSolution(int[] solution)
    {
        this.solution = solution;
    }
    
    /**
     * Sets the minimum number of colors required to solve the graph
     * @param number_of_colors the number of colors used in the graph solution.
     */
    void setColors(int number_of_colors)
    {
        this.number_of_colors = number_of_colors;
    }
    
    /**
     * Implements the GraphColoring class to color a graph with the least
     * number of colors possible.
     * @param args the file name from stdin that contains the graph to color.
     */
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("Usage: java Graph_coloring graph.col");
            System.exit(0);
        }
        GraphColoring gc = new GraphColoring();
        gc.colorGraph(args[0]);
        System.out.println(gc.printSolution());
    }
}
