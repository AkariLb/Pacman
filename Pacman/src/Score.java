
public class Score {
	
	// �����޷���ʵ����
	private Score() {
		
	}
	
	
	/*
	 * ����2��ָ��ֵ�����ۺϷ���
	 */
	public static int comprehensive_Score(Integer eatNUmber,Integer makeRays) {
		
		int score = 0;
		int divisor = 0;
		
		if(eatNUmber == null || eatNUmber == 0)
			return 0;
		
		if(makeRays == null || makeRays == 0)
			divisor = 2;
		else
			divisor = makeRays;
		
		return score = eatNUmber/divisor;
	}
	
	
}
