package com.mycompany.wanderinginwoodslauncher;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WanderingInWoods35 extends JPanel {
    private Timer timer;
    private int gridSizeX;
    private int gridSizeY;
    private List<Point> characters;
    private Random random = new Random();
    private int moves = 0;
    private List<Integer> moveCounts;
    private ImageIcon[] characterIcons;
    private JButton backButton;
    private JButton resetButton;
    private JButton homeButton;
    private JLabel movesLabel;

    public WanderingInWoods35(int gridX, int gridY, int characterCount, JFrame parentFrame) {
        this.gridSizeX = gridX;
        this.gridSizeY = gridY;
        this.characters = new ArrayList<>();
        this.moveCounts = new ArrayList<>();
        this.characterIcons = new ImageIcon[characterCount];

        // Load character images with error handling
        for (int i = 0; i < characterCount; i++) {
            String imagePath = "character" + (i + 1) + ".png";
            characterIcons[i] = loadImageIcon(imagePath);
            if (characterIcons[i] == null) {
                // Fallback to an empty icon if the image could not be loaded
                characterIcons[i] = new ImageIcon();
            }
            Point p = new Point(random.nextInt(gridSizeX), random.nextInt(gridSizeY));
            characters.add(p);
            moveCounts.add(0);
        }

        timer = new Timer(1000, e -> updateGame());
        timer.start();

        this.setLayout(null); // Absolute positioning for custom layout

        // Add Back button
        backButton = new JButton("Back");
        backButton.setBounds(10, 520, 80, 30);
        backButton.addActionListener(e -> {
            parentFrame.dispose();
            SwingUtilities.invokeLater(() -> WanderingInWoodsLauncher.main(new String[0]));
        });
        this.add(backButton);

        // Add Reset button
        resetButton = new JButton("Reset");
        resetButton.setBounds(100, 520, 80, 30);
        resetButton.addActionListener(e -> {
            resetGame();
            repaint();
        });
        this.add(resetButton);

        // Add Home button
        homeButton = new JButton("Home");
        homeButton.setBounds(190, 520, 80, 30);
        homeButton.addActionListener(e -> {
            parentFrame.dispose();
            SwingUtilities.invokeLater(() -> WanderingInWoodsLauncher.main(new String[0]));
        });
        this.add(homeButton);

        // Add Moves Label
        movesLabel = new JLabel("Total Moves: 0");
        movesLabel.setBounds(300, 520, 150, 30);
        this.add(movesLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                g.drawRect(i * 40, j * 40, 40, 40); // Draw the grid
            }
        }

        // Draw the characters using the images
        for (int i = 0; i < characters.size(); i++) {
            g.drawImage(characterIcons[i].getImage(), characters.get(i).x * 40, characters.get(i).y * 40, 40, 40, this);
        }
    }

    private void updateGame() {
        for (int i = 0; i < characters.size(); i++) {
            moveCharacter(characters.get(i), i);
        }

        if (allCharactersMet()) {
            ImageIcon icon = loadImageIcon("congrats.png");
            JOptionPane.showMessageDialog(this, "Characters met after " + moves + " moves!", "Congratulations", JOptionPane.INFORMATION_MESSAGE, icon);
            resetGame();
        }

        repaint(); // Repaint the grid and characters
    }

    private void moveCharacter(Point character, int index) {
        int moveX = random.nextInt(3) - 1; // -1, 0, 1
        int moveY = random.nextInt(3) - 1;
        character.translate(moveX, moveY);
        if (character.x < 0) character.x = 0;
        if (character.x >= gridSizeX) character.x = gridSizeX - 1;
        if (character.y < 0) character.y = 0;
        if (character.y >= gridSizeY) character.y = gridSizeY - 1;

        moves++;
        moveCounts.set(index, moveCounts.get(index) + 1);
        movesLabel.setText("Total Moves: " + moves); // Update moves count label
    }

    private boolean allCharactersMet() {
        Point first = characters.get(0);
        for (Point character : characters) {
            if (!character.equals(first)) {
                return false;
            }
        }
        return true;
    }

    private void resetGame() {
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).setLocation(random.nextInt(gridSizeX), random.nextInt(gridSizeY));
            moveCounts.set(i, 0);
        }
        moves = 0;
        movesLabel.setText("Total Moves: " + moves); // Reset moves count label
    }

    private ImageIcon loadImageIcon(String path) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wandering in the Woods - 3-5");
        WanderingInWoods35 game = new WanderingInWoods35(10, 10, 3, frame); // Example: 10x10 grid, 3 characters
        frame.add(game);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
