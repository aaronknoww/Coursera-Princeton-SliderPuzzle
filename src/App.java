import javax.print.DocFlavor.STRING;

import edu.princeton.cs.algs4.ST;

public class App
{
    public static void main(String[] args) throws Exception
    {
        int[][] tiles = {{1,2,3},{4,7,6},{5,8,0}}; 
       
        Board tablero = new Board(tiles);
        
        System.out.println(tablero);
        System.out.println(tablero.dimension());
        System.out.println(tablero.hamming());

    }
}
