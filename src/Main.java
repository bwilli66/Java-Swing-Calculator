import GUI.CalculatorScreen;

/**
 * Created by BradWilliams on 9/3/16.
 */
public class Main {

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CalculatorScreen();
            }
        });
    }

}
