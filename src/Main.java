import GUI.CalculatorScreen;
import GUI.LoginScreen;

/**
 * Created by BradWilliams on 9/3/16.
 */
public class Main {

    public static void main(String args[]) {

        /* Create and display the JFrame */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalculatorScreen cs = new CalculatorScreen();
                LoginScreen.calcScreen = cs;
            }
        });
    }

}
