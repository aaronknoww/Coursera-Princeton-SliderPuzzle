import edu.princeton.cs.algs4.PrimMST;

public class Board
{
    private int [][] tiles;
    
     // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)  You may also assume that 2 ≤ n < 128
    public Board(int[][] tiles)
    {
        if((tiles.length < 2) || (tiles.length>128))
            throw new IndexOutOfBoundsException("number of n is out of limits"); // TODO: REVISAR QUE EXEPCION MANDAR.
        this.tiles = tiles;      
      
    }
                                           
    //string representation of this board
    public String toString()
    {        
        StringBuilder tablero = new StringBuilder();
       
        int n = tiles.length;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
               tablero.append(tiles[i][j]);
               tablero.append(" ");
            }
            tablero.replace(n-1, n-1, "");
            tablero.append('\n');
            
        }
        return tablero.toString();
    }

    // board dimension n
     public int dimension()
     {
        return tiles.length;
     }

    // // number of tiles out of place
    // public int hamming()

    // // sum of Manhattan distances between tiles and goal
    // public int manhattan()

    // // is this board the goal board?
    // public boolean isGoal()

    // // does this board equal y?
    // public boolean equals(Object y)

    // // all neighboring boards
    // public Iterable<Board> neighbors()

    // // a board that is obtained by exchanging any pair of tiles
    // public Board twin()

    // // unit testing (not graded)
    // public static void main(String[] args)
    
}
