package dnl.games.stratego;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class BoardMapReader {

	public static Board readSystemResource(String resourceName) throws IOException {
		InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
		if(is == null){
			throw new IllegalStateException("Did not find a system resource named: "+resourceName);
		}
		StringWriter stringWriter = new StringWriter();
		IOUtils.copy(is, stringWriter);
		String s = stringWriter.getBuffer().toString();
		Board board = BoardMapReader.read(s);
		return board;
	}

	public static Board read(String positions) {
		String errMessage = "Positions string must contain 10 lines of length 10.";
		String[] rows = StringUtils.split(positions, "\n");
		if (rows.length != 10) {
			throw new IllegalArgumentException(errMessage);
		}
		StrategoPiece[][] squares = new StrategoPiece[10][10];
		for (int i = 0; i < squares.length; i++) {
			char[] carr = rows[i].toCharArray();
			for (int j = 0; j < carr.length; j++) {
				if(carr.length > 10){
					throw new IllegalArgumentException("Given positions has a row that is larger than 10. rownum="+i);
				}
				if (carr[j] != '0') { // ignore ponds
					StrategoPiece piece = StrategoPiece.parse(carr[j]);
					if (carr[j] != ' ' && piece == null) {
						throw new IllegalStateException("No piece found for abreviation: "
								+ carr[j]);
					}
					squares[i][j] = piece;
				}
			}
		}
		Board board = new Board();
		board.setSquares(squares);
		return board;
	}

}
