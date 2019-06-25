import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PathSwitch {
	
	private static PathSwitch ps;
	
	public final static String IMAGE_ICON0 = "image/eat-icon-0.png";
	public final static String IMAGE_ICON1 = "image/eat-icon-1.png";
	public final static String MUSIC_1 = "music/1.mp3";
	public final static String MUSIC_BEAM = "music/beam.mp3";
	public final static String MUSIC_EAT = "music/eat.mp3";
	public final static String MUSIC_MISS = "music/miss.mp3";
	
	private PathSwitch() {

	}

	public static InputStream switchPath(String path) {
		
		if(ps == null)
			ps = new PathSwitch();
		
		InputStream input = ps.getClass().getResourceAsStream(path);
		
		
		return input;
	}

}
