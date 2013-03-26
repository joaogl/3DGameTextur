package com.game.engine;

import org.lwjgl.input.Keyboard;

public class InputHandler {

	public static boolean up, down, left, right, jump, speedup, slowdown, esc;

	public InputHandler() {

	}

	public void update() {
		up = Keyboard.isKeyDown(Keyboard.KEY_W);
		down = Keyboard.isKeyDown(Keyboard.KEY_S);
		left = Keyboard.isKeyDown(Keyboard.KEY_A);
		right = Keyboard.isKeyDown(Keyboard.KEY_D);

		jump = Keyboard.isKeyDown(Keyboard.KEY_SPACE);

		speedup = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		slowdown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		esc = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}

}