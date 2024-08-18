package com.mycompany.wanderinginwoodslauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WanderingInWoodsK2 extends JPanel implements ActionListener {
    private Timer timer;
    private int gridSize = 10;
    private Point character1, character2;
    private Random random = new Random();
    private int moves = 0;
    private ImageIcon character1Icon;
    private ImageIcon character2Icon;
    private JButton backButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel movesLabel;

    public WanderingInWoodsK2(JFrame parentFrame) {
        character1 = new Point(0, 0);
        character2 = new Point(gridSize - 1, gridSize - 1);
        timer = new Timer(1000, this);
        timer.start();

        // Load character images
        character1Icon = new ImageIcon(getClass().getResource("/character1.png"));
        character2Icon = new ImageIcon(getClass().getResource("/character2.png"));

        // Set layout to null for absolute positioning
        this.setLayout(null);

        // Add Back button
        backButton = new JButton("Back");
        backButton.setBounds(10, 520, 80, 30); // Position at bottom left
        backButton.addActionListener(e -> {
            parentFrame.dispose(); // Close the current game window
            SwingUtilities.invokeLater(() -> {
                WanderingInWoodsLauncher.main(new String[0]); // Reopen the main menu
            });
        });
        this.add(backButton);

        // Add Reset button
        resetButton = new JButton("Reset");
        resetButton.setBounds(100, 520, 80, 30); // Position next to Back button
        resetButton.addActionListener(e -> {
            resetGame(); // Reset the game when clicked
            repaint(); // Repaint the grid after resetting
        });
        this.add(resetButton);

        // Add Home button
        homeButton = new JButton("Home");
        homeButton.setBounds(190, 520, 80, 30); // Position next to Reset button
        homeButton.addActionListener(e -> {
            parentFrame.dispose(); // Close the current game window
            SwingUtilities.invokeLater(() -> {
                WanderingInWoodsLauncher.main(new String[0]); // Reopen the main menu
            });
        });
        this.add(homeButton);

        // Add Moves Label
        movesLabel = new JLabel("Total Moves: 0");
        movesLabel.setBounds(300, 520, 150, 30); // Position next to Home button
        this.add(movesLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                g.drawRect(i * 50, j * 50, 50, 50);
            }
        }

        // Draw the characters using the images
        g.drawImage(character1Icon.getImage(), character1.x * 50, character1.y * 50, 50, 50, this);
        g.drawImage(character2Icon.getImage(), character2.x * 50, character2.y * 50, 50, 50, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveCharacter(character1);
        moveCharacter(character2);

        if (character1.equals(character2)) {
            playHappySound();
            JOptionPane.showMessageDialog(this, "Characters met after " + moves + " moves!");
            resetGame();
        }
        repaint();
    }

    private void moveCharacter(Point character) {
        int moveX = random.nextInt(3) - 1; // -1, 0, 1
        int moveY = random.nextInt(3) - 1;
        character.translate(moveX, moveY);
        if (character.x < 0) character.x = 0;
        if (character.x >= gridSize) character.x = gridSize - 1;
        if (character.y < 0) character.y = 0;
        if (character.y >= gridSize) character.y = gridSize - 1;

        moves++;
        movesLabel.setText("Total Moves: " + moves); // Update moves count label
    }

    private void playHappySound() {
        // Add code to play a happy sound when characters meet
    }

    private void resetGame() {
        character1.setLocation(0, 0);
        character2.setLocation(gridSize - 1, gridSize - 1);
        moves = 0;
        movesLabel.setText("Total Moves: " + moves); // Reset moves count label
    }

    public int getMovesCount() {
        return moves;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wandering in the Woods - K2");
        WanderingInWoodsK2 game = new WanderingInWoodsK2(frame);
        frame.add(game);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
