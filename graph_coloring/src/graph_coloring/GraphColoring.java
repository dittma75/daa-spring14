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

    public GraphColoring()
    {
    }

    /**
     * Reads a graph from the file with the given file_name and solves
     * for the minimum number of colors required to color the graph.
     *
     * @param file_name the name of the file describing the graph to solve
     * @return a solution to the graph coloring problem with the minimum number
     * of colors
     */
    public int[] colorGraph(String file_name)
    {
        readGraph(file_name);
        return solve();
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
     * the current graph and stores the solution in current_solution
     * @return an int array containing the values that satisfy the graph's
     * SAT clauses.
     */
    int[] solve()
    {
        int lower_bound = 2;
        int upper_bound = parser.getNumberOfVertices();
        int current_colors = upper_bound;
        int last_k = 0;
        int[] minimum_solution = null;
        int solving_colors = upper_bound;
        do
        {
            try
            {
                IProblem problem = parser.parseGraph(current_colors);
                int[] solution = problem.findModel();
                if (solution != null)
                {
                    minimum_solution = solution;
                    solving_colors = current_colors;
                    upper_bound = current_colors;
                    last_k = current_colors;
                }
                else
                {
                    lower_bound = current_colors;
                    last_k = current_colors;
                }
                current_colors = (lower_bound + upper_bound) / 2;
            }
            catch (org.sat4j.specs.TimeoutException e)
            {
                System.out.println("Timeout, sorry!");
            }
        } while (last_k != current_colors);
        return getAssignments(minimum_solution, solving_colors);
    }

    /**
     * Gets a satisfying assignment for the minimum coloring for a graph
     * solution.
     *
     * @param solution the minimum coloring solution to the graph.
     * @param colors the number of colors that were used.
     * @return an array with an element for each vertex corresponding to the
     * number of the color that it was assigned.
     */
    int[] getAssignments(int[] solution, int colors)
    {
        int[] vertex_assignments = new int[solution.length / colors];
        int vertex = 0;
        for (int variable = 0; variable < solution.length; variable++)
        {
            if (solution[variable] > 0)
            {
                vertex_assignments[vertex] = solution[variable] % colors;
                vertex++;
            }
        }
        for (int i = 0; i < solution.length; i++)
        {
            System.out.println(solution[i]);
        }
        System.out.println(colors);
        return vertex_assignments;
    }

    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("Usage: java Graph_coloring cnf-formula");
            System.exit(0);
        }
        GraphColoring gc = new GraphColoring();
        int[] solution = gc.colorGraph(args[0]);
        for (int i = 0; i < solution.length; i++)
        {
            System.out.println("Vertex " + i + " has Color " + solution[i]);
        }
    }
}
