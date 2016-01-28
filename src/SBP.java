public class SBP
{    
    public static void main(String args[])
    {
        Board StartState = new Board("D:\\Drexel\\CS 510\\HW1\\SBP-test-not-normalized.txt");
        
        if(!StartState.isValid())
        {
            System.out.println("Missing or invalid input file.");
            return;
        }
        
        StartState.outputGameState();
        
        StartState.normalizeState();
        
        System.out.println("Normalized");
        StartState.outputGameState();
    }
}