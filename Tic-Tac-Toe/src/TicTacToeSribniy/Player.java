package TicTacToeSribniy;

public class Player {

	private String name;
	private char mark;

	Player(String name, char mark) {
		this.name = name;
		this.mark = mark;
	}

	public String getName() {
		return name;
	}

	public char getMark() {
		return mark;
	}

}
