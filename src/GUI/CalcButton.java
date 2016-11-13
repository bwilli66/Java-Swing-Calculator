package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by BradWilliams on 10/5/16.
 * Changes color of button upon constructions
 */
public class CalcButton extends JButton
{
    private String type;
    private String character;
    private Color color1 = null;
    private Color color2 = null;


    public CalcButton(String type, String text)
    {
        this.type = type;
        this.character = text;

        //Set up superclass for super.paintComponent() method call in subclass paintComponent()
        super.setText(text);
        super.setOpaque(false);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        super.setForeground(Color.WHITE);
        super.setFocusPainted(false);
        super.setFont(new Font("Tahoma", Font.BOLD, 24));

        switch (type) {
            //make it blue
            case "number" :
                this.color1 = new Color(96, 195, 220); //lighter blue
                this.color2 = new Color(78, 158, 200); //darker blue
                break;
            //make it red
            case "equals" :
                this.color1 = new Color(241, 141, 141); //lighter red
                this.color2 = new Color(229, 43, 49); //darker red
                break;
            //make it black
            case "clear" :
                this.color1 = new Color(93, 93, 94); //lighter red
                this.color2 = new Color(31, 31, 31); //darker red
                break;
            //make it yellow-orange
            case "operation" :
                this.color1 = new Color(253, 194, 45); //lighter red
                this.color2 = new Color(252, 157, 39); //darker red
                break;
        }
    }

    // ------------- Custom Font Size Constructor
    public CalcButton(String type, String text, int font)
    {
        this.type = type;
        this.character = text;
        int fontSize = font;
        //Set up superclass for super.paintComponent() method call in subclass paintComponent()
        super.setText(text);
        super.setOpaque(false);
        super.setContentAreaFilled(false);
        super.setBorderPainted(false);
        super.setForeground(Color.WHITE);
        super.setFocusPainted(false);
        super.setFont(new Font("Tahoma", Font.BOLD, fontSize));

        switch (type) {
            //make it blue
            case "number" :
                color1 = new Color(96, 195, 220); //lighter blue
                color2 = new Color(78, 158, 200); //darker blue
                break;
            //make it red
            case "equals" :
                color1 = new Color(241, 141, 141); //lighter red
                color2 = new Color(229, 43, 49); //darker red
                break;
            //make it black
            case "clear" :
                color1 = new Color(93, 93, 94); //lighter red
                color2 = new Color(31, 31, 31); //darker red
                break;
            //make it yellow-orange
            case "operation" :
                color1 = new Color(253, 194, 45); //lighter red
                color2 = new Color(252, 157, 39); //darker red
                break;
        }
    }

    public String getType(){
        return this.type;
    }

    public String getCharacter(){
        return this.character;
    }

    public void flipGradient(){
        Color temp;
        temp = this.color1;
        this.color1 = this.color2;
        this.color2 = temp;
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

        // repaint text over gradient
        super.paintComponent(g);
    }
}
