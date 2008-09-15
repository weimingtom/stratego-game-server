package dnl.games.stratego;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class BoardMapReader {

	public static Board readResource(String resourceName) throws IOException {
		InputStream is = BoardMapReader.class.getResourceAsStream(resourceName);
		if(is == null){
			throw new IllegalStateException("Did not find a system resource named: "+resourceName);
		}
		return readStream(is);
	}

	public static Board readFile(File f) throws IOException {
		return readStream(new FileInputStream(f));
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Board readStream(InputStream is) throws IOException {
		StringWriter stringWriter = new StringWriter();
		IOUtils.copy(is, stringWriter);
		String s = stringWriter.getBuffer().toString();
		Board board = read(s);
		return board;
	}

	/**
	 * 
	 * @param board
	 * @param f
	 * @throws IOException
	 */
	public static void save(Board board, File f) throws IOException{
		StringBuilder sb = new StringBuilder();
		StrategoPiece[][] squares = board.getSquares();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				if(squares[i][j] == null){
					sb.append(" ");
				}
				else {
					sb.append(squares[i][j].getAbbreviatedName());
				}
			}
			sb.append("\n");
		}
		FileUtils.writeStringToFile(f, sb.toString());
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
