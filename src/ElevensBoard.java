import java.util.List;
import java.util.ArrayList;


public class ElevensBoard extends Board {

    
    private static final int BOARD_SIZE = 9;

    
    private static final String[] RANKS =
            {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

    
    private static final String[] SUITS =
            {"spades", "hearts", "diamonds", "clubs"};

    
    private static final int[] POINT_VALUES =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0};

    
    private static final boolean I_AM_DEBUGGING = false;


    
    public ElevensBoard() {
        super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES);
    }

    
    @Override
    public boolean isLegal(List<Integer> selectedCards) {
        
        return (containsPairSum11(selectedCards) || containsJQK(selectedCards));
    }

    
    @Override
    public boolean anotherPlayIsPossible() {
        
        return isLegal(this.cardIndexes());
    }

    
    private boolean containsPairSum11(List<Integer> selectedCards) {
        
        int sum = 0;
        if(selectedCards.size() == 2) {
            for(int i : selectedCards)
            {
                sum += this.cardAt(i).getPointValue();
            }
            if(sum == 11)
            {
                return true;
            }
        }
        else {

            for (int i = 0; i < selectedCards.size(); i++) {

                for (int j = i + 1; j < selectedCards.size(); j++) {
                    sum = this.cardAt(i).getPointValue() + this.cardAt(j).getPointValue();
                    if (sum == 11) {
                        return true;
                    }
                }

            }
        }
       return false;
    }

    
    private boolean containsJQK(List<Integer> selectedCards) {
        
        boolean jFound = false;
        boolean qFound = false;
        boolean kFound = false;
        for(int i : selectedCards)
        {
            if(this.cardAt(i).getRank().contains("jack"))
            {
                jFound = true;
            }
            else if(this.cardAt(i).getRank().contains("queen"))
            {
                qFound = true;
            }
            else if(this.cardAt(i).getRank().contains("king"))
            {
                kFound = true;
            }
        }
        return (jFound && qFound && kFound);
    }

    
    public static void printCards(ElevensBoard board) {
        List<Integer> cIndexes = board.cardIndexes();
        for (int i : cIndexes) {
            System.out.println(board.cardAt(i));
        }
    }
}