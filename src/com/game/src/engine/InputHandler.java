package com.game.src.engine;

import org.lwjgl.input.Keyboard;

public class InputHandler {

	boolean up, down, left, right, flyup, flydown, speedup, slowdown, esc;

	public void udate() {
		up = Keyboard.isKeyDown(Keyboard.KEY_W);
		down = Keyboard.isKeyDown(Keyboard.KEY_S);
		left = Keyboard.isKeyDown(Keyboard.KEY_A);
		right = Keyboard.isKeyDown(Keyboard.KEY_D);

		flyup = Keyboard.isKeyDown(Keyboard.KEY_E);
		flydown = Keyboard.isKeyDown(Keyboard.KEY_Q);

		speedup = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		slowdown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
		esc = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}

}