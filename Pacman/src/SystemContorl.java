import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SystemContorl extends Thread {

	private static Musicrun MC;
	private static SystemContorl SC;
	protected List<Rays> RS = new ArrayList<Rays>();
	private List<Thread> th = new ArrayList<Thread>();
	private Face f;
	private int second = 30000;
	private static Timer T;
	private int totalAdd;

	private SystemContorl() {
		// crateTimer();
	}

	public static SystemContorl getSystemContorl() {

		if (SC == null)
			SC = new SystemContorl();

		return SC;
	}

	public static void removeSystemContorl() {
		SC = null;
	}

//	// ��ʱ�������
//	public void crateTimer() {
//		
//		if (T == null) {
//			T = new Timer();
//			T.schedule(new TimerTask() {
//
//				@Override
//				public void run() {
//					System.out.println(RS.size());
//					if(RS.size() >= 70) {
//						System.out.println("Exit timer, reason RS size too long");
//						return ;
//					}
//					
//					System.out.println("Timer start");
//					addRays(new Random().nextInt(9)+1);
//					
//					
//					if(totalAdd == 5) {
//						Console.MR1.status = 3;
//						Console.MR1.setCoro(false);
//						f.speed[0] = 2;
//						f.speed[1] = 2;
//					}
//					
//					++totalAdd;
//					
//					
//				}
//			}, new Date(), second);
//		}
//		
//	}

	/**
	 * ʹ��ǰ���ע��һ��Face����
	 * 
	 * @param f
	 */
	public void registeredFace(Face f) {
		this.f = f;
	}

	/**
	 * ע��һ��Ĭ�ϵı������ֶ���
	 */
	public void registeredMC(Musicrun mc) {

		if (MC == null)
			MC = mc;

	}

	/**
	 * �h��������Map�е�Rays
	 */
	public void removAll() {

		if (th == null || RS == null)
			return;

		Map.getMap().removeAll();
		Map.getMap().repaint();

		for (int i = 0; i < RS.size(); i++) {

			try {

				Rays r = (Rays) RS.get(i);
				r.setSleepSpeed(0);
				r = null;

				Thread t = (Thread) th.get(i);
				t.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		this.th = new ArrayList<Thread>();
		this.RS = new ArrayList<Rays>();

	}

	/**
	 * �h��һ��ָ����Rays
	 */
	public void removeTarget(Rays r) {

		Map.getMap().remove(r);
		Map.getMap().repaint();

		RS.remove(r);
		r.isLive = false;
		th.remove(r);
		r = null;

	}

	/**
	 * Ĭ�J��Ӕ�ֵ��1
	 * 
	 * @param rayNumber
	 */
	public void addRays(Integer rayNumber) {

		if (th == null || RS == null) {
			this.th = new ArrayList<Thread>();
			this.RS = new ArrayList<Rays>();
		}

		if (rayNumber == null || rayNumber == 0)
			rayNumber = 1;

		for (int i = 0; i < rayNumber; i++) {

			// System.out.println(i);

			Rays r = new Rays();
			RS.add(r);
			Map.getMap().add(r);
			r.updateUI();

			Thread t = new Thread(r);
			th.add(t);
			t.start();

		}

		System.out.println("�������ӣ�" + rayNumber);
		System.out.println("����������=" + RS.size() + ",���߳���=" + th.size());

	}

	@Override
	public void run() {

		while (true) {

			for (int i = 0; i < RS.size(); i++) {

				// Rays location
				Rays r = RS.get(i);

				int rx1 = r.x;
				int ry1 = r.y;

				int posx1 = f.posx;
				int posx2 = posx1 + f.size;
				int posy1 = f.posy;
				int posy2 = posy1 + f.size;

				// collision check
				if (

				rx1 >= posx1 && rx1 <= posx2 && ry1 >= posy1 && ry1 <= posy2 && rx1 + r.width >= posx1
						&& rx1 + r.width <= posx2 && ry1 + r.height >= posy1 && ry1 + r.height <= posy2

				) {

					r.setBackground(Color.GREEN);
					Map.getMap().removeAll();

					// ��Ϸ��������
					MC.status = 4;
					MC.setCoro(false);

					// ��������
					int score = Score.comprehensive_Score(f.eatNumber, f.makeRays);
					int option = JOptionPane.showConfirmDialog(Pui.getUI(),
							"You are die\n" + "MakeRays:[" + f.makeRays + "]\n" + "Eat:[" + f.eatNumber + "]\n"
									+ "Score:[" + score + "]\n" + "do you want try again?",
							"Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
							new ImageIcon(this.getClass().getResource(PathSwitch.IMAGE_ICON1)));

					// ���ѡ���˳���Ϸ
					if (option != 0)
						System.exit(0);

					// �½���Ϸ
					Console.newGame();

				}

			}

			// ����]���յ�face�ĺ��Єt�M�붨�r�����ߠ�B
			if (!f.callContorl) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {

				f.callContorl = false;
				Face.FaceBeam fb = f.fb;

				int checkDirection = 0; // 10 = y, 20 = x
				int checkStart = 0;
				int checkEnd = 0;

				if (fb.y == 0 || fb.z == Pui.getUI().limit_height) {// y
					checkDirection = 10;

					if (fb.y == 0) { // UP
						checkStart = fb.z;
						checkEnd = 0;
					} else { // Down
						checkStart = Pui.getUI().limit_height;
						checkEnd = fb.y += 8;
					}

				} else if (fb.x == 0 || fb.i == Pui.getUI().limit_width) { // x
					checkDirection = 20;

					if (fb.x == 0) {// Left
						checkStart = fb.i;
						checkEnd = 0;
					} else {// Right
						checkStart = Pui.getUI().limit_width;
						checkEnd = fb.x += 8;
					}

				} else {
					System.out.println("break");
					break;
				}

				// ����Face�����������������ͬһ��ֱ���ϵ��ϰ���
				for (int i = 0; i < RS.size(); i++) {

					Rays r = (Rays) RS.get(i);

					switch (checkDirection) {
					case 10: // y

						// ȡ��faceΪλ��x
						int x = f.posx;

						if (r.y <= checkStart && r.y >= checkEnd) {

							if (r.x >= x && r.x <= x + f.size) {
								removeTarget(r);
								// System.out.println("remove1="+i);
							}

						}

						break;
					case 20:// x

						int y = f.posy;

						if (r.x <= checkStart && r.x >= checkEnd) {

							if (r.y >= y && r.y <= y + f.size) {
								removeTarget(r);
								// System.out.println("remove2="+i);
							}

						}

						break;
					default:
						return;
					}

				}

				Map.getMap().remove(fb);
				fb = null;
			}

		}

	}

}
