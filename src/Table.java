import java.util.ArrayList;

public class Table
{
    private ArrayList <Integer> entries;
    
    Table()
    {
        entries = new ArrayList<>();
    }
    
    boolean contains(State s)
    {
        s.normalizeState();
        
        int h = s.board.hashCode();
        
        return entries.contains(h);
    }
    
    int size() {return entries.size();}
    
    void add(State s)
    {
        s.normalizeState();
        
        int h = s.board.hashCode();
        
        entries.add(h);
    }
}