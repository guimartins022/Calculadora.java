import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    JFrame frame;
    JTextField textField;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[9];
    JPanel panel;
    double num1 = 0, num2 = 0, result = 0;
    char operator;

    String[] funcTexts = {"+", "-", "*", "/", ".", "=", "Del", "C", "(-)"};
    Color bgColor = new Color(40, 40, 40), btnColor = new Color(30, 144, 255),
            funcColor = new Color(220, 20, 60), textColor = Color.WHITE;

    public Main() {
        frame = new JFrame("Calculadora");
        frame.setSize(420, 550);
        frame.setLayout(null);
        frame.setResizable(false); // Não deixa maximizar
        frame.getContentPane().setBackground(bgColor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setEditable(false);
        textField.setBackground(Color.BLACK);
        textField.setForeground(textColor);
        frame.add(textField);

        criarBotoes();
        panel = new JPanel(new GridLayout(4, 4, 10, 10));
        panel.setBounds(50, 100, 300, 300);
        panel.setBackground(bgColor);
        frame.add(panel);

        adicionarBotoes();
        adicionarExtras();
        frame.setVisible(true);
    }

    private void criarBotoes() {
        for (int i = 0; i < 9; i++) {
            functionButtons[i] = criarBotao(funcTexts[i], funcColor);
        }
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = criarBotao(String.valueOf(i), btnColor);
        }
    }

    private void adicionarBotoes() {
        String layout = "123+456-789*0.=/";
        for (char c : layout.toCharArray()) {
            panel.add(Character.isDigit(c) ? numberButtons[Character.getNumericValue(c)] : functionButtons["+-*/.=DelC(-)".indexOf(c) % 9]);
        }
    }

    private void adicionarExtras() {
        frame.add(functionButtons[6]).setBounds(50, 430, 100, 50);  // Del
        frame.add(functionButtons[7]).setBounds(150, 430, 100, 50); // C
        frame.add(functionButtons[8]).setBounds(250, 430, 100, 50); // (-)
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBackground(cor);
        botao.setForeground(textColor);
        botao.addActionListener(this);
        return botao;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String text = source.getText();
        switch (text) {
            case "+": case "-": case "*": case "/":
                num1 = Double.parseDouble(textField.getText());
                operator = text.charAt(0);
                textField.setText("");
                break;
            case "=":
                num2 = Double.parseDouble(textField.getText());
                result = switch (operator) {
                    case '+' -> num1 + num2;
                    case '-' -> num1 - num2;
                    case '*' -> num1 * num2;
                    case '/' -> num1 / num2;
                    default -> result;
                };
                textField.setText(String.valueOf(result));
                num1 = result;
                break;
            case "Del":
                textField.setText(textField.getText().isEmpty() ? "" : textField.getText().substring(0, textField.getText().length() - 1));
                break;
            case "C":
                textField.setText("");
                break;
            case "(-)":
                textField.setText(String.valueOf(-Double.parseDouble(textField.getText())));
                break;
            default:
                textField.setText(textField.getText().concat(text));
                break;
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
