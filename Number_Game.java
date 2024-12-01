import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

public class Number_Game extends JFrame {
    private int randomNumber;
    private int attempts;
    private final int maxAttempts = 10;
    private int score;
    private final JTextField guessField;
    private final JLabel feedbackLabel;
    private final JLabel scoreLabel;
    private final JButton guessButton;
    private final JButton newGameButton;

    public Number_Game() {
        setTitle("Number Guessing Game");
        setSize(480, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel instructionLabel = new JLabel("Guess the number between 1 and 100:", SwingConstants.CENTER);
        panel.add(instructionLabel);

        guessField = new JTextField();
        panel.add(guessField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());
        panel.add(guessButton);

        feedbackLabel = new JLabel("You have " + maxAttempts + " attempts remaining.", SwingConstants.CENTER);
        panel.add(feedbackLabel);

        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        panel.add(scoreLabel);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new NewGameButtonListener());
        newGameButton.setEnabled(false);
        panel.add(newGameButton);

        add(panel);
        startNewRound();
    }

    private void startNewRound() {
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;
        attempts = 0;
        feedbackLabel.setText("You have " + maxAttempts + " attempts remaining.");
        guessField.setText("");
        guessButton.setEnabled(true);
        newGameButton.setEnabled(false);
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guessText = guessField.getText();
            try {
                int guess = Integer.parseInt(guessText);
                attempts++;
                int remainingAttempts = maxAttempts - attempts;

                if (guess == randomNumber) {
                    feedbackLabel.setText("Correct! You guessed the number in " + attempts + " attempts.");
                    score += maxAttempts - attempts + 1;
                    scoreLabel.setText("Score: " + score);
                    guessButton.setEnabled(false);
                    newGameButton.setEnabled(true);
                } else if (guess < randomNumber) {
                    feedbackLabel.setText("Too low! " + remainingAttempts + " attempts remaining.");
                } else {
                    feedbackLabel.setText("Too high! " + remainingAttempts + " attempts remaining.");
                }

                if (remainingAttempts == 0 && guess != randomNumber) {
                    feedbackLabel.setText("Out of attempts! The number was " + randomNumber + ".");
                    guessButton.setEnabled(false);
                    newGameButton.setEnabled(true);
                }

            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
            }
        }
    }

    private class NewGameButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startNewRound();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Number_Game game = new Number_Game();
            game.setVisible(true);
        });
    }
}
