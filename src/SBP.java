import java.util.ArrayList;

public class SBP
{    
    /**Stores path of level file.*/
    String path;
    
    SBP(String path)
    {
        this.path = path;
    }
    
    public static void main(String args[])
    {
        String path = "B:\\Documents\\Drexel\\CS 510\\HW1\\SBP-level3.txt";
        
        State StartState = new State(path);
        
        if(!StartState.isValid())
        {
            System.out.println("Missing or invalid input file.");
            return;
        }
        
        StartState.normalizeState();
        StartState.outputGameState();
        System.out.println("Manhattan Distance = " + StartState.ManhattanDistance());
        
        System.out.println("Piece List");
        ArrayList <Integer> PieceList = StartState.getPieceList();
        for(int x : PieceList) System.out.print(x + ",");
        System.out.println();
        
        System.out.println("Move List");
        ArrayList <Move> MoveList = StartState.allMoves();
        
        System.out.println("Number of moves = " + MoveList.size());
        
        for(Move x : MoveList) System.out.println(x.piece + "   " + x.direction);
    }
}