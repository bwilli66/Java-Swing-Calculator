package GUI;
import Logic.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by BradWilliams on 10/4/16.
 *
 * Main Calculator GUI. Logging out will take user back to LoginScreen
 */
public class CalculatorScreen extends JFrame
{
    private JPanel right;
    private JPanel keypad;
    private JPanel top;
    private JLabel loggedInAsLabel;
    private JButton logoutButton;
    private JButton accountButton;
    private JLabel usernameLabel;
    private JPanel mainPanel;
    private JPanel keys;
    private JPanel operations;
    private CalcTextField calcTextField;


    // keypad buttons
    private CalcButton zeroButton;
    private CalcButton oneButton;
    private CalcButton twoButton;
    private CalcButton threeButton;
    private CalcButton fourButton;
    private CalcButton fiveButton;
    private CalcButton sixButton;
    private CalcButton sevenButton;
    private CalcButton eightButton;
    private CalcButton nineButton;

    private CalcButton clearButton;
    private CalcButton equalsButton;

    // Operation buttons
    private CalcButton plusButton;
    private CalcButton minusButton;
    private CalcButton multButton;
    private CalcButton divButton;
    private CalcButton modButton;

    public static boolean loggedIn;
    public static String username;


    public CalculatorScreen()
    {
        super("Integer Calculator");

        loggedIn = false;
        setContentPane(this.mainPanel);
        pack();

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);

        LoginScreen firstLogin = new LoginScreen();
    }


    // ---------- CREATE ALL CUSTOM COMPONENTS
    private void createUIComponents() {
        // TODO: place custom component creation code here

        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g){
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(253, 253, 253);
                Color color2 = new Color(205, 205, 205);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

        keypad = new JPanel();// JPanel to contain key and operation JPanels

        // -------- create JPanel for keys
        keys = new JPanel(new GridLayout(4,3,8,8)); //GridLayout(rows,cols,hgap,vgap)
        keys.setBorder(new EmptyBorder(12,12,18,0)); //EmptyBorder(top,left,bottom,right)

        // --------- Create key buttons
        zeroButton = new CalcButton("number","0");
        oneButton = new CalcButton("number","1");
        twoButton = new CalcButton("number","2");
        threeButton = new CalcButton("number","3");
        fourButton = new CalcButton("number","4");
        fiveButton = new CalcButton("number","5");
        sixButton = new CalcButton("number","6");
        sevenButton = new CalcButton("number","7");
        eightButton = new CalcButton("number","8");
        nineButton = new CalcButton("number","9");

        clearButton = new CalcButton("clear","C");
        equalsButton = new CalcButton("equals","=",36);

        // --------- Add key buttons
        keys.add(zeroButton);
        keys.add(oneButton);
        keys.add(twoButton);
        keys.add(threeButton);
        keys.add(fourButton);
        keys.add(fiveButton);
        keys.add(sixButton);
        keys.add(sevenButton);
        keys.add(eightButton);
        keys.add(nineButton);

        keys.add(clearButton);
        keys.add(equalsButton);

        // -------- create JPanel for operations
        operations = new JPanel(new GridLayout(5,1,0,8));//GridLayout(rows,cols,hgap,vgap)
        operations.setBorder(new EmptyBorder(11,8,18,8));//EmptyBorder(top,left,bottom,right)

        // --------- Create operation buttons
        plusButton = new CalcButton("operation","+");
        minusButton = new CalcButton("operation","-",34);
        multButton = new CalcButton("operation","X");
        divButton = new CalcButton("operation","/");
        modButton = new CalcButton("operation","%",17);

        // --------- Add operation buttons
        operations.add(plusButton);
        operations.add(minusButton);
        operations.add(multButton);
        operations.add(divButton);
        operations.add(modButton);

        // Couldn't get padding to work right in GUI designer :/
        right = new JPanel();
        right.setBorder(new EmptyBorder(20,0,20,0));

        // Set Username label
        usernameLabel = new JLabel(this.username);


        // ---------- Create top panel to hold JTextfield
        top = new JPanel();
        top.setLayout(new GridBagLayout());

        // Center variable for JTextfield
        GridBagConstraints center = new GridBagConstraints();
        center.anchor = GridBagConstraints.CENTER;
        center.fill = GridBagConstraints.NONE;

        // Add JTextfield to panel
        calcTextField = new CalcTextField();
        top.add(calcTextField, center);

        // focus on text-field on form creation
        calcTextField.setFocusable(true);
        calcTextField.requestFocus();


        // Create Buttons
        logoutButton = new JButton();
        accountButton = new JButton();

        // ------------------  Event Listeners ----------------------
        logoutButton.addActionListener(new ButtonActionListener());
        accountButton.addActionListener(new ButtonActionListener());

        // keypad buttons
        zeroButton.addMouseListener(new KeypadListener());
        oneButton.addMouseListener(new KeypadListener());
        twoButton.addMouseListener(new KeypadListener());
        threeButton.addMouseListener(new KeypadListener());
        fourButton.addMouseListener(new KeypadListener());
        fiveButton.addMouseListener(new KeypadListener());
        sixButton.addMouseListener(new KeypadListener());
        sevenButton.addMouseListener(new KeypadListener());
        eightButton.addMouseListener(new KeypadListener());
        nineButton.addMouseListener(new KeypadListener());

        clearButton.addMouseListener(new KeypadListener());
        equalsButton.addMouseListener(new KeypadListener());

        // operation buttons
        plusButton.addMouseListener(new KeypadListener());
        minusButton.addMouseListener(new KeypadListener());
        multButton.addMouseListener(new KeypadListener());
        divButton.addMouseListener(new KeypadListener());
        modButton.addMouseListener(new KeypadListener());

        // KeyListener to format text-field input from keyboard
        calcTextField.addKeyListener(new KeyFormatListener());
    }

//    public JPanel getMainPanel () {
//        return this.mainPanel;
//    }

    public void setTextField(String s){
        this.calcTextField.setText(s);
    }
    public void updateLabel(String s){
        this.usernameLabel.setText(s);
    }

    public class ButtonActionListener implements javax.swing.Action {

        @Override
        public void actionPerformed(ActionEvent e) {
            // disable buttons until logged in
            if(!loggedIn)
                return;

            if(e.getSource() == accountButton) {

                AccountSettingsScreen accountSettingsScreen = new AccountSettingsScreen();
//                CalculatorScreen.getContentPane().remove(CalculatorScreen.getMainPanel());
//                myFrame.getContentPane().add(newPanel);
//                myFrame.validate();
            }

            else if(e.getSource() == logoutButton) {
                LoginScreen loginScreen = new LoginScreen();
                loggedIn = false; //disables key and mouse listeners
            }
        }
        @Override
        public Object getValue(String key) {
            return null;
        }
        @Override
        public void putValue(String key, Object value) {}
        @Override
        public void setEnabled(boolean b) {}
        @Override
        public boolean isEnabled() {
            return false;
        }
        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {}
        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {}
    }

    public class KeypadListener implements MouseListener {

        char [] allowedNums = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        char [] allowedOps = new char[]{'+','-','*','/','%'};

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            if(!loggedIn)
                return;

            System.out.println("Mouse pressed");
            if(e.getSource() instanceof CalcButton){
                CalcButton button = (CalcButton)e.getSource();
                button.flipGradient();
                button.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(!loggedIn)
                return;

            System.out.println("Mouse Released");
            //If field has a an error message,
            Pattern pattern = Pattern.compile(".*[A-Za-z]");//anything containing lower or uppercase letters

            Matcher matcher = pattern.matcher(calcTextField.getText());

            //clear it first
            if(matcher.find())
                calcTextField.setText("");

            if(e.getSource() instanceof CalcButton){
                CalcButton button = (CalcButton)e.getSource();
                button.flipGradient();
                button.repaint();
                switch (button.getType()){
                    //if it was a number
                    case "number" :
                        switch (button.getCharacter()){
                            case "0" :
                                System.out.println("0 released");
                                calcTextField.setText(calcTextField.getText() + "0");
                                break;
                            case "1" :
                                System.out.println("1 released");
                                calcTextField.setText(calcTextField.getText() + "1");
                                break;
                            case "2" :
                                System.out.println("2 released");
                                calcTextField.setText(calcTextField.getText() + "2");
                                break;
                            case "3" :
                                System.out.println("3 released");
                                calcTextField.setText(calcTextField.getText() + "3");
                            case "4" :
                                System.out.println("4 released");
                                calcTextField.setText(calcTextField.getText() + "4");
                                break;
                            case "5" :
                                System.out.println("5 released");
                                calcTextField.setText(calcTextField.getText() + "5");
                                break;
                            case "6" :
                                System.out.println("6 released");
                                calcTextField.setText(calcTextField.getText() + "6");
                                break;
                            case "7" :
                                System.out.println("7 released");
                                calcTextField.setText(calcTextField.getText() + "7");
                                break;
                            case "8" :
                                System.out.println("8 released");
                                calcTextField.setText(calcTextField.getText() + "8");
                                break;
                            case "9" :
                                System.out.println("9 released");
                                calcTextField.setText(calcTextField.getText() + "9");
                                break;
                        }
                        break;
                    //if it was equals
                    case "equals" :
                        System.out.println("equals released");

                        if(calcTextField.getText().equals("")) { //if text-field is empty
                            return;
                        }

                        // --------------  OLD INVALID EXPRESSION HANDLING
//                        boolean validExpression = true;
//                        String[] tokens = calcTextField.getText().split("\\s"); //get text from text field and store as separate strings
//
//
//
//                        /*
//                        * Two operations back to back will result in an extra " " after split() is called
//                        * " " returns 0 when length() is called
//                         */
//                        for(int i = 0; i < tokens.length; ++i){
//                            // if " " is found, it is an invalid expression (validExpression = false)
//                            if(tokens[i].length() == 0){
//                                System.out.println("Condition Met");
//                                validExpression = false;
//                                break;
//                            }
//
//                        }
//
//                        // if last character is an operation
//                        for(char c : allowedOps){
//                            if(c == tokens[tokens.length-1].charAt(0)) {
//                                validExpression = false;
//                                break;
//                            }
//                        }
//
//                        if(validExpression)
//                            calcTextField.setText(Logic.ComputeEngine.compute(calcTextField.getText()));
//                        else
//                            calcTextField.setText("Invalid Expression");

                        calcTextField.setText(ComputeEngine.compute(calcTextField.getText()));
                        break;
                    //if it was clear
                    case "clear" :
                        System.out.println("clear released");
                        calcTextField.setText("");
                        break;
                    //if it was an operation
                    case "operation" :
                        switch (button.getCharacter()) {
                            case "+":
                                System.out.println("+ released");
                                calcTextField.setText(calcTextField.getText() + " + ");
                                break;
                            case "-":
                                System.out.println("- released");
                                calcTextField.setText(calcTextField.getText() + " - ");
                                break;
                            case "X":
                                System.out.println("* released");
                                calcTextField.setText(calcTextField.getText() + " * ");
                                break;
                            case "/":
                                System.out.println("/ released");
                                calcTextField.setText(calcTextField.getText() + " / ");
                                break;
                            case "%":
                                System.out.println("% released");
                                calcTextField.setText(calcTextField.getText() + " % ");
                                break;
                        }
                        break;
                }
            }
            // set focus back to text-field after every button event
            calcTextField.requestFocus();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }


    public class KeyFormatListener implements KeyListener {

        char [] allowedNums = new char[]{'0','1','2','3','4','5','6','7','8','9'};
        char [] allowedOps = new char[]{'+','-','*','/','%'};

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if(!loggedIn)
                return;
            // -------------- GRADIENT FLIPPING ------------------
                switch (e.getKeyCode()){
                    // 0 through 9
                    case 48: // 0
                        zeroButton.flipGradient();
                        zeroButton.repaint();
                        break;
                    case 49: // 1
                        oneButton.flipGradient();
                        oneButton.repaint();
                        break;
                    case 50: // 2
                        twoButton.flipGradient();
                        twoButton.repaint();
                        break;
                    case 51: // 3
                        threeButton.flipGradient();
                        threeButton.repaint();
                        break;
                    case 52: // 4
                        fourButton.flipGradient();
                        fourButton.repaint();
                        break;
                    case 53: // 5
                        fiveButton.flipGradient();
                        fiveButton.repaint();
                        break;
                    case 54: // 6
                        sixButton.flipGradient();
                        sixButton.repaint();
                        break;
                    case 55: // 7
                        sevenButton.flipGradient();
                        sevenButton.repaint();
                        break;
                    case 56: // 8
                        eightButton.flipGradient();
                        eightButton.repaint();
                        break;
                    case 57: // 9
                        nineButton.flipGradient();
                        nineButton.repaint();
                        break;

                    // clear and return ('c' and carriage return)
                    case 67: //clear
                        clearButton.flipGradient();
                        clearButton.repaint();
                        break;
                    case 10: //return
                        equalsButton.flipGradient();
                        equalsButton.repaint();
                        break;
                }
                switch (e.getKeyChar()) {
                    // operations (+,-,*,/,%)
                    case '+':
                        plusButton.flipGradient();
                        plusButton.repaint();
                        break;
                    case '-':
                        minusButton.flipGradient();
                        minusButton.repaint();
                        break;
                    case '*':
                        multButton.flipGradient();
                        multButton.repaint();
                        break;
                    case '/':
                        divButton.flipGradient();
                        divButton.repaint();
                        break;
                    case '%':
                        modButton.flipGradient();
                        modButton.repaint();
                        break;

                }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(!loggedIn)
                return;

            System.out.println(e.getKeyCode()); //debug

            //If field has a an error message,
            Pattern pattern = Pattern.compile(".*[A-Za-z]");//anything containing lower or uppercase letters

            Matcher matcher = pattern.matcher(calcTextField.getText());

            //clear it first
            if(matcher.find())
                calcTextField.setText("");

            // -------------- GRADIENT RESETTING ------------------
            switch (e.getKeyCode()){
                // 0 through 9
                case 48: // 0
                    zeroButton.flipGradient();
                    zeroButton.repaint();
                    break;
                case 49: // 1
                    oneButton.flipGradient();
                    oneButton.repaint();
                    break;
                case 50: // 2
                    twoButton.flipGradient();
                    twoButton.repaint();
                    break;
                case 51: // 3
                    threeButton.flipGradient();
                    threeButton.repaint();
                    break;
                case 52: // 4
                    fourButton.flipGradient();
                    fourButton.repaint();
                    break;
                case 53: // 5
                    fiveButton.flipGradient();
                    fiveButton.repaint();
                    break;
                case 54: // 6
                    sixButton.flipGradient();
                    sixButton.repaint();
                    break;
                case 55: // 7
                    sevenButton.flipGradient();
                    sevenButton.repaint();
                    break;
                case 56: // 8
                    eightButton.flipGradient();
                    eightButton.repaint();
                    break;
                case 57: // 9
                    nineButton.flipGradient();
                    nineButton.repaint();
                    break;

                // clear and return ('c' and carriage return)
                case 67: //clear
                    clearButton.flipGradient();
                    clearButton.repaint();
                    break;
                case 10: //return
                    equalsButton.flipGradient();
                    equalsButton.repaint();
                    break;
            }
            switch (e.getKeyChar()) {
                // operations (+,-,*,/,%)
                case '+':
                    plusButton.flipGradient();
                    plusButton.repaint();
                    break;
                case '-':
                    minusButton.flipGradient();
                    minusButton.repaint();
                    break;
                case '*':
                    multButton.flipGradient();
                    multButton.repaint();
                    break;
                case '/':
                    divButton.flipGradient();
                    divButton.repaint();
                    break;
                case '%':
                    modButton.flipGradient();
                    modButton.repaint();
                    break;

            }


            // -------------- KEY FORMATTING ------------------

            char key = e.getKeyChar();
            boolean isNum = false;
            boolean isOp = false;

            // check key typed to see if it a allowed number
            for(char allowed : allowedNums){
                if(key == allowed)
                    isNum = true;
            }

            // check key typed to see if it a allowed operation
            for(char allowed : allowedOps){
                if(key == allowed)
                    isOp = true;
            }


            // if it is a number
            if(isNum && !isOp){
                calcTextField.setText(calcTextField.getText() + key);
            }
            // if it is an operation
            else if (isOp && !isNum){
                calcTextField.setText(calcTextField.getText() + " " + key + " ");
            }
            // if it is C, clear the field
            else if(e.getKeyCode() == 67){
                calcTextField.setText("");
            }
            // if it is a return
            else if(e.getKeyCode() == 10 || e.getKeyCode() == 61){

                if(calcTextField.getText().equals("")) { //if text-field is empty
                    return;
                }

                // -------------     invalid expression handling

//                boolean validExpression = true;
//                String[] tokens = calcTextField.getText().split("\\s"); //get text from text field and store as separate strings
//
//                /*
//                * Two operations back to back will result in an extra " " after split() is called
//                * " " returns 0 when length() is called
//                 */
//                for(int i = 0; i < tokens.length; ++i){
//                    // if " " is found, it is an invalid expression (validExpression = false)
//                    if(tokens[i].length() <= 0) {
//                        System.out.println("Condition Met");
//                        validExpression = false;
//                        break;
//                    }
//                }
//
//                // if last character is an operation
//                for(char c : allowedOps){
//                    if(c == tokens[tokens.length-1].charAt(0)) {
//                        validExpression = false;
//                        break;
//                    }
//                }
//
//                if(validExpression)
//                    calcTextField.setText(Logic.ComputeEngine.compute(calcTextField.getText()));
//                else
//                    calcTextField.setText("Invalid Expression");

                calcTextField.setText(ComputeEngine.compute(calcTextField.getText()));
            }
            // if it is delete
            else if(e.getKeyCode() == 8){
                if(calcTextField.getText().equals("")) {
                    calcTextField.setText(""); // if text-field is empty, do nothing
                }
                // if the last character in the textfield is a digit, delete 1 character
                else if(Character.isDigit(calcTextField.getText().charAt(calcTextField.getText().length()-1))) {

                    calcTextField.setText(calcTextField.getText().substring(0, calcTextField.getText().length() - 1));
                }// if the last character in the textfield is not a digit, delete 3 characters
                else {

                    calcTextField.setText(calcTextField.getText().substring(0, calcTextField.getText().length() - 3));
                }
            }

        }
    }
}
