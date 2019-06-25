import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

// ����
public class Pui extends JFrame {

	private static Pui UI;
	// �L��
	protected int height_Size = 430;
	protected int width_Size = 496;
	// �����L��
	protected int limit_height = 415;
	protected int limit_width = 400;

	private Pui() {// Ĭ������
		this.setTitle("Pacman");
		this.setIconImage(new ImageIcon(this.getClass().getResource(PathSwitch.IMAGE_ICON0)).getImage());
		this.setLocationByPlatform(true);
		this.setSize(height_Size, width_Size);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static Pui getUI() {
		if (UI == null)
			UI = new Pui();
		return UI;
	}

	/***
	 * ������n���˵�ѡ��
	 * 
	 * @param jms(���ݷ���Ķ��������������ͬ�����Ĳ˵�ѡ��)
	 */
	public void addMenus(JMenu... jms) {
		if (jms == null)
			return;
		JMenuBar jmb = new JMenuBar();
		for (JMenu j : jms) {
			jmb.add(j);
			j.updateUI();
		}
		this.setJMenuBar(jmb);
	}

	/***
	 * ����ָ����JMenu��������Ӳ˵���
	 * 
	 * @param obj(��Ҫ��ӵĶ���)
	 * @param mis(�����1-n���������Ӳ˵���)
	 */
	public void addMenuItems(JMenu obj, JMenuItem... mis) {
		if (obj == null || mis == null)
			return;
		for (JMenuItem j : mis) {
			obj.addSeparator();
			obj.add(j);
			j.updateUI();
		}

	}

}
