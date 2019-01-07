public class ElevensGUIRunner {

    public static void main(String[] args) {
        Board board = new ElevensBoard();
        CardGameGUI gui = new CardGameGUI(board);
        gui.displayGame();
    }
}