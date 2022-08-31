import javax.print.DocFlavor.STRING;

import edu.princeton.cs.algs4.ST;

public class App
{
    public static void main(String[] args) throws Exception
    {
        int[][] tiles = {{8,1,3},
                         {4,0,2},
                         {7,6,5}}; 
       
        Board tablero = new Board(tiles);
        
        System.out.println(tablero);
        System.out.println(tablero.dimension());
        System.out.println(tablero.hamming());
        System.out.println(tablero.manhattan());
        

    }
}
