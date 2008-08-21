package dnl.games.stratego.server;


public enum StrategoPieceType {

	MARSHAL(1, "Marshal"), 
	GENERAL(2, "General"),
	COLONEL(3, "Colonel"),
	MAJOR(4, "Major"),
	CAPTAIN(5, "Captain"),
	LIEUTENANT(6, "Lieutenant"),
	SERGEANT(7, "Sergeant"),
	MINER(8, "Miner"),
	SCOUT(9, "Scout"),
	SPY(0, "Spy", 'S'),
	BOMB(-1, "Bomb", 'B'),
	FLAG(-2, "Flag", 'F')
	;
	
	private int rank;
	private String name;
	private char abbreviatedName;

	private StrategoPieceType(int rank, String name, char abbreviatedName) {
		this.rank = rank;
		this.name = name;
		this.abbreviatedName = abbreviatedName;
	}

	private StrategoPieceType(int rank, String name) {
		this(rank, name, Integer.toString(rank).charAt(0));
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
