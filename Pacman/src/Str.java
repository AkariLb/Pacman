import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Str extends JPanel {

	public Str(String str, int strx, int stry, int panelx, int panely, int panelWidth, int panelHeight) {
		super();
		this.str = str;
		this.strx = strx;
		this.stry = stry;
		this.panelx = panelx;
		this.panely = panely;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
	}

	private String str;
	private int strx;
	private int stry;
	private int panelx;
	private int panely;
	private int panelWidth;
	private int panelHeight;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.YELLOW);
		this.setBackground(Color.red);
		this.setBounds(panelx, panely, panelWidth, panelHeight);
		if (str != null)
			g.drawString(str, strx, stry);
	}
}