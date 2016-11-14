package GUI;
import Logic.*;

import javax.swing.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;

/**
 * Created by BradWilliams on 10/19/16.
 */
public class RegisterScreen extends JFrame {
    private JPanel mainPanel;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton cancelButton;
    private JButton registerButton;

    public RegisterScreen()
    {
        super("Register New User");

        setContentPane(this.mainPanel);
        pack();

        setResizable(false);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);

        this.addWindowListener(new RegisterWindowListener(this)); // used to go back to Login Screen when the window closes


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        registerButton = new JButton();
        cancelButton = new JButton();

        registerButton.addActionListener(new RegisterListener(this));
        cancelButton.addActionListener(new RegisterListener(this));
    }

    public class RegisterListener implements javax.swing.Action{

        RegisterScreen parent;
        public RegisterListener(RegisterScreen parent){
            this.parent = parent;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean validUsername;
            boolean validEmail;
            boolean validPassword;
            boolean passwordsMatch;

            if(e.getSource() == registerButton){

                String errorMessage = "The following errors were encountered: \n\n\n";

                // check credentials and set flags
                validPassword = Validation.isValidPassword(passwordField.getText().trim());
                validEmail = Validation.isValidEmailAddress(emailField.getText().trim());
                validUsername = Validation.isValidUsername(usernameField.getText().trim());

                if(!passwordField.getText().equals(confirmPasswordField.getText()))
                    passwordsMatch = false;
                else
                    passwordsMatch = true;

                if(!validPassword)
                    errorMessage += "Password does not meet requirements \n\n";

                if(!passwordsMatch)
                    errorMessage += "The passwords entered do not match \n\n";

                if(!validEmail)
                    errorMessage += "Email is not correct format (example@domain.com) \n\n";

                if(validUsername)
                    errorMessage += "Username is taken \n\n";

                if(!validEmail || !validPassword || validUsername || !passwordsMatch) {
                    JOptionPane.showMessageDialog(parent,
                            errorMessage,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JavaDatabaseAPI.addUser(usernameField.getText().trim(),emailField.getText().trim(),passwordField.getText().trim());

                    parent.setVisible(false);
                    parent.dispose();

                    LoginScreen ls = new LoginScreen();
                    JOptionPane.showMessageDialog(parent,
                            "Congrats! New user created!",
                            "User Created",
                            JOptionPane.OK_OPTION);
                }
            }
            else if(e.getSource() == cancelButton){

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

    public class RegisterWindowListener implements java.awt.event.WindowListener{
        JFrame parent;

        public RegisterWindowListener(JFrame parent){
            this.parent = parent;
        }

        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {
            parent.setVisible(false);
            parent.dispose();
        }

        @Override
        public void windowClosed(WindowEvent e) {
            LoginScreen ls = new LoginScreen();
        }

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
    }



}
