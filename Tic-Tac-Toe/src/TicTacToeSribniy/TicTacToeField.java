package TicTacToeSribniy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;

public class TicTacToeField {

	private final int field[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private Player p1, p2; // —обственно сами игроки , заделка на будущее что бы легче было вводить
							// функционал
	private Scanner in = new Scanner(System.in);
	private int n;
	private boolean isXMark = false;
	private boolean gameRetry;
	private final File f = new File("src\\TicTacToeSribniy\\History.txt");
	private String history;

	public TicTacToeField(String name1, String name2) {
		p1 = new Player(name1, 'X');
		p2 = new Player(name2, '0');
	}

	public void run() {
		int t = 0;
		System.out.println("Menu: \n1.Start game \n2.History \n3.Quit ");
		int n = in.nextInt();
		if (n < 3) {
			switch (n) {
			case 1:
				while (t != 2) {
					t = 0;
					startGame();
					System.out.println("Retry - press 1 \nMenu - press 2 ");
					gameRetry = true;
					t = in.nextInt();
				}
				gameRetry = true;
				run();

			case 2:
				System.out.println(readHistory());
				System.out.println("Press 1 to exit the menu");
				t = in.nextInt();
				run();
			case 3:
				return;
			}

		}
	}

	/*
	 * собсвенно цикл игры с проверками и выводом на консоль
	 */
	public void startGame() {
		if (gameRetry) {
			for (int i = 0; i < field.length; i++) {
				field[i] = 0;
			}
		}
		boolean b;
		gameRetry = false;
		do {
			isXMark = !isXMark;
			drawField();
			System.out.println("\nNow step " + (isXMark ? 'X' : '0'));
			int n = getNumber();
			field[n] = isXMark ? 1 : 2;
			b = !isWin(n);
			if (isDraw()) {
				System.out.println("Draw");
				return;
			}
		} while (b);
		drawField();
		System.out.println("\n" + (isXMark ? p1.getName() : p2.getName()) + " win the game !");
		printToHistory();
	}

	private int getNumber() {
		while (true) {
			System.out.println("Please choose a cell and enter this number");
			n = in.nextInt();
			if (n >= 0 && n < field.length && field[n] == 0) {
				return n;
			} else {
				System.out.println("!WRONG NUMBER! \nType correct number: ");
				getNumber();
			}
		}
	}

	private boolean isWin(int n) {
		// проверка по строке
		int row = n - n % 3;
		if (field[row] == field[row + 1] && field[row] == field[row + 2]) {
			return true;
		}
		// проверка по столбцу
		int col = n % 3;
		if (field[col] == field[col + 3] && field[col] == field[col + 6]) {
			return true;
		}
		// провер€ю что быы не вышло на грань
		if (n % 2 != 0) {
			return false;
		}
		// проверка по диагонали
		if (n % 4 == 0) {
			if (field[0] == field[4] && field[0] == field[8]) {
				return true;
			}
			if (n != 4) {
				return false;
			}
		}

		return field[2] == field[4] && field[2] == field[6];
	}

	private void drawField() {
		System.out.println();
		for (int i = 0; i < field.length; i++) {
			if (i != 0) {
				if (i % 3 == 0) {
					System.out.println();
					System.out.println("_____|_____|_____");
					System.out.println("     |     |     ");
				} else
					System.out.print("|");
			}

			if (field[i] == 0)
				System.out.print("  " + i + "  ");
			if (field[i] == 1)
				System.out.print("  X  ");
			if (field[i] == 2)
				System.out.print("  O  ");

		}
	}

	public boolean isDraw() {
		for (int n : field) {
			if (n == 0) {
				return false;
			}
		}
		return true;
	}

	private void printToHistory() {
		try (FileOutputStream fos = new FileOutputStream(f); PrintStream ps = new PrintStream(fos)) {
			if (!f.exists()) {
				f.createNewFile();
			}
			Date dateNow = new Date();
			ps.println(dateNow.toString());
			for (int i = 0; i < field.length; i++) {
				if (i != 0) {
					if (i % 3 == 0) {
						ps.println();
						ps.println("_____|_____|_____");
						ps.println("     |     |     ");
					} else
						ps.print("|");
				}

				if (field[i] == 0)
					ps.print("  " + i + "  ");
				if (field[i] == 1)
					ps.print("  X  ");
				if (field[i] == 2)
					ps.print("  O  ");
			}
			ps.println("\n" + (isXMark ? p1.getName() : p2.getName()) + " win the game !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String readHistory() {
		try (BufferedReader bf = new BufferedReader(new FileReader(f))) {
			String s;
			while ((s = bf.readLine()) != null) {
				history += ("\n" + s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return history;
	}

}
