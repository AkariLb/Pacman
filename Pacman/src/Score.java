
public class Score {
	
	// 该类无法被实例化
	private Score() {
		
	}
	
	
	/*
	 * 输入2个指定值给出综合分数
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
