package fr.utt.sit.lo02.vue;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * <p>La classe ImagePanel hérite de Panel et
 * permet de produire une image redimensionnée.</p>
 * 
 */
public class ImagePanel extends JPanel{
	
	private static final long serialVersionUID = 6354072657133010141L;
	private BufferedImage image;
	
	public ImagePanel(String src) {
		try {
			image = ImageIO.read(new File(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ImagePanel(String src, int w, int h) {
		try {
			image = ImageIO.read(new File(src));
			Image tmp = image.getScaledInstance(w, h, Image.SCALE_DEFAULT);
			image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = image.createGraphics();
	        g2d.drawImage(tmp, 0, 0, null);
	        g2d.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
}