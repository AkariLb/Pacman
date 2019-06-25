import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

public class Rays extends JPanel implements Runnable {

	// 依賴對象
	private static Pui PUI = Pui.getUI();
	// 射线的x轴与y轴
	protected int x;
	protected int y;
	// 轴的状态true表示++,false--
	private Boolean xStatus;
	private Boolean yStatus;
	// 高宽比
	protected int height;
	protected int width;
	// 射线的睡眠速度
	private int sleepSpeed;
	// 生成射线出现位置
	protected int xOry;
	private Random r;
	// 生命期
	protected Boolean isLive = true;

	/**
	 * 該對象創建時必須依賴于類Pui
	 */
	public Rays() {
		this.setBackground(new Color(70, 130, 180));
		r = new Random();
	}

	public void setSleepSpeed(int sleepSpeed) {
		this.sleepSpeed = sleepSpeed;
	}

	@Override
	public void run() {
		// 随机射线所出现的位置0为x，1为反向x,2为y,3为反向y
		xOry = r.nextInt(4);
		switch (xOry) {
		case 0:
			x = 0;
			y = r.nextInt(PUI.limit_height);
			width = 15;
			height = 5;
			xStatus = true;
			yStatus = null;
			break;
		case 1:
			x = PUI.limit_width;
			y = r.nextInt(PUI.limit_height);
			width = 15;
			height = 5;
			xStatus = false;
			yStatus = null;
			break;
		case 2:
			x = r.nextInt(PUI.limit_width);
			y = 0;
			width = 5;
			height = 15;
			yStatus = true;
			xStatus = null;
			break;
		case 3:
			x = r.nextInt(PUI.limit_width);
			y = PUI.height_Size;
			width = 5;
			height = 15;
			yStatus = false;
			xStatus = null;
			break;
		}
		// 初始化射线速度10~110
		sleepSpeed = r.nextInt(100) + 10;

		// System.out.println("info:\n" + "x=" + x + ",y=" + y);

		while (true) {

			// System.out.println("isLive");

			// 結束生命期
			if (isLive == false)
				return;

			this.setBounds(x, y, width, height);
			if (xStatus != null) {
				if (xStatus) {
					++x;
					if (x >= (PUI.limit_width))
						xStatus = false;
				} else {
					--x;
					if (x <= 0)
						xStatus = true;
				}
			}
			if (yStatus != null) {
				if (yStatus) {
					++y;
					if (y >= (PUI.limit_height))
						yStatus = false;
				} else {
					--y;
					if (y <= 0)
						yStatus = true;
				}
			}
			try {
				Thread.sleep(sleepSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}