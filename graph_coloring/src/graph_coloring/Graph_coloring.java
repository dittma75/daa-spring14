package graph_coloring;

import java.io.File;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

public class Graph_coloring
{
    /**
     * Takes file_name, gets an input file with the given file_name and then
     * parses the input.
     *
     * @param file_name the name of the file containing the formula.
     */
    ISolver readGraph(String file_name, int colors)
    {
        File input = new File(file_name);
        GraphParser parser = new GraphParser(input, colors);
        //System.out.println(parser.parseGraph());
        return parser.parseGraph();
    }
    
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("Usage: java Graph_coloring cnf-formula");
            System.exit(0);
        }
        
        try 
        {
            IProblem problem = new Graph_coloring().readGraph(args[0], 2);
            int[] solution = problem.findModel();
            if (solution == null)
            {
                System.out.println("Unsatisfiable");
            }
            else
            {
                for (int i = 0; i < solution.length; i++)
                {
                    System.out.println(solution[i]);
                }
            }
        } 
        catch (org.sat4j.specs.TimeoutException e) {
            System.out.println("Timeout, sorry!");      
        }
    }
}
