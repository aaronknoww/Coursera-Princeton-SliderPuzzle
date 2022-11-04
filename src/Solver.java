import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver 
{
    private int move;
    private boolean solvable;
    
    private MinPQ<NodeBoard> priorityPQ;
    private MinPQ<NodeBoard> twinPQ;
    private MinPQ<NodeBoard> createdNode; 
    private MinPQ<NodeBoard> createdtwin; 
    private Stack<Board> solutionStk;
    
    // find a solution to the initial board (using the A* algorithm)
    /**
     * @param initial
     */
    public Solver(final Board initial)
    {
        if(initial == null)
        throw new IllegalArgumentException("The null board is not allowed.");
        NodeBoard node;
        NodeBoard twinNode;
        NodeBoard aux;
        NodeBoard twinAux;
        NodeBoard nTop;
        NodeBoard twinTop;
        move = 0;
        solvable = false;
        createdNode = new MinPQ<NodeBoard>();
        createdtwin = new MinPQ<NodeBoard>();
                     
        if(initial.isGoal())
        {
            solvable = true;
            solutionStk = new Stack<Board>();
            solutionStk.push(initial);
            move = 0;
            return;
        }

        final Comparator<NodeBoard> mycomp = new NodeBoard().minNode();

        
        priorityPQ = new MinPQ<NodeBoard>(4, new Comparator<NodeBoard>() {
            
            @Override
            public int compare(NodeBoard board1, NodeBoard board2)
            {
                if( (board1.hamming + board1.manhattan) > (board2.hamming + board2.manhattan) ) return 1;
                if( (board1.hamming + board1.manhattan) < (board2.hamming + board2.manhattan) ) return -1;
                return 0;
            }    
        } );

        twinPQ = new MinPQ<NodeBoard>(4, new Comparator<NodeBoard>() {
            
            @Override
            public int compare(NodeBoard board1, NodeBoard board2)
            {
                if( (board1.hamming + board1.manhattan) > (board2.hamming + board2.manhattan) ) return 1;
                if( (board1.hamming + board1.manhattan) < (board2.hamming + board2.manhattan) ) return -1;
                return 0;
            }    
        } );

        createdNode = new MinPQ<NodeBoard>(4, mycomp);
        createdtwin = new MinPQ<NodeBoard>(4, mycomp);
       
        aux = new NodeBoard(initial, null);
        twinAux = new NodeBoard(initial.twin(), null);
        priorityPQ.insert(aux);
        twinPQ.insert(twinAux);               
        createdNode.insert(aux);
        createdtwin.insert(twinAux);

        do 
        {
            node = priorityPQ.delMin();
            twinNode = twinPQ.delMin();
                       
            for (Board board : node.getBoard().neighbors())
            {
                if ((node.getFather()!= null) && (node.getFather().getBoard().equals(board)))
                    continue;

                aux = new NodeBoard(board,node);

                for (NodeBoard nd : createdNode)
                {
                    if((aux != null) && aux.equal(nd))
                    {
                        aux=null;
                        break;
                    }
                    
                }

                if(aux == null)
                    continue;

                createdNode.insert(aux);
                priorityPQ.insert(aux); // To insert every neighbor in the preority queue, but need to be a board pointer.
                aux = null;                
            }

            // ||||||| TWIN AREA |||||||

            for (Board board : twinNode.getBoard().neighbors())
            {
                if ((twinNode.getFather()!= null) && (twinNode.getFather().getBoard().equals(board)))
                    continue;

                    twinAux = new NodeBoard(board,twinNode);

                for (NodeBoard nd : createdtwin)
                {
                    if((twinAux != null) && twinAux.equal(nd))
                    {
                        twinAux=null;
                        break;
                    }
                    
                }

                if(twinAux == null)
                    continue;                    

                createdtwin.insert(twinAux);
                twinPQ.insert(twinAux); // To insert every neighbor in the preority queue, but need to be a board pointer.
                twinAux = null;                
            }
           nTop = priorityPQ.min();
           twinTop = twinPQ.min();          
                       
        } while ((!nTop.board.isGoal()) && (!twinTop.board.isGoal()));


        if (nTop.getBoard().isGoal())
        {
            solutionStk = new Stack<Board>();
            while (nTop != null)
            {
                solutionStk.push(nTop.getBoard());
                nTop = nTop.getFather();
            }
            move = solutionStk.size();
            solvable = true;
            return;

        }
        else
        {
            move = -1;
            solvable = false;
            solutionStk = null;
            return;
        }       
    }

    // // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        return solvable;
    }

    // // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        return move; 
    }

    // // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        return solutionStk;
    }

    
    private class NodeBoard implements  Comparable<NodeBoard>
    {
        private final Board board;
        private final int hamming;
        private final int manhattan;
        private final NodeBoard father;
        
        public NodeBoard()
        {
            this.father = null;
            this.board = null;
            hamming = 0; 
            manhattan = 0;
        }        
        public NodeBoard(final Board board, NodeBoard father) // Here a constant reference of an object is received.
        {
            this.father = father;
            this.board = board;
            hamming = this.board.hamming(); // Because it is a reference is possible to use methods in this way.
            manhattan = this.board.manhattan();
        }
        public Board getBoard()
        {
            if(board != null)
            return board;
            
            return null;
        }
    
        public NodeBoard getFather()
        {
            return father;
        }
        public Comparator<NodeBoard> minNode()
        {
            return new PriorityNode();
        }
        public boolean equal(NodeBoard node)
        {
            if(this.board.equals(node.getBoard()))
                return true;
            return false;
        }           
        public class PriorityNode implements Comparator<NodeBoard>
        {                
            @Override
            public int compare(NodeBoard board1, NodeBoard board2)
            {
                if( (board1.hamming + board1.manhattan) > (board2.hamming + board2.manhattan) ) return 1;
                if( (board1.hamming + board1.manhattan) < (board2.hamming + board2.manhattan) ) return -1;
                return 0;
            }          
        }

        @Override
        public int compareTo(Solver.NodeBoard node)
        {
            if ((this.hamming + this.manhattan) > (node.hamming + node.manhattan)) return 1;
            if ((this.hamming + this.manhattan) < (node.hamming + node.manhattan)) return 1;
            return 0;
        }
    }

    // // test client (see below) 
    public static void main(String[] args)
    {
        // int[][] tiles = {{1,2,3},
        //                  {4,0,5},
        //                  {7,8,6}};

        int[][] tiles = {{8,6,7},
                         {2,5,4},
                         {3,0,1}};
        
        // No tiene solucion.
        // int[][] tiles = {{8,6,7},
        //                  {2,5,4},
        //                  {1,3,0}};



        Board tablero = new Board(tiles);
        Solver sol = new Solver(tablero);

        //var arr = tablero.neighbors();

        if(!sol.solvable)
            StdOut.println("No tiene Solucion");
            

        for (var e : sol.solutionStk)
        {
            StdOut.print(e + " ");
            StdOut.println("Hamming: " + e.hamming());
            StdOut.println("Manhattan: " + e.manhattan());

            
        }
        StdOut.println(sol.moves());         
    }
    
}
