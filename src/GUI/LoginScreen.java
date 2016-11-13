package GUI;
import Logic.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;

/**
 * Created by BradWilliams on 10/7/16.
 * Login GUI
 *
 *
 */
public class LoginScreen extends JFrame{
    private JPanel mainPanel;
    private JTextField usernNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerNewUserButton;
    public static byte count; // used for login lockout (if count > 3 lockout user, go to password recovery)

    public LoginScreen()
    {
        super("Login");

        setFocusable(true);

        setContentPane(this.mainPanel);
        pack();

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        loginButton = new JButton();
        registerNewUserButton = new JButton();


        // ---------- Add Action Listeners

        loginButton.addActionListener(new LoginActionListener(this));
        registerNewUserButton.addActionListener(new LoginActionListener(this));

    }

    public class LoginActionListener implements javax.swing.Action{

        LoginScreen parent;


        public LoginActionListener(LoginScreen parent){
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton){
                // if lockout limit is exceeded
                if(count >= 2){
                    CalculatorScreen.loggedIn = false;

                    PasswordRecoveryScreen prs = new PasswordRecoveryScreen();
                    PasswordRecoveryScreen.username = usernNameField.getText().trim();

                    //System.out.println(usernNameField.getText().trim());
                    System.out.println(PasswordRecoveryScreen.username);
                    //System.out.println("WTF");

                    //System.out.println(Logic.JavaDatabaseAPI.getAccountInfo(usernNameField.getText().trim())[1]);

                    parent.setVisible(false); //hide login window
                    parent.dispose(); //dispose login window
                }
                else {
                    // check database to see if login credentials are valid
                    if (JavaDatabaseAPI.loginAuth(usernNameField.getText().trim(), passwordField.getText().trim())) {
                        CalculatorScreen.loggedIn = true;
                        CalculatorScreen.username = usernNameField.getText().trim();
                        //CalculatorScreen.updatLabel(usernNameField.getText());
                        // when CalcScreen gains focus, repaint()
                        parent.setVisible(false); //hide login window
                        parent.dispose(); //dispose login window


                    }
                    // else, increment lockout variable
                    else {
                        parent.count++;
                        JOptionPane.showMessageDialog(parent,
                                "Username or password invalid",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else if (e.getSource() == registerNewUserButton){
                CalculatorScreen.loggedIn = false;
                parent.setVisible(false); //hide login window
                parent.dispose(); //dispose login window

                RegisterScreen rs = new RegisterScreen();
            }

        }

        @Override
        public Object getValue(String key) {return null;}

        @Override
        public void putValue(String key, Object value) {}

        @Override
        public void setEnabled(boolean b) {}

        @Override
        public boolean isEnabled() {return false;}

        @Override
        public void addPropertyChangeListener(PropertyChangeListener listener) {}

        @Override
        public void removePropertyChangeListener(PropertyChangeListener listener) {}

    }


    // user enter to fire loginButton Event
    public class LoginKeyListener implements KeyListener {

        LoginScreen parent;


        public LoginKeyListener(LoginScreen parent){
            this.parent = parent;
        }


        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println(e.getKeyCode()); //debug
            if(e.getKeyChar() == 10){
                parent.loginButton.doClick();
            }
        }
    }

}
