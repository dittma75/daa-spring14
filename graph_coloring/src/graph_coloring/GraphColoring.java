package graph_coloring;

import java.io.File;
import org.sat4j.specs.IProblem;

public class GraphColoring
{
    private int[] current_solution;
    private int lower_bound;
    private int upper_bound;
    private int last_k;
    private GraphParser parser;
    public GraphColoring()
    {
        lower_bound = 2;
        current_solution = null;
    }
    
    public int[] colorGraph(String file_name)
    {
        readGraph(file_name);
        solve();
        return current_solution;
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
        upper_bound = parser.getNumberOfVertices();
    }

    void solve()
    {
        int colors = (lower_bound + upper_bound) / 2;
        if (colors != last_k)
        {
            try 
            {
                IProblem problem = parser.parseGraph(colors);
                int[] solution = problem.findModel();
                if (solution != null)
                {
                    current_solution = solution;
                    upper_bound = colors;
                    last_k = colors;
                    solve();
                }
                else
                {
                    lower_bound = colors;
                    last_k = colors;
                    solve();
                }
            } 
            catch (org.sat4j.specs.TimeoutException e) {
                System.out.println("Timeout, sorry!");      
            }
        }
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
            System.out.println(solution[i]);
        }
    }
}
