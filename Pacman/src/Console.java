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

	// 該類不需要實例
	private Console() {

	}

	// 唯一常量對象
	private static Pui UI; // 第一层级
	private static Map PMAP; // 第二层级
	private static Str STR; // 第三层级(Logo标题)
	protected static Musicrun MR1; // 控制音乐斷點播放
	private static SystemContorl SC; // 系统控制逻辑
	private static Face F; // 玩家操控的对象
	private static Object keys;
	private static Boolean ISSTART = false; // 标识游戏是否已经开始

	// 创建游戏环境
	public static void createGame() {
		settingUI();
		settingMAP();
		settingLogo();
		settingMusic();
		addRaysinTheMap();
	}
	
	// 设置UI
	private static void settingUI() {

		UI = Pui.getUI();

		JMenu op = new JMenu("Option");
		JMenuItem st = new JMenuItem("Start Game");
		JMenuItem ab = new JMenuItem("About");
		UI.addMenus(op);
		UI.addMenuItems(op, st, ab);

		// 菜單鼠標點擊事件
		ClickAbout(ab);
		StartGame(st);
	}

	// 设置地图
	private static void settingMAP() {
		PMAP = Map.getMap();
		UI.add(PMAP);
		PMAP.updateUI();
	}

	// 设置Logo
	private static void settingLogo() {
		STR = new Str("Pacman", 5, 10, (UI.width_Size - 137) / 2, (UI.height_Size - 10) / 2, 56, 11);
		PMAP.add(STR);
		STR.paint(PMAP.getGraphics());
		STR.updateUI();
		PMAP.repaint();
	}

	// 设置音乐
	private static void settingMusic() {
		MR1 = new Musicrun(PathSwitch.switchPath(PathSwitch.MUSIC_1));
		MR1.isMain = true;
		MR1.start();
	}

	// 添加數個Rays作爲背景動畫
	private static void addRaysinTheMap() {
		SC = SystemContorl.getSystemContorl();
		SC.addRays(new Random().nextInt(10) + 20);
	}
	
	// 创建游戏
	protected static void newGame() {
		
		try {
			// 清除所有已画对象
			PMAP.removeAll();
			
			SC.removAll();
			SC.removeSystemContorl();
			SC = SystemContorl.getSystemContorl();
			
			MR1.status = 2;
			MR1.setCoro(false); // 标志下一个播放节点，并中断当前播放
			SC.registeredMC(MR1);
			
			Beans.BEANSLIST = null;
			Beans.addBeans(new Random().nextInt(5) + 3); // 加入指定的豆子
			
			if(keys != null) {
				UI.removeKeyListener((KeyListener)keys);
				keys = null;
			}
			
			F = new Face();
			keys = F.new Keys();
			PMAP.add(F); // 加入玩家操作的對象
			F.paint(PMAP.getGraphics());
			F.updateUI();
			
			UI.addKeyListener((KeyListener)keys); // 增加鍵盤監聽事件
			
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

	// 点击about时所触发的事件
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
				JOptionPane.showMessageDialog(UI, "Version 1.3\nControl Method:{↑↓←→}{Space}\nby Akari design", "About",
						JOptionPane.OK_CANCEL_OPTION, new ImageIcon(""));
			}
		});
	}

	// 点击Game Start时所触发的事件(游戏的整个执行过程将在该方法中控制)
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

						ISSTART = true; // 游戏开始,並清空背景動畫
						newGame();
						
					}
				
			}

		});

	}

}
