import java.awt.Color;

import javax.swing.JPanel;

public class Map extends JPanel {

	private static Map MAP;

	private Map() {
		this.setBackground(new Color(163, 148, 128));
		this.setLayout(null); // ���Ĭ�ϲ���,ʹ�þ��Զ�λ
		setVisible(false);
		setVisible(true);
	}

	public static Map getMap() {
		if (MAP == null)
			MAP = new Map();
		return MAP;
	}

}
