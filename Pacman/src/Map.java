import java.awt.Color;

import javax.swing.JPanel;

public class Map extends JPanel {

	private static Map MAP;

	private Map() {
		this.setBackground(new Color(163, 148, 128));
		this.setLayout(null); // 清除默认布局,使用绝对定位
		setVisible(false);
		setVisible(true);
	}

	public static Map getMap() {
		if (MAP == null)
			MAP = new Map();
		return MAP;
	}

}
