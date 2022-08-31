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
        tablero.append(tiles.length);
        tablero.append('\n');

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

    // number of tiles out of place
    public int hamming()
    {
        int count = 0;
       
        // To check every tile.
        for (int row = 0; row < tiles.length; row++)
        {
            for (int col = 0; col < tiles.length; col++)
            {
                if(tiles[row][col] == 0)
                    continue;
             
               if(!(tiles[row][col] == ((row*tiles.length)+col+1)))
                    count++;
            }           
            
        }
        
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        
        int fila = 0; 
        int columna = 0;
        int man = 0;
        
        // To check every tile.
        for (int row = 0; row < tiles.length; row++)
        {
            for (int col = 0; col < tiles.length; col++)
            {
                if(tiles[row][col] == 0)
                    continue;
                if(tiles[row][col] != ((row*tiles.length)+col+1))
                {                    
                    fila = tiles[row][col]/tiles.length; //----------> To find the row it belongs to the current number
                    columna = tiles[row][col]-(fila*tiles.length+1);//-> To find the column it belongs to the current number
                    man += Math.abs(fila-row)+ Math.abs(columna-col);
                }
             
               
            }           
            
        }

        return man;
    }

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
