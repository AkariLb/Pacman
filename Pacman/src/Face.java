import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class Face extends JPanel {

	// 颜色
	protected Color cr = new Color(255, 201, 14);
	// 体型
	protected int size = 20;;
	// 位置
	protected int posx = 100;
	protected int posy = 200;
	// 扇形值
	private int startAngle = -330;
	private int arcAngle = 300;
	// 眼睛位置
	private int eyeX = 10;
	private int eyeY = 5;
	// 速度
	protected int speed[] = { 1, 1 };
	// 吃的数量
	protected int eatNumber;
	// 创造的射线数量
	protected int makeRays;
	// 必杀技使用次数
	protected int steRay = 5;
	// 计数器每吃5个豆子增加一次必杀技值
	private int counter;
	// 取得上次输入的键值
	protected int lastTimeKey;
	// 必杀技CD是否已经冷却
	private Boolean isCold = true;
	private Timer T;
	private int cd = 3000;
	protected Boolean callContorl = false;
	protected FaceBeam fb;

	public Face() {

		this.setBounds(posx, posy, size + 1, size + 1);

	}

	public int getEatNumber() {

		return eatNumber;

	}

	public void setSpeed(int speed) {

		this.speed[0] = speed;
		this.speed[1] = speed;

	}

	public void setColor(Color c) {

		this.cr = c;

	}

	/**
	 * 该重写方法要求取得父级Graphics对象而不是自身
	 */
	@Override
	public void paint(Graphics g) {

		g.setColor(cr);
		g.fillArc(0, 0, size, size, startAngle, arcAngle);
		// Eye
		g.setColor(Color.BLACK); // Left and Right = 10,5 UP and Down = 5,10
		g.drawRect(eyeX, eyeY, 1, 1);

	}

	// 边缘碰撞检测
	private void wallCollision(int keyvar) {

		if (speed[0] == 0)
			speed[0] = speed[1];

		// 邊界限制座標
		int limitX = Pui.getUI().limit_width;
		int limitY = Pui.getUI().limit_height;

		if (posx >= limitX) {
			if (keyvar == KeyEvent.VK_RIGHT) {
				this.speed[0] = 0;
				return;
			}
		}

		if (posx <= 0) {
			if (keyvar == KeyEvent.VK_LEFT) {
				this.speed[0] = 0;
				return;
			}
		}

		if (posy >= limitY) {
			if (keyvar == KeyEvent.VK_DOWN) {
				this.speed[0] = 0;
				return;
			}
		}

		if (posy <= 0) {
			if (keyvar == KeyEvent.VK_UP) {
				this.speed[0] = 0;
			}
		}

	}

	// 重制脸的位置
	private void setBounds(int posx, int posy) {

		this.setBounds(posx, posy, size + 1, size + 1);

	}

	private void crateTimer(int second) {

		if (T == null) {
			T = new Timer();
			T.schedule(new TimerTask() {

				@Override
				public void run() {
					cdCold();
				}
			}, new Date(), second);
		}

	}

	private void cdCold() {
		this.isCold = true;
	}

	/**
	 * 此方法用来警示控制器玩家已经发射必杀技
	 */
	private void callContorl() {
		this.callContorl = true;
	}

	// 发射必杀技
	private void makeRay() {

		crateTimer(cd);

		if (!isCold)
			return;
		this.isCold = false;

		Musicrun mc = new Musicrun(PathSwitch.switchPath(PathSwitch.MUSIC_BEAM));

		if (steRay <= 0) {
			Pui.getUI().setTitle("Energy : (0)");
			mc.setPath(PathSwitch.switchPath( PathSwitch.MUSIC_MISS));
			mc.start();
			return;
		}

		Map map = Map.getMap();
		fb = new FaceBeam();
		Thread tfb = new Thread(fb);
		map.add(fb);
		tfb.start();
		--steRay;
		Pui.getUI().setTitle("Energy : (" + steRay + ")");
		mc.start();
		
		this.callContorl();
		
		if(makeRays < 999)
			++makeRays;
		
	}
	
	private void printLcation() {
		
		System.out.println("Face:posx="+posx+",poxy="+posy);
		
	}
	
	
	private void eat() {

		for (Beans b : Beans.BEANSLIST) {

			int beanSize = b.size * 3;
			if (posx >= b.x - beanSize && posx <= b.x + beanSize && posy >= b.y - beanSize && posy <= b.y + beanSize) {

				startAngle = 360;
				arcAngle = 360;
				Map.getMap().repaint();

				new Musicrun(PathSwitch.switchPath(PathSwitch.MUSIC_EAT)).start();

				b.x = Beans.RA.nextInt(Pui.getUI().limit_width);
				b.y = Beans.RA.nextInt(Pui.getUI().limit_height);

				if (b.x <= 0)
					b.x = 1;
				if (b.y <= 0)
					b.y = 1;

				b.setBounds(b.x, b.y, b.size, b.size);
				Map.getMap().repaint();
				
				if(eatNumber < 999)
					++eatNumber;

				// 每吃5个豆子增加一次必杀技使用
				if (!(steRay > 999))
					if (counter < 4) {
						++counter;
					} else {
						counter = 0;
						++steRay;
						Pui.getUI().setTitle("Energy : (" + steRay + ")");
					}

				return;

			}
		}

	}
	

	public class FaceBeam extends JPanel implements Runnable {

		protected int x;
		protected int y;
		protected int width;
		protected int height;
		private static final int SPEED = 45;
		protected int i;
		protected int z;

		public FaceBeam() {

			this.x = Face.this.posx + size / 2;
			this.y = Face.this.posy + size / 2;
			this.setBackground(Face.this.cr);

		}

		private void setBounds(int width, int height) {

			this.width = width;
			this.height = height;
			this.setBounds(x, y, width, height);

		}

		@Override
		public void run() {

			int keys = Face.this.lastTimeKey;

			if (keys == 0)
				keys = 39;

			if (keys == 37) {// Left
				this.x = 0;
				i = Face.this.posx;
				z = 10;
			} else if (keys == 38) {// Up
				this.y = 0;
				i = 10;
				z = Face.this.posy;
			} else if (keys == 39) {// Right
				this.x += 8;
				i = Pui.getUI().limit_width;
				z = 10;
			} else if (keys == 40) {// Down
				this.y += 8;
				i = 10;
				z = Pui.getUI().limit_height;
			}

			if (z == 10) {

				while (z > 0) {
					try {
						setBounds(i, z);
						Thread.sleep(SPEED);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					--z;
				}

			} else if (i == 10) {

				while (i > 0) {
					try {
						setBounds(i, z);
						Thread.sleep(SPEED);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					--i;
				}

			}

			Map.getMap().remove(this);
			Map.getMap().repaint();
			
		}

	}

	public class Keys extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {

			int keyvar = e.getKeyCode();

			// 设置指定值重画脸部
			switch (keyvar) {
			case 37: // Left
				startAngle = 200;
				arcAngle = 310;
				wallCollision(keyvar);
				posx -= speed[0];
				break;
			case 38: // Up
				startAngle = -300;
				arcAngle = -300;
				wallCollision(keyvar);
				posy -= speed[0];
				break;
			case 39: // Right
				startAngle = -330;
				arcAngle = 300;
				wallCollision(keyvar);
				posx += speed[0];
				break;
			case 40: // Down
				startAngle = 300;
				arcAngle = 300;
				wallCollision(keyvar);
				posy += speed[0];
				break;
			case 32:
				makeRay();
				return;
			}
			eat();
			// 设置指定值重眼睛
			if (keyvar == KeyEvent.VK_DOWN || keyvar == KeyEvent.VK_UP) {
				eyeX = 5;
				eyeY = 10;
			} else {
				eyeX = 10;
				eyeY = 5;
			}

			lastTimeKey = keyvar;
			setBounds(posx, posy);
			
		//	printLcation();
			
		}
		
	}

}
