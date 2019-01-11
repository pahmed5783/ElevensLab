import java.util.List;

public class TensBoard extends Board {

    
    private static final int BOARD_SIZE = 13;

    
    private static final String[] RANKS =
            {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

    
    private static final String[] SUITS =
            {"spades", "hearts", "diamonds", "clubs"};

    
    private static final int[] POINT_VALUES =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0};

    
    private static final boolean I_AM_DEBUGGING = false;


    
    public TensBoard() { super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES); }

    
    @Override
    public boolean isLegal(List<Integer> selectedCards) {
        if (selectedCards.size() == 2) {
            return containsPairSum10(selectedCards);
        } else if (selectedCards.size() == 4) {
            return containsQuartet(selectedCards);
        } else {
            return false;
        }
    }

    
    @Override
    public boolean anotherPlayIsPossible() {
        return (containsPairSum10(cardIndexes()) || containsQuartet(cardIndexes()));
    }

    
    private boolean containsPairSum10(List<Integer> selectedCards) {
        int sum = 0;
        for (int i = 0; i < selectedCards.size(); i++) {
            for (int j = i + 1; j < selectedCards.size(); j++) {
                sum = cardAt(selectedCards.get(i)).getPointValue() + cardAt(selectedCards.get(j)).getPointValue();
                if (sum == 10) {
                    return true;
                }
            }
        }
        return false;
    }

    
    private boolean containsQuartet(List<Integer> selectedCards) {
        int jackAmountFound = 0;
        int queenAmountFound = 0;
        int kingAmountFound = 0;
        int tenAmountFound = 0;
        for (int cardIndex : selectedCards) {
            switch (cardAt(cardIndex).getRank()) {
                case "jack":
                    jackAmountFound++;
                    break;
                case "queen":
                    queenAmountFound++;
                    break;
                case "king":
                    kingAmountFound++;
                    break;
                case "10":
                    tenAmountFound++;
                    break;
            }
        }
        return (jackAmountFound == 4 || queenAmountFound == 4 || kingAmountFound == 4 || tenAmountFound == 4);
    }

    
    public static void printCards(ElevensBoard board) {
        List<Integer> cIndexes = board.cardIndexes();
        for (int i : cIndexes) {
            System.out.println(board.cardAt(i));
        }
    }
}