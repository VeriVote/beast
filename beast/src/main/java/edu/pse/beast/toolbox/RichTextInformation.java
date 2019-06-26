package edu.pse.beast.toolbox;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RichTextInformation {
    public final String text;
    public final javafx.scene.text.Font font;
    public final Color color;

    /**
     *
     * @param text the text which has to be displayed
     * @param font the font the text should have
     * @param color the color the text should have
     */
    public RichTextInformation(String text, Font font, Color color) {
        this.text = text;
        this.font = font;
        this.color = color;
    }
    
    /**
    *
    * @param represents standard text in black with size 12
    */
   public RichTextInformation(String text) {
       this.text = text;
       this.font = new Font(12);
       this.color = Color.BLACK;
   }
   
   public Bounds getSize() {
	   Text textField = new Text(this.text);
	   textField.setFont(this.font);
	   
	   return textField.getBoundsInLocal();
   }
}
