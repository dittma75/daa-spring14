package graph_coloring;

import java.io.File;

public class Graph_coloring
{
    /**
     * Takes file_name, gets an input file with the given file_name and then
     * parses the input.
     *
     * @param file_name the name of the file containing the formula.
     */
    void readGraph(String file_name, int colors)
    {
        File input = new File(file_name);
        GraphParser parser = new GraphParser(input, colors);
        System.out.println(parser.parseGraph());
    }
    
    public static void main(String[] args)
    {
        if (args.length < 1)
        {
            System.err.println("Usage: java Graph_coloring cnf-formula");
            System.exit(0);
        }
        new Graph_coloring().readGraph(args[0], 2);
        
    }
}
