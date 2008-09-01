package dnl.games.stratego;



public enum StrategoPieceType {

	MARSHAL(1, "Marshal", 1), 
	GENERAL(2, "General", 1),
	COLONEL(3, "Colonel", 2),
	MAJOR(4, "Major", 3),
	CAPTAIN(5, "Captain", 4),
	LIEUTENANT(6, "Lieutenant", 4),
	SERGEANT(7, "Sergeant", 4),
	MINER(8, "Miner", 5),
	SCOUT(9, "Scout", 8),
	SPY(0, "Spy", 'S', 1),
	BOMB(-1, "Bomb", 'B', 6),
	FLAG(-2, "Flag", 'F', 1),
	UNKNOWN(-3, "Unknown", 'X', 0)
	;
	
	private int rank;
	private int initialQuantity;
	private String name;
	private char abbreviatedName;

	private StrategoPieceType(int rank, String name, char abbreviatedName, int initialQuantity) {
		this.rank = rank;
		this.name = name;
		this.abbreviatedName = abbreviatedName;
		this.initialQuantity = initialQuantity;
	}

	private StrategoPieceType(int rank, String name, int initialQuantity) {
		this(rank, name, Integer.toString(rank).charAt(0), initialQuantity);
	}

	public int getRank() {
		return rank;
	}

	public String getName() {
		return name;
	}

	public char getAbbreviatedName() {
		return abbreviatedName;
	}
	
	public int getInitialQuantity() {
		return initialQuantity;
	}
	
	public static StrategoPieceType getByAbbreviatedName(char abbreviatedName){
		StrategoPieceType[] values = values();
		for (StrategoPieceType strategoPieceType : values) {
			if(Character.toUpperCase(abbreviatedName) == strategoPieceType.abbreviatedName){
				return strategoPieceType;
			}
		}
		return null;
	}
	
}
