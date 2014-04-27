package graph_coloring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;

/**Parses a .col file (information for a Graph) and creates
 * a .cnf file (information for a SAT formula) to be passed to
 * SAT4J for evaluation.
 *
 * @author Kevin Dittmar
 */
public class GraphParser
{
    private int colors;
    private Scanner scanner;
    private int vertices;
    private int edges;
    private ISolver solver;
    private String file_string;
    GraphParser(File input)
    {
        file_string = "";
        prepareGraph(input);
    }
   
    /* Prepare the file_string and extract 
     * the number of vertices and clauses.
     * @param input the file containing the graph.  The first line is in the
     * format "number_of_vertices number_of_clauses".  The rest of the lines
     * in the file contain two numbers, where each number represents two
     * vertices connected by an edge.
     */ 
    private void prepareGraph(File input)
    {
        try
        {
            scanner = new Scanner(input);
            vertices = scanner.nextInt();
            edges = scanner.nextInt();
            while (scanner.hasNextLine())
            {
                file_string += formatInput(scanner.nextLine());
            }
            file_string = formatInput(file_string);
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(GraphParser.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    
    String formatInput(String next_line)
    {
        return next_line.replace("  *", " ") + " ";
    }
    
    ISolver parseGraph(int colors)
    {
        this.colors = colors;
        solver = SolverFactory.newDefault();
        solver.newVar(vertices * colors);
        scanner = new Scanner(file_string);
        
        for (int vertex = 0; vertex < vertices; vertex++)
        {
            addHasColorClause(vertex);
            addHasOneColorClauses(vertex);
        }
        
        for (int edge = 0; edge < edges; edge++)
        {
            int vertex_i = scanner.nextInt();
            int vertex_j = scanner.nextInt();
            addDifferentColorClauses(vertex_i, vertex_j);
        }
        return solver;
    }
    
    /**
     * Adds the clauses that make sure the two vertices connected by an edge
     * have different colors.
     * @param vertex_i the index of the first vertex of the edge.
     * @param vertex_j the index of the second vertex of the edge.
     */
    void addDifferentColorClauses(int vertex_i, int vertex_j)
    {
        for (int color = 0; color < colors; color++)
        {
            addClause(new int[] {makeVariable(vertex_i, color) * -1, 
                                 makeVariable(vertex_j, color) * -1});
        }
    }
    
    /**
     * Adds the clause that makes sure a vertex has a color.
     * @param vertex the index of the vertex for which a has-color clause will
     * be added.
     */
    void addHasColorClause(int vertex)
    {
        int[] clause = new int[colors];
        for (int color = 0; color < colors; color++)
        {
            clause[color] = makeVariable(vertex, color);
        }
        addClause(clause);
    }
    /**
     * Adds the clauses that make sure a vertex has only one color.
     * @param vertex the index of the vertex for which k has-one-color
     * clauses will be added, where k is the number of colors.
     */
    void addHasOneColorClauses(int vertex)
    {
        for (int l_color = 0; l_color < colors - 1; l_color++)
        {
            for (int r_color = l_color + 1; r_color < colors; r_color++)
            {
                addClause(new int[] {makeVariable(vertex, l_color) * -1, 
                                     makeVariable(vertex, r_color) * -1});
            }
        }
    }
    
    /**Make a variable for a specified color.  The formula is 
     * number of colors available * vertex + color + 1.  The +1 eliminates the
     * 0th variable because 0 is a delimiter in DIMACS format.
     * For example, if there are 2 colors and 3 vertices,
     * vertex 1 will have color variables 1-2, vertex 2 will have color
     * variables 3-4, and vertex 3 will have color variables 5-6.
     * @param vertex number of the vertex for which to generate a variable.
     * @param color number of the color for which to generate a variable.
     * @return a variable made from a color and a vertex to be used in a DIMACS 
     * clause.
     */
    private int makeVariable(int vertex, int color)
    {
        return (vertex * colors + color) + 1;
    }
    
    public void addClause(int[] clause)
    {
        try
        {
            // the clause should not contain a 0, only integer (positive or negative)
            // with absolute values less or equal to MAXVAR
            // e.g. int [] clause = {1, -3, 7}; is fine
            // while int [] clause = {1, -3, 7, 0}; is not fine
            solver.addClause(new VecInt(clause)); // adapt Array to IVecInt
        }
        catch (ContradictionException ex)
        {
            Logger.getLogger(GraphColoring.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getNumberOfVertices()
    {
        return vertices;
    }
}