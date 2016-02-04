import java.util.ArrayList;

public class Search
{
    void Random(State StartState, int n)
    {
        int depth;
        
        ArrayList <Move> MoveList = StartState.allMoves();
        
        Move thisMove = getRandom(MoveList);
    }
    
    void BFS()
    {
        
    }
    
    void DFS()
    {
        
    }
    
    void aStar(State StartState)
    {
        int [][] visited;
    }
    
    Move getRandom(ArrayList <Move> MoveList)
    {
        int index = -1;
        int size = MoveList.size();
        
        while(index<0 || index>size-1)
             index = (int) (size*Math.random());
        
        return MoveList.get(index);
    }
}