import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class Beans extends JPanel {

	private Color cr = Color.YELLOW;
	protected static Random RA = new Random();
	protected static List<Beans> BEANSLIST = new ArrayList<Beans>(); // 用于管理所有的Beans对象
	protected int x;
	protected int y;
	protected int size = 5;

	private Beans() {
		this.x = RA.nextInt(Pui.getUI().limit_width);
		this.y = RA.nextInt(Pui.getUI().limit_height);
		this.setBounds(x, y, size, size);
		this.setBackground(cr);
	}

	public void setColor(Color cr) {
		this.cr = cr;
	}

	/**
	 * 增加指定的Beans進入到MAP中 該方法需要Map、Pui類的支持
	 * 
	 * @param quantity = null或者0則增加一個
	 */
	public static void addBeans(Integer quantity) {

		if (quantity == null || quantity == 0) {
			quantity = 1;
		}
		
		if(BEANSLIST == null) {
			BEANSLIST = new ArrayList<Beans>();
		}
		
		try {

			for (int i = 0; i < quantity; i++) {

				// System.out.println(i);

				Beans b = new Beans();

				BEANSLIST.add(b);
				Map.getMap().add(b);
				b.updateUI();

			}

		} catch (Exception e) {
			System.out.println("沒有受到指定對象的支持！");
		}

	}

}
