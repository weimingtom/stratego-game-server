package dnl.games.stratego;

import java.util.ArrayList;
import java.util.List;

public class StrategoPiece {
	private StrategoPieceType type;
	private PlayerType player;

	
	public StrategoPiece(PlayerType player, StrategoPieceType type) {
		if(player == null){
			throw new IllegalArgumentException("player cannot be null");
		}
		if(type == null){
			throw new IllegalArgumentException("type cannot be null");
		}
		this.player = player;
		this.type = type;
	}

	public static List<StrategoPiece> enumerateInitialPieces(PlayerType playerType){
		List<StrategoPiece> result = new ArrayList<StrategoPiece>();
		StrategoPieceType[] values = StrategoPieceType.values();
		for (StrategoPieceType strategoPieceType : values) {
			int quantity = strategoPieceType.getInitialQuantity();
			for (int i = 0; i < quantity; i++) {
				result.add(new StrategoPiece(playerType, strategoPieceType));
			}
		}
		if (result.size() != 40) {
			throw new IllegalStateException(
					"This game is started using 40 pieces of every color and not "
							+ result.size() + ".");
		}
		return result;
	}
	
	public boolean isUnknown(){
		return StrategoPieceType.UNKNOWN.equals(type);
	}

	public boolean isBomb(){
		return StrategoPieceType.BOMB.equals(type);
	}

	public boolean isFlag(){
		return StrategoPieceType.FLAG.equals(type);
	}

	public boolean isSpy(){
		return StrategoPieceType.SPY.equals(type);
	}

	public boolean isMiner(){
		return StrategoPieceType.MINER.equals(type);
	}

	public boolean isScout(){
		return StrategoPieceType.SCOUT.equals(type);
	}
	
	public StrategoPieceType getType() {
		return type;
	}

	public char getAbbreviatedName(){
		if(player.isRed()){
			return type.getAbbreviatedName();
		}
		switch (type) {
		case MARSHAL:
			return '!';
		case GENERAL:
			return '@';
		case COLONEL:
			return '#';
		case MAJOR:
			return '$';
		case CAPTAIN:
			return '%';
		case LIEUTENANT:
			return '^';
		case SERGEANT:
			return '&';
		case MINER:
			return '*';
		case SCOUT:
			return '(';
			
		default:
			return Character.toLowerCase(type.getAbbreviatedName());
		}
	}
	
	public static StrategoPiece parse(char abbreviatedName){
		switch (abbreviatedName) {
		case '!':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.MARSHAL);
		case '@':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.GENERAL);
		case '#':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.COLONEL);
		case '$':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.MAJOR);
		case '%':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.CAPTAIN);
		case '^':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.LIEUTENANT);
		case '&':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.SERGEANT);
		case '*':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.MINER);
		case '(':
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.SCOUT);
		}
		if(Character.isLowerCase(abbreviatedName)){
			return new StrategoPiece(PlayerType.BLUE, StrategoPieceType.getByAbbreviatedName(abbreviatedName));
		}
		StrategoPieceType pieceType = StrategoPieceType.getByAbbreviatedName(abbreviatedName);
		if(pieceType == null){
			return null;
		}
		return new StrategoPiece(PlayerType.RED, pieceType);
	}
	
	public PlayerType getPlayer() {
		return player;
	}
	

	@Override
	public String toString() {
		return getClass().getSimpleName()+"{"+player.name()+" "+type.getName()+"}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StrategoPiece other = (StrategoPiece) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

}
