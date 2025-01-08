import javax.swing.*;
import java.awt.event.*;

public class CalculatorApp {

    private static double num1 = 0, num2 = 0, result = 0;
    private static char operator = ' ';
    private static boolean isOperatorPressed = false;

    public static void main(String[] args) {
        // Frame
        JFrame frame = new JFrame("Calculator");
        frame.setBounds(560, 27, 380, 420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Text Field
        JTextField textField = new JTextField();
        textField.setBounds(0, 0, 360, 80);
        textField.setEditable(false);
        frame.add(textField);

        // Button Labels
        String[] buttonLabels = {
            "CE", "C", "X", "÷",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        int x = 2, y = 80, width = 88, height = 56;

        // Loop to create buttons
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setBounds(x, y, width, height);
            frame.add(button);

            // Adjust position for the next button
            x += width + 2;
            if (x > 360) {
                x = 2;
                y += height + 2;
            }

            // Add ActionListener for button functionality
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String command = button.getText();
                    handleButtonClick(command, textField);
                }
            });
        }

        frame.setVisible(true);
    }

    // Method to handle button click events
    private static void handleButtonClick(String command, JTextField textField) {
        try {
            switch (command) {
                case "CE":
                    // Clear only the current input
                    textField.setText("");
                    break;
                case "C":
                    // Reset everything
                    textField.setText("");
                    num1 = num2 = result = 0;
                    operator = ' ';
                    isOperatorPressed = false;
                    break;
                case "+/-":
                    // Toggle sign of the current input
                    if (!textField.getText().isEmpty()) {
                        double value = Double.parseDouble(textField.getText());
                        value *= -1;
                        textField.setText(String.valueOf(value));
                    }
                    break;
                case ".":
                    // Add decimal point
                    if (!textField.getText().contains(".")) {
                        textField.setText(textField.getText() + ".");
                    }
                    break;
                case "+":
                case "-":
                case "*":
                case "÷":
                    // Set operator and first number
                    if (!textField.getText().isEmpty()) {
                        num1 = Double.parseDouble(textField.getText());
                        operator = command.charAt(0);
                        isOperatorPressed = true;
                        textField.setText("");
                    }
                    break;
                case "=":
                    // Perform calculation
                    if (!textField.getText().isEmpty() && operator != ' ') {
                        num2 = Double.parseDouble(textField.getText());
                        switch (operator) {
                            case '+': result = num1 + num2; break;
                            case '-': result = num1 - num2; break;
                            case '*': result = num1 * num2; break;
                            case '÷': 
                                if (num2 == 0) {
                                    textField.setText("Error");
                                    return;
                                }
                                result = num1 / num2; 
                                break;
                        }
                        textField.setText(String.valueOf(result));
                        operator = ' ';
                        isOperatorPressed = false;
                    }
                    break;
                default:
                    // Handle number buttons
                    if (isOperatorPressed) {
                        textField.setText("");
                        isOperatorPressed = false;
                    }
                    textField.setText(textField.getText() + command);
                    break;
            }
        } catch (Exception ex) {
            textField.setText("Error");
        }
    }
}
