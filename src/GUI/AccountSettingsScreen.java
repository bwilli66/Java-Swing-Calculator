package GUI;
import Logic.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by BradWilliams on 10/6/16.
 * Account Settings GUI. Users can look up and/or change their password and/or email
 */
public class AccountSettingsScreen extends JFrame {
    private JPanel mainPanel;
    private JPasswordField currentPasswordTextField;
    private JTextField emailTextField;
    private JPasswordField newPasswordTextField;
    private JButton cancelButton;
    private JButton confirmButtonPassword;
    private JButton confirmButtonEmail;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JPasswordField confirmPasswordTextField;
    private String username;

    public AccountSettingsScreen()
    {
        super("Account Settings");

        setContentPane(this.mainPanel);
        pack();

        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setVisible(true);
    }


    private void createUIComponents() {

        // Paint background with gradient
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
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

        // Create Buttons
        confirmButtonEmail = new JButton();
        confirmButtonPassword = new JButton();
        cancelButton = new JButton();

        // Create Labels
        usernameLabel = new JLabel(CalculatorScreen.username);
        emailLabel = new JLabel(JavaDatabaseAPI.getAccountInfo(CalculatorScreen.username)[1]);

        //add actionlisteners
        confirmButtonEmail.addActionListener(new AccountListener(this));
        confirmButtonPassword.addActionListener(new AccountListener(this));
        cancelButton.addActionListener(new AccountListener(this));
    }

    public class AccountListener implements javax.swing.Action{

        AccountSettingsScreen parent;
        public AccountListener(AccountSettingsScreen parent){
            this.parent = parent;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean validEmail = true;
            boolean validPassword = true;
            boolean passwordsMatch = true;
            boolean isCurrentPassword = true;

            String errorMessage = "The following errors were encountered: \n\n\n";

            if(e.getSource() == confirmButtonEmail){

                // check credentials and set flags
                validEmail = Validation.isValidEmailAddress(emailTextField.getText().trim());

                if(!validEmail)
                    errorMessage += "Email is not correct format (example@domain.com) \n\n";
                else {
                    JavaDatabaseAPI.changeEmail(CalculatorScreen.username,emailTextField.getText().trim());

                    parent.setVisible(false);
                    parent.dispose();

                    AccountSettingsScreen asc = new AccountSettingsScreen();
                    JOptionPane.showMessageDialog(parent,
                            "Your email has been updated!",
                            "Confirmation",
                            JOptionPane.OK_OPTION);
                }
            }
            else if(e.getSource() == confirmButtonPassword){

                // check credentials and set flags
                validPassword = Validation.isValidPassword(newPasswordTextField.getText().trim());

                if(JavaDatabaseAPI.getAccountInfo(CalculatorScreen.username)[0].equals(currentPasswordTextField.getText().trim()))
                    isCurrentPassword = true;
                else
                    isCurrentPassword = false;

                if(!newPasswordTextField.getText().equals(confirmPasswordTextField.getText()))
                    passwordsMatch = false;
                else
                    passwordsMatch = true;

                if(!isCurrentPassword)
                    errorMessage += "Current password is wrong \n\n";

                if(!validPassword)
                    errorMessage += "New password does not meet requirements \n\n";

                if(!passwordsMatch)
                    errorMessage += "The passwords entered do not match \n\n";

                if(validPassword && passwordsMatch && isCurrentPassword){
                    JavaDatabaseAPI.changePassword(CalculatorScreen.username,newPasswordTextField.getText().trim());
                    JOptionPane.showMessageDialog(parent,
                            "Your password has been updated!",
                            "Confirmation",
                            JOptionPane.OK_OPTION);

                    // clear fields
                    currentPasswordTextField.setText("");
                    newPasswordTextField.setText("");
                    confirmPasswordTextField.setText("");

                }

            }
            else if(e.getSource() == cancelButton){
                parent.setVisible(false);
                parent.dispose();
            }

            if(!validEmail || !validPassword || !passwordsMatch || !isCurrentPassword) {
                JOptionPane.showMessageDialog(parent,
                        errorMessage,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
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
