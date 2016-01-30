import java.util.ArrayList;

public class Table
{
    private ArrayList entries;
    
    Table()
    {
        entries = new ArrayList<>();
    }
    
    boolean contains()
    {
        return entries.contains(this);
    }
    
    int size()
    {
        return entries.size();
    }
    
    boolean add(State s)
    {
        s.normalizeState();
        
        int h = s.board.hashCode();
        
        return true;
    }
}
