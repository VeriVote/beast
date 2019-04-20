package edu.pse.beast.highlevel.javafx.resultpresenter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import edu.pse.beast.highlevel.javafx.GUIController;
import javafx.scene.image.ImageView;

public class ResultPresenterNEW implements Runnable {
	
	
	private ImageView view = new ImageView();
	
	private BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_4BYTE_ABGR);

	public ResultPresenterNEW() {
		
		for(int i = 0; i < 200; i++) {
			for(int j = 0; j < 200; j++) {
				img.setRGB(i, j, new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), (float)Math.random()).getRGB());
			}
		}
		
		
		GUIController.getController().getResultPane().getChildren().add(view);
		
		//view.setImage(SwingFXUtils.toFXImage(img, null));
	}

	@Override
	public void run() {
		boolean running = true;
		
		while(running) {
			
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}