
import java.util.ArrayList;

public class SBP
{    
    public static void main(String args[])
    {
        Board StartState = new Board("B:\\Documents\\Drexel\\CS 510\\HW1\\SBP-level0.txt");
        
        if(!StartState.isValid())
        {
            System.out.println("Missing or invalid input file.");
            return;
        }
        
        StartState.outputGameState();
        
        StartState.normalizeState();
        
        System.out.println("Normalized");
        StartState.outputGameState();
        
        System.out.println("Move List");
        ArrayList <Move> MoveList = StartState.allMoves();
        
        System.out.println("Number of moves = " + MoveList.size());
        
        if(MoveList == null) System.out.println("Null piece List");
        else for(Move x : MoveList) System.out.println(x.piece + "   " + x.direction);
    }
}