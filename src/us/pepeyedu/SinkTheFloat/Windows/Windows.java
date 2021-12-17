package us.pepeyedu.SinkTheFloat.Windows;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Windows {
	private JFrame frame;
	private String title;
	private int x;
	private int y;
	public Windows(String title, int x, int y, Canvas canvas) {
		this.x = x;
		this.y = y;
		this.title = title;
		frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(x, y));
		frame.setMinimumSize(new Dimension(x, y));
		frame.setMaximumSize(new Dimension(x, y));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/logo_sintitulo.png")));
		frame.add(canvas);
	}
	public String getTitle() {
		return title;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getXToPaint() {
		return x - 6;
	}
	public int getYToPaint() {
		return y - 29;
	}
	public void setVisible() {
		frame.setVisible(true);
	}
}
