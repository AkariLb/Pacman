import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class Musicrun extends Thread {

	public int status; // ��״̬��ʾ���ŵ�ʱ��� 1=��ҳ��2=��Ϸ1��3=��Ϸ2,4=��Ϸ����
	private InputStream in;
	private MyPlay p;
	protected Boolean isMain = false;	// �ö����Ƿ�Ϊ��Ҫ�������ֶ���
	
	public void setPath(InputStream in) {
		this.in = in;
	}

	public Musicrun(InputStream in) {
		this.status = 1;
		this.in = in;
	}

	public void playSound() {
		try {
			AdvancedPlayer p = new AdvancedPlayer(in);
			p.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
	}

	public void playMusic() {
		try {
			while (true) {
				if (p == null)
					p = new MyPlay(new BufferedInputStream(PathSwitch.switchPath(PathSwitch.MUSIC_1)));
				switch (status) {
				case 1:
					p.play(0, 1165);
					break;
				case 2:
					p.play(1410, 2460);
					break;
				case 3:
					p.play(2480, 3290);
					break;
				case 4:
					p.play(3660, 4830);
					break;
				}
				p = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setCoro(Boolean CloseOrOpen) {
		p.setcloseOrOpen(CloseOrOpen);
	}

	public Boolean getCoro() {
		return p.getCloseOrOpen();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();

		if (isMain)
			playMusic();
		else
			playSound();
	}

	class MyPlay extends AdvancedPlayer {
		public MyPlay(InputStream stream) throws JavaLayerException {
			super(stream);
		}
	}
}