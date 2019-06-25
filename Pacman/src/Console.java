import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Console {

	// ԓ���Ҫ����
	private Console() {

	}

	// Ψһ��������
	private static Pui UI; // ��һ�㼶
	private static Map PMAP; // �ڶ��㼶
	private static Str STR; // �����㼶(Logo����)
	protected static Musicrun MR1; // �������֔��c����
	private static SystemContorl SC; // ϵͳ�����߼�
	private static Face F; // ��ҲٿصĶ���
	private static Object keys;
	private static Boolean ISSTART = false; // ��ʶ��Ϸ�Ƿ��Ѿ���ʼ

	// ������Ϸ����
	public static void createGame() {
		settingUI();
		settingMAP();
		settingLogo();
		settingMusic();
		addRaysinTheMap();
	}
	
	// ����UI
	private static void settingUI() {

		UI = Pui.getUI();

		JMenu op = new JMenu("Option");
		JMenuItem st = new JMenuItem("Start Game");
		JMenuItem ab = new JMenuItem("About");
		UI.addMenus(op);
		UI.addMenuItems(op, st, ab);

		// �ˆ�����c���¼�
		ClickAbout(ab);
		StartGame(st);
	}

	// ���õ�ͼ
	private static void settingMAP() {
		PMAP = Map.getMap();
		UI.add(PMAP);
		PMAP.updateUI();
	}

	// ����Logo
	private static void settingLogo() {
		STR = new Str("Pacman", 5, 10, (UI.width_Size - 137) / 2, (UI.height_Size - 10) / 2, 56, 11);
		PMAP.add(STR);
		STR.paint(PMAP.getGraphics());
		STR.updateUI();
		PMAP.repaint();
	}

	// ��������
	private static void settingMusic() {
		MR1 = new Musicrun(PathSwitch.switchPath(PathSwitch.MUSIC_1));
		MR1.isMain = true;
		MR1.start();
	}

	// ��Ӕ���Rays���������Ӯ�
	private static void addRaysinTheMap() {
		SC = SystemContorl.getSystemContorl();
		SC.addRays(new Random().nextInt(10) + 20);
	}
	
	// ������Ϸ
	protected static void newGame() {
		
		try {
			// ��������ѻ�����
			PMAP.removeAll();
			
			SC.removAll();
			SC.removeSystemContorl();
			SC = SystemContorl.getSystemContorl();
			
			MR1.status = 2;
			MR1.setCoro(false); // ��־��һ�����Žڵ㣬���жϵ�ǰ����
			SC.registeredMC(MR1);
			
			Beans.BEANSLIST = null;
			Beans.addBeans(new Random().nextInt(5) + 3); // ����ָ���Ķ���
			
			if(keys != null) {
				UI.removeKeyListener((KeyListener)keys);
				keys = null;
			}
			
			F = new Face();
			keys = F.new Keys();
			PMAP.add(F); // ������Ҳ����Č���
			F.paint(PMAP.getGraphics());
			F.updateUI();
			
			UI.addKeyListener((KeyListener)keys); // �����I�P�O �¼�
			
			SC.addRays(40);
			SC.registeredFace(F);
			SC.start();
			
			PMAP.repaint();
			
			System.gc();
			
		}catch(Exception e) {
			e.printStackTrace();
			StringWriter sw = new StringWriter();    
			PrintWriter pw = new PrintWriter(sw);    
			e.printStackTrace(pw);
			String str = sw.toString();
			JOptionPane.showConfirmDialog(UI, str);
			
		}
		
	}

	// ���aboutʱ���������¼�
	protected static void ClickAbout(JMenuItem ab) {
		ab.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(UI, "Version 1.3\nControl Method:{��������}{Space}\nby Akari design", "About",
						JOptionPane.OK_CANCEL_OPTION, new ImageIcon(""));
			}
		});
	}

	// ���Game Startʱ���������¼�(��Ϸ������ִ�й��̽��ڸ÷����п���)
	protected static void StartGame(JMenuItem st) {
		st.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseClicked(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
				
					if (ISSTART != false) {
						JOptionPane.showMessageDialog(UI, "Already started", "Warning", JOptionPane.OK_CANCEL_OPTION);
					} else {

						ISSTART = true; // ��Ϸ��ʼ,�K��ձ����Ӯ�
						newGame();
						
					}
				
			}

		});

	}

}
