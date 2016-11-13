package GUI;
import Logic.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by BradWilliams on 10/8/16.
 * Password Recovery GUI. If a user has forgotten their password, they can choose to have a new password sent to them via email
 */
public class PasswordRecoveryScreen extends JFrame {

    public static String username; // this takes the username from the login window
    private JPanel mainPanel;
    private JButton goButton;
    private JTextField emailTextField;
    private JPanel top;
    private JLabel forgotPasswordLabel;
    private JLabel paragraphLabel;
    private String recoveryInfo =
            "Enter the email address associated with your username to obtain a new password. " +
            "You will recieve an email with the new password. " +
            "Once logged back in you can change your password by clicking the “Account” button.";


    public PasswordRecoveryScreen()
    {
        super("Register New User");

        setContentPane(this.mainPanel);

        pack();

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        top = new JPanel();

        forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setFont(new Font("San-Serif", Font.PLAIN, 20));

        paragraphLabel = new JLabel();
        paragraphLabel.setPreferredSize(new Dimension(460,200));

        paragraphLabel.setText("<html>" + recoveryInfo + "</html>");

        paragraphLabel.setVerticalAlignment(JLabel.TOP);
        paragraphLabel.setBorder(new EmptyBorder(0,10,0,10));

        top.add(forgotPasswordLabel);
        top.add(paragraphLabel);

        goButton = new JButton();

        emailTextField = new JTextField();

        goButton.addActionListener(new RecoveryActionListener(this));

    }

    public class RecoveryActionListener implements javax.swing.Action {

        private PasswordRecoveryScreen parent;

        public RecoveryActionListener(PasswordRecoveryScreen parent){
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            boolean validUsername;
            boolean validEmail;
            boolean emailExists = true;

            if(e.getSource() == goButton){
                String errorMessage = "The following errors were encountered: \n\n\n";

                // check credentials and set flags
                validEmail = Validation.isValidEmailAddress(emailTextField.getText().trim());
                validUsername = Validation.isValidUsername(parent.username);


                if(!validEmail)
                    errorMessage += "Email is not correct format (example@domain.com) \n\n";

                if(!validUsername)
                    errorMessage += "The username you were trying to log in with doesn't exist \n\n";
                else
                    emailExists = Validation.doesEmailExist(parent.username, emailTextField.getText().trim()); //don't check email if username isn't in database

                if(!emailExists)
                    errorMessage += "Email does not correspond to the username you tried to log in with \n\n";

                if(!validEmail || !validUsername || !emailExists) {
                    JOptionPane.showMessageDialog(parent,
                            errorMessage,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String password = GmailAPI.sendMessage(emailTextField.getText());

                    JavaDatabaseAPI.changePassword(parent.username,password);

                    LoginScreen.count = 0;
                    parent.setVisible(false);
                    parent.dispose();

                    LoginScreen ls = new LoginScreen();

                    JOptionPane.showMessageDialog(parent,
                            "Your new password has been sent!",
                            "Password Recovery",
                            JOptionPane.OK_OPTION);
                }
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

}
