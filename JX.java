import java.util.*;
@SuppressWarnings("unused")

public class JX {

	public static int xSize, ySize, b;

	public static void main(String[] args) {

		while (Game.getConfig() == false) {
		}

		Game.generateNew();

		boolean survival = true;

		while (survival == true) {
			Command cmd = new Command();
			int[] command = cmd.getCommand();
			if (command[0] < xSize && command[1] < ySize
					&& Game.statusCheck(command) != true)
				survival = Game.act(command);
			else
				System.out.println("error-invalid position");
			Game.updateScreen();
			if (Game.numOfUnrevealed() == b) {
				System.out.println("YOU WIN");
				while (Game.getConfig() == false) {
				}
				Game.generateNew();
			}
		}
		System.out.println("Game over, you died");

	}
}
