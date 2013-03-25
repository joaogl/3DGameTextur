package com.game.src.graphics;

import static org.lwjgl.opengl.GL11.*;

public class Render {

	public Render() {
	}

	byte r = 0, g = 0, b = 0;

	public void tile(float x, float y, float size) {
		drawQuad(x, y, size, size);
	}

	private void drawQuad(float x, float y, float w, float h) {
		glBegin(GL_TRIANGLES);
		glVertex2f(x, y + h);
		glVertex2f(x, y);
		glVertex2f(x + w, y);

		glVertex2f(x, y + h);
		glVertex2f(x + w, y);
		glVertex2f(x + w, y + h);
		glEnd();
	}

	public void setColor(float r, float g, float b) {
		glColor3f(r, g, b);
	}
}