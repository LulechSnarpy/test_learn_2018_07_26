package club.iskyc.lulech.elden.runju;

public class Main {
	public static void main(String[] args) {
		CheckBoard cb = new CheckBoard();
		Stone stone = new Stone();
		stone.setX(7);
		stone.setY(8);
		stone.setBlack(true);
		cb.setStone(stone);
		stone.setX(7);
		stone.setY(7);
		stone.setBlack(false);
		cb.setStone(stone);
		stone.setX(8);
		stone.setY(7);
		stone.setBlack(true);
		cb.setStone(stone);
		PrintTool.PrintSites(cb.getSites());
	}
}
