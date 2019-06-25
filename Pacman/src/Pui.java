import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

// 顶级
public class Pui extends JFrame {

	private static Pui UI;
	// 長寬
	protected int height_Size = 430;
	protected int width_Size = 496;
	// 限制長寬
	protected int limit_height = 415;
	protected int limit_width = 400;

	private Pui() {// 默认设置
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
	 * 可增加n个菜单选项
	 * 
	 * @param jms(根据放入的对象个数来生成相同个数的菜单选项)
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
	 * 对于指定的JMenu对象添加子菜单栏
	 * 
	 * @param obj(需要添加的对象)
	 * @param mis(可添加1-n个，复数子菜单栏)
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
