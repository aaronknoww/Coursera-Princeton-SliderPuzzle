import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class Board
{
    private int [][] tiles;
    private Stack<Board> sNeighbors;
    private int col0;//---------------->To save the position of the number zero or empty space. 
    private int fil0;//---------------->To save the position of the number zero or empty space.
    private Board gemelo;//------------>To store a twin 
    
     // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)  You may also assume that 2 ≤ n < 128
    public Board(int[][] tiles)
    {
        if((tiles.length < 2) || (tiles.length>128))
            throw new IndexOutOfBoundsException("number of n is out of limits"); // TODO: REVISAR QUE EXEPCION MANDAR.
        
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles.length; j++)
                this.tiles[i][j] = tiles[i][j];                         
        }
        sNeighbors = new Stack<Board>();    
        col0 = 0;
        fil0 = 0;      
      
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
                    fila = (tiles[row][col]-1)/tiles.length; //----------> To find the row it belongs to the current number
                    columna = tiles[row][col]-(fila*tiles.length+1);//-> To find the column it belongs to the current number
                    man += Math.abs(fila-row)+ Math.abs(columna-col);
                }
             
               
            }           
            
        }

        return man;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        for (int row = 0; row < tiles.length; row++)
        {
            for (int col = 0; col < tiles.length; col++)
            {
                if(tiles[row][col] == 0)
                    continue;
             
               if(tiles[row][col] != ((row*tiles.length)+col+1))
                    return false;
            }           
            
        }
        return true;
    }

    // // does this board equal y?
    public boolean equals(Object y)
    {
        Board other = (Board)y;
        return Arrays.deepEquals(this.tiles, other.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        if(!sNeighbors.isEmpty())
            return sNeighbors;
            
        boolean zero = false;
        
        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles.length; j++)
            {
                if(tiles[i][j] == 0) 
                {
                    fil0 = i;
                    col0 = j;
                    zero = true;
                    break;
                }
                
            }
            if(zero)
                break;
        }

        if (col0-1 >= 0)
            createNeighbor(fil0, col0-1);   
        if (col0+1 < tiles.length)
            createNeighbor(fil0, col0+1);
        if (fil0-1 >= 0)
            createNeighbor(fil0-1, col0);
        if (fil0+1 < tiles.length)
            createNeighbor(fil0+1, col0);   

        return sNeighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
        if(gemelo!=null)
            return gemelo;

        int x;
        int y;
        int x2;
        int y2;

        boolean condition = true;
        do
        {
            x  = StdRandom.uniform(0, tiles.length);
            y  = StdRandom.uniform(0, tiles.length);
            x2 = StdRandom.uniform(0, tiles.length);
            y2 = StdRandom.uniform(0, tiles.length);
            if(tiles[x][y] == 0 || tiles[x2][y2] == 0)
                condition = true;
            else if(tiles[x][y] == tiles[x2][y2])
                condition = true;
            else
                condition = false;
        }
        while (condition);

        int aux = 0;

        // Creating twin
        aux = tiles[x][y];
        tiles[x][y] = tiles[x2][y2];
        tiles[x2][y2] = aux;
        gemelo = new Board(tiles);

        // Returning tile to its intial state.

        tiles[x2][y2] = tiles[x][y];
        tiles[x][y] = aux;
        
        return gemelo;        
    }


    ////////////////////////////////////////////////////////////////////////
    //                      PRIVATE FUNCIONS                             //
    ////////////////////////////////////////////////////////////////////// 

    private void createNeighbor(int fila, int col)
    {   
        

        tiles[fil0][col0] = tiles[fila][col];
        tiles[fila][col] = 0;
        sNeighbors.add(new Board(tiles));
        tiles[fila][col] = tiles[fil0][col0];
        tiles[fil0][col0] = 0;

    }

    // // unit testing (not graded)
    public static void main(String[] args)
    {
        int[][] tiles = {{1,2,3},
                         {4,0,5},
                         {7,8,6}};

        Board tablero = new Board(tiles);

        for (Board board : tablero.neighbors())
        {
            board.manhattan();
            StdOut.print(board.toString()); 
            
        }

        StdOut.println(tablero.manhattan());
        StdOut.println(tablero.hamming());
        StdOut.println(tablero.twin().toString());
        StdOut.println(tablero.dimension());
        StdOut.println(tablero.isGoal());
    
    }
    
}
