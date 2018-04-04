import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.math.BigDecimal;

public class Calculator {

    // singleton instance
    private static class CalculatorInstance {
        private static final Calculator calculator = new Calculator();

        public static Calculator getCalculator() {
            return calculator;
        }
    }

    private String str1 = "0";      // num1
    private String str2 = "0";      // num2
    private String signal = "+";    // operation
    private String result = "";     // result

    private int k1 = 1; // input is str1 or str2
    private int k2 = 1; // whether there are multiple calculations
    private int k3 = 1; // whether str1 can be cleared
    private int k4 = 1; // whether str2 can be cleared
    private int k5 = 1; // whether there are decimals
    private JButton store;  // whether there are continuous pushes of buttons

    @SuppressWarnings("rawtypes")
    private Vector vt;

    // UI components
    private JFrame frame;
    private JTextField resultTextField;
    private JButton clearButton;
    private JButton button0;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button8;
    private JButton button9;
    private JButton buttonPoint;
    private JButton buttonAdd;
    private JButton buttonSub;
    private JButton buttonMul;
    private JButton buttonDiv;
    private JButton buttonEqu;

    private void createPanel() {

        // keyboard keys
        button0.setMnemonic(KeyEvent.VK_0);
        button1.setMnemonic(KeyEvent.VK_1);
        button2.setMnemonic(KeyEvent.VK_2);
        button3.setMnemonic(KeyEvent.VK_3);
        button4.setMnemonic(KeyEvent.VK_4);
        button5.setMnemonic(KeyEvent.VK_5);
        button6.setMnemonic(KeyEvent.VK_6);
        button7.setMnemonic(KeyEvent.VK_7);
        button8.setMnemonic(KeyEvent.VK_8);
        button9.setMnemonic(KeyEvent.VK_9);
        button0.setPreferredSize(new Dimension(40, 60));

        // text filed
        JPanel text = new JPanel();
        resultTextField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        text.add(resultTextField, BorderLayout.WEST);
        text.add(clearButton, BorderLayout.EAST);

        JPanel main = new JPanel();
        main.setLayout(new GridLayout(4, 4, 4, 4));
        main.add(button7);
        main.add(button8);
        main.add(button9);
        main.add(buttonDiv);
        main.add(button4);
        main.add(button5);
        main.add(button6);
        main.add(buttonMul);
        main.add(button1);
        main.add(button2);
        main.add(button3);
        main.add(buttonSub);
        main.add(button0);
        main.add(buttonPoint);
        main.add(buttonEqu);
        main.add(buttonAdd);
        main.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        // general settings
        frame.setLocation(300, 200);
        frame.setResizable(false);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(text, BorderLayout.NORTH);
        frame.getContentPane().add(main, BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(58, 67, 84));

        frame.pack();
        frame.setVisible(true);

        // number listeners
        button0.addActionListener(numberListener);
        button1.addActionListener(numberListener);
        button2.addActionListener(numberListener);
        button3.addActionListener(numberListener);
        button4.addActionListener(numberListener);
        button5.addActionListener(numberListener);
        button6.addActionListener(numberListener);
        button7.addActionListener(numberListener);
        button8.addActionListener(numberListener);
        button9.addActionListener(numberListener);

        // clear button listener
        clearButton.addActionListener(clearListener);

        // point button listener
        buttonPoint.addActionListener(pointListener);

        // operation listener
        buttonAdd.addActionListener(opeListener);
        buttonSub.addActionListener(opeListener);
        buttonDiv.addActionListener(opeListener);
        buttonMul.addActionListener(opeListener);
        buttonEqu.addActionListener(equalListener);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    private Calculator() {

        vt              = new Vector(20, 10);
        frame           = new JFrame("Calculator");
        resultTextField = new JTextField(17);
        clearButton     = new JButton("C");
        button0         = new JButton("0");
        button1         = new JButton("1");
        button2         = new JButton("2");
        button3         = new JButton("3");
        button4         = new JButton("4");
        button5         = new JButton("5");
        button6         = new JButton("6");
        button7         = new JButton("7");
        button8         = new JButton("8");
        button9         = new JButton("9");
        buttonPoint     = new JButton(".");
        buttonAdd       = new JButton("+");
        buttonSub       = new JButton("-");
        buttonMul       = new JButton("*");
        buttonDiv       = new JButton("/");
        buttonEqu       = new JButton("=");

        createPanel();

    }

    public static Calculator createCalculator() {
        return CalculatorInstance.getCalculator();
    }

    @SuppressWarnings("unchecked")
    ActionListener numberListener = e -> {
        String num = ((JButton) e.getSource()).getText();
        store = (JButton) e.getSource();
        vt.add(store);
        if (k1 == 1) {
            if (k3 == 1) {
                str1 = "";
                // reset k5
                k5 = 1;
            }
            str1 = str1 + num;
            k3 = k3 + 1;
            // show result
            resultTextField.setText(str1);
        } else if (k1 == 2) {
            if (k4 == 1) {
                str2 = "";
                // reset k5
                k5 = 1;
            }
            str2 = str2 + num;
            k4 = k4 + 1;
            resultTextField.setText(str2);
        }
    };

    @SuppressWarnings("unchecked")
    ActionListener opeListener = e -> {
        String num2 = ((JButton) e.getSource()).getText();
        store = (JButton) e.getSource();
        vt.add(store);

        if (k2 == 1) {
            k1 = 2;
            k5 = 1;
            signal = num2;
            k2++;
        } else {
            int a = vt.size();
            JButton c = (JButton) vt.get(a - 2);

            if (!c.getText().equals("+") &&
                    !c.getText().equals("-") &&
                    !c.getText().equals("*") &&
                    !c.getText().equals("/")) {
                cal();
                str1 = result;

                k1 = 2;
                k5 = 1;
                k4 = 1;
                signal = num2;
            }

            k2++;
        }
    };

    @SuppressWarnings("unchecked")
    ActionListener clearListener = e -> {
        store = (JButton) e.getSource();
        vt.add(store);

        k1 = 1;
        k2 = 1;
        k3 = 1;
        k4 = 1;
        k5 = 1;

        str1 = "0";
        str2 = "0";
        signal = "";
        result = "";
        resultTextField.setText("");

        vt.clear();
    };

    @SuppressWarnings("unchecked")
    ActionListener equalListener = e -> {
        store = (JButton) e.getSource();
        vt.add(store);
        cal();

        k1 = 1;
        k2 = 1;
        k3 = 1;
        k4 = 1;

        str1 = result;
    };

    @SuppressWarnings("unchecked")
    ActionListener pointListener = e -> {
        store = (JButton) e.getSource();
        vt.add(store);
        if (k5 == 1) {
            String decimal = ((JButton) e.getSource()).getText();
            if (k1 == 1) {
                if (k3 == 1) {
                    str1 = "";
                    k5 = 1;
                }
                str1 = str1 + decimal;
                k3++;

                resultTextField.setText(str1);
            } else {
                if (k4 == 1) {
                    str2 = "";
                    k5 = 1;
                }
                str2 = str2 + decimal;
                k4++;
                resultTextField.setText(str2);
            }
        }

        k5++;
    };

    private void cal() {
        double num1, num2, res = 0;
        String c = signal;

        if (c.equals("")) {
            resultTextField.setText("No operator detected");
        } else {
            if (str1.equals(".")) str1 = "0.0";
            if (str2.equals(".")) str2 = "0.0";
            num1 = Double.valueOf(str1).doubleValue();
            num2 = Double.valueOf(str2).doubleValue();

            if (c.equals("+")) res = num1 + num2;
            if (c.equals("-")) res = num1 - num2;
            if (c.equals("*")) {
                BigDecimal bd1 = new BigDecimal(Double.toString(num1));
                BigDecimal bd2 = new BigDecimal(Double.toString(num2));
                res = bd1.multiply(bd2).doubleValue();
            }
            if (c.equals("/")) {
                if (num2 == 0) res = 0;
                else    res = num1 / num2;
            }

            result = (new Double(res)).toString();
            resultTextField.setText(result);
        }

    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        try {
            // UI style
            String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lookAndFeel);

        } catch (Exception e) {

            e.printStackTrace();

        }
        Calculator c = Calculator.createCalculator();

    }
}
