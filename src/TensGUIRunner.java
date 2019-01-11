public class TensGUIRunner {
    public static void main(String[] args) {
        Board board = new TensBoard();
        board.newGame();
        CardGameGUI gui = new CardGameGUI(board);
        gui.displayGame();
    }
}