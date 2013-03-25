package com.game.src;

public class Main {

	public static void main(String[] args) {
		Game game = new Game();
		Game.setSize(1200, (1200 / 16 * 9));
		game.start();
	}

}