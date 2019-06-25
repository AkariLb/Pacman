import java.awt.Color;
import java.util.Random;
import javax.swing.JPanel;

public class Rays extends JPanel implements Runnable {

	// ��ه����
	private static Pui PUI = Pui.getUI();
	// ���ߵ�x����y��
	protected int x;
	protected int y;
	// ���״̬true��ʾ++,false--
	private Boolean xStatus;
	private Boolean yStatus;
	// �߿��
	protected int height;
	protected int width;
	// ���ߵ�˯���ٶ�
	private int sleepSpeed;
	// �������߳���λ��
	protected int xOry;
	private Random r;
	// ������
	protected Boolean isLive = true;

	/**
	 * ԓ���󄓽��r�����ه���Pui
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
		// ������������ֵ�λ��0Ϊx��1Ϊ����x,2Ϊy,3Ϊ����y
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
		// ��ʼ�������ٶ�10~110
		sleepSpeed = r.nextInt(100) + 10;

		// System.out.println("info:\n" + "x=" + x + ",y=" + y);

		while (true) {

			// System.out.println("isLive");

			// �Y��������
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