import java.awt.Point;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;


public class CardGameGUI extends JFrame implements ActionListener {

    
    private static final int DEFAULT_HEIGHT = 302;
    
    private static final int DEFAULT_WIDTH = 800;
    
    private static final int CARD_WIDTH = 73;
    
    private static final int CARD_HEIGHT = 97;
    
    private static final int LAYOUT_TOP = 30;
    
    private static final int LAYOUT_LEFT = 30;
    
    private static final int LAYOUT_WIDTH_INC = 100;
    
    private static final int LAYOUT_HEIGHT_INC = 125;
    
    private static final int BUTTON_TOP = 30;
    
    private static final int BUTTON_LEFT = 570;
    
    private static final int BUTTON_HEIGHT_INC = 50;
    
    private static final int LABEL_TOP = 160;
    
    private static final int LABEL_LEFT = 540;
    
    private static final int LABEL_HEIGHT_INC = 35;

    
    private Board board;

    
    private JPanel panel;
    
    private JButton replaceButton;
    
    private JButton restartButton;
    
    private JLabel statusMsg;
    
    private JLabel totalsMsg;
    
    private JLabel[] displayCards;
    
    private JLabel winMsg;
    
    private JLabel lossMsg;
    
    private Point[] cardCoords;

    
    private boolean[] selections;
    
    private int totalWins;
    
    private int totalGames;


    
    public CardGameGUI(Board gameBoard) {
        board = gameBoard;
        totalWins = 0;
        totalGames = 0;


        cardCoords = new Point[board.size()];
        int x = LAYOUT_LEFT;
        int y = LAYOUT_TOP;
        for (int i = 0; i < cardCoords.length; i++) {
            cardCoords[i] = new Point(x, y);
            if (i % 5 == 4) {
                x = LAYOUT_LEFT;
                y += LAYOUT_HEIGHT_INC;
            } else {
                x += LAYOUT_WIDTH_INC;
            }
        }

        selections = new boolean[board.size()];
        initDisplay();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

    
    public void displayGame() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }

    
    public void repaint() {
        for (int k = 0; k < board.size(); k++) {
            String cardImageFileName =
                    imageFileName(board.cardAt(k), selections[k]);
            URL imageURL = getClass().getResource(cardImageFileName);
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(imageURL);
                displayCards[k].setIcon(icon);
                displayCards[k].setVisible(true);
            } else {
                throw new RuntimeException(
                        "Card image not found: \"" + cardImageFileName + "\"");
            }
        }
        statusMsg.setText(board.deckSize()
                + " undealt cards remain.");
        statusMsg.setVisible(true);
        totalsMsg.setText("You've won " + totalWins
                + " out of " + totalGames + " games.");
        totalsMsg.setVisible(true);
        pack();
        panel.repaint();
    }

    
    private void initDisplay()	{
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };



        String className = board.getClass().getSimpleName();
        int classNameLen = className.length();
        int boardLen = "Board".length();
        String boardStr = className.substring(classNameLen - boardLen);
        if (boardStr.equals("Board") || boardStr.equals("board")) {
            int titleLength = classNameLen - boardLen;
            setTitle(className.substring(0, titleLength));
        }



        int numCardRows = (board.size() + 4) / 5;
        int height = DEFAULT_HEIGHT;
        if (numCardRows > 2) {
            height += (numCardRows - 2) * LAYOUT_HEIGHT_INC;
        }

        this.setSize(new Dimension(DEFAULT_WIDTH, height));
        panel.setLayout(null);
        panel.setPreferredSize(
                new Dimension(DEFAULT_WIDTH - 20, height - 20));
        displayCards = new JLabel[board.size()];
        for (int k = 0; k < board.size(); k++) {
            displayCards[k] = new JLabel();
            panel.add(displayCards[k]);
            displayCards[k].setBounds(cardCoords[k].x, cardCoords[k].y,
                    CARD_WIDTH, CARD_HEIGHT);
            displayCards[k].addMouseListener(new MyMouseListener());
            selections[k] = false;
        }
        replaceButton = new JButton();
        replaceButton.setText("Replace");
        panel.add(replaceButton);
        replaceButton.setBounds(BUTTON_LEFT, BUTTON_TOP, 100, 30);
        replaceButton.addActionListener(this);

        restartButton = new JButton();
        restartButton.setText("Restart");
        panel.add(restartButton);
        restartButton.setBounds(BUTTON_LEFT, BUTTON_TOP + BUTTON_HEIGHT_INC,
                100, 30);
        restartButton.addActionListener(this);

        statusMsg = new JLabel(
                board.deckSize() + " undealt cards remain.");
        panel.add(statusMsg);
        statusMsg.setBounds(LABEL_LEFT, LABEL_TOP, 250, 30);

        winMsg = new JLabel();
        winMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
        winMsg.setFont(new Font("SansSerif", Font.BOLD, 25));
        winMsg.setForeground(Color.GREEN);
        winMsg.setText("You win!");
        panel.add(winMsg);
        winMsg.setVisible(false);

        lossMsg = new JLabel();
        lossMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
        lossMsg.setFont(new Font("SanSerif", Font.BOLD, 25));
        lossMsg.setForeground(Color.RED);
        lossMsg.setText("Sorry, you lose.");
        panel.add(lossMsg);
        lossMsg.setVisible(false);

        totalsMsg = new JLabel("You've won " + totalWins
                + " out of " + totalGames + " games.");
        totalsMsg.setBounds(LABEL_LEFT, LABEL_TOP + 2 * LABEL_HEIGHT_INC,
                250, 30);
        panel.add(totalsMsg);

        if (!board.anotherPlayIsPossible()) {
            signalLoss();
        }

        pack();
        getContentPane().add(panel);
        getRootPane().setDefaultButton(replaceButton);
        panel.setVisible(true);
    }

    
    private void signalError() {
        Toolkit t = panel.getToolkit();
        t.beep();
    }

    
    private String imageFileName(Card c, boolean isSelected) {
        String str = "cards/";
        if (c == null) {
            return "cards/back1.GIF";
        }
        str += c.getRank() + c.getSuit();
        if (isSelected) {
            str += "S";
        }
        str += ".GIF";
        return str;
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(replaceButton)) {

            List<Integer> selection = new ArrayList<Integer>();
            for (int k = 0; k < board.size(); k++) {
                if (selections[k]) {
                    selection.add(new Integer(k));
                }
            }

            if (!board.isLegal(selection)) {
                signalError();
                return;
            }
            for (int k = 0; k < board.size(); k++) {
                selections[k] = false;
            }

            board.replaceSelectedCards(selection);
            if (board.isEmpty()) {
                signalWin();
            } else if (!board.anotherPlayIsPossible()) {
                signalLoss();
            }
            repaint();
        } else if (e.getSource().equals(restartButton)) {
            board.newGame();
            getRootPane().setDefaultButton(replaceButton);
            winMsg.setVisible(false);
            lossMsg.setVisible(false);
            if (!board.anotherPlayIsPossible()) {
                signalLoss();
                lossMsg.setVisible(true);
            }
            for (int i = 0; i < selections.length; i++) {
                selections[i] = false;
            }
            repaint();
        } else {
            signalError();
            return;
        }
    }

    
    private void signalWin() {
        getRootPane().setDefaultButton(restartButton);
        winMsg.setVisible(true);
        totalWins++;
        totalGames++;
    }

    
    private void signalLoss() {
        getRootPane().setDefaultButton(restartButton);
        lossMsg.setVisible(true);
        totalGames++;
    }

    
    private class MyMouseListener implements MouseListener {

        
        public void mouseClicked(MouseEvent e) {
            for (int k = 0; k < board.size(); k++) {
                if (e.getSource().equals(displayCards[k])
                        && board.cardAt(k) != null) {
                    selections[k] = !selections[k];
                    repaint();
                    return;
                }
            }
            signalError();
        }

        
        public void mouseExited(MouseEvent e) {
        }

        
        public void mouseReleased(MouseEvent e) {
        }

        
        public void mouseEntered(MouseEvent e) {
        }

        
        public void mousePressed(MouseEvent e) {
        }
    }
}