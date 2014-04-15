package graph_coloring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Parses a .col file (information for a Graph) and creates
 * a .cnf file (information for a SAT formula) to be passed to
 * SAT4J for evaluation.
 *
 * @author Kevin Dittmar
 */
public class GraphParser
{
    private static final int RED_START_MODIFIER = 0;
    private static final int GREEN_START_MODIFIER = 1;
    private static final int BLUE_START_MODIFIER = 2;
    private int red[];
    private int green[];
    private int blue[];
    private Scanner scanner;
    private String cnf_file;
    
    GraphParser(File input)
    {
        try
        {
            scanner = new Scanner(input);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            cnf_file = "";
        }
    }
   
    void parseGraph()
    {
        String file_string = "";
        while (scanner.hasNextLine())
        {
            file_string += formatInput(scanner.nextLine());
        }
        file_string = file_string.replaceAll("  *", " ");
        parseEdges(file_string);
    }
    
    String formatInput(String next_line)
    {
        return next_line.replace("  *", " ");
    }
    
    void parseEdges(String file_string)
    {
        scanner = new Scanner(file_string);
        int total_vertices = scanner.nextInt();
        int total_edges = scanner.nextInt();
        red = new int[total_vertices];
        green = new int[total_vertices];
        blue = new int[total_vertices];
        
        for (int vertex = 0; vertex < total_vertices; vertex++)
        {
            addHasColorClause(vertex);
            addHasOneColorClauses(vertex);
        }
        
        for (int edge = 0; edge < total_edges; edge++)
        {
            int vertex_i = scanner.nextInt();
            int vertex_j = scanner.nextInt();
            addDifferentColorClauses(vertex_i, vertex_j);
        }
    }
    
    /**
     * Adds the clauses that make sure the two vertices connected by an edge
     * have different colors.
     * @param vertex_i the index of the first vertex of the edge.
     * @param vertex_j the index of the second vertex of the edge.
     */
    void addDifferentColorClauses(int vertex_i, int vertex_j)
    {
        
    }
    
    /**
     * Adds the clause that makes sure a vertex has a color.
     * @param vertex the index of the vertex for which a has-color clause will
     * be added.
     */
    void addHasColorClause(int vertex)
    {
        
    }
    /**
     * Adds the clauses that make sure a vertex has only one color.
     * @param vertex the index of the vertex for which three has-one-color
     * clauses will be added.
     */
    void addHasOneColorClauses(int vertex)
    {
        
    }
}
