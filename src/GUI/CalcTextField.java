package GUI;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

/**
 * Created by BradWilliams on 10/6/16.
 * Defines a custom JTextfield
 */
public class CalcTextField extends JTextField
{
    private Color color1 = null;
    private Color color2 = null;

    public CalcTextField()
    {
        super.setColumns(21);// set width
        //super.setOpaque(false); // attempt to make text field transparent
        super.setBackground(new Color(0,0,0,0));// Used to make textfield transparent
        super.setFont(new Font("Tahoma", Font.BOLD, 30));// set font. font dictates height
        super.setHorizontalAlignment(JTextField.RIGHT); // align text to right

        super.setBorder(null);// get ride of border

        super.setBorder(BorderFactory.createCompoundBorder(
                super.getBorder(),
                BorderFactory.createEmptyBorder(10, 5, 10, 5))); //Padding inside JTextField


        // set gradient colors
        this.color1 = new Color(190, 190, 190);
        this.color2 = new Color(215, 215, 215);

        this.addFocusListener(new CalcTextFieldFocusListener());
        this.getInputMap().setParent(null);


    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int width = getWidth();
        int height = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);

        // repaint over gradient
        super.paintComponent(g);
    }

    // ------------  This FocusListener keeps the text-field caret at the end of the text-field
    public class CalcTextFieldFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if(e.getSource() instanceof CalcTextField){
                CalcTextField calcTextField = (CalcTextField)e.getSource();
                calcTextField.setCaretPosition(calcTextField.getText().length());
            }
        }

        @Override
        public void focusLost(FocusEvent e) {

        }
    }

}
