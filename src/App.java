
import edu.princeton.cs.algs4.StdOut;

public class App
{
    public static void main(String[] args) throws Exception
    {
        // int[][] tiles = {{8,1,3},
        //                  {4,0,2},
        //                  {7,6,5}};

        // int[][] tiles = {{1,2,3},
        //                  {4,5,6},
        //                  {7,8,0}};
        
        int[][] tiles = {{1,2,3},
                         {4,5,0},
                         {7,8,6}};


       
        Board tablero = new Board(tiles);
        
        


        StdOut.println("Tablero original ");
        StdOut.print(tablero+ " ");
        StdOut.println("Hamming: "+ tablero.hamming());
        StdOut.println("Manhattan: "+tablero.manhattan());
        StdOut.println("\n");
        
        
        var arr = tablero.neighbors();

        StdOut.println("VECINOS ");
        for (var e : arr)
        {
            StdOut.print(e + " ");
            StdOut.println("Hamming: " + e.hamming());
            StdOut.println("Manhattan: " + e.manhattan());

        }


    
        


    
        

    }
}
