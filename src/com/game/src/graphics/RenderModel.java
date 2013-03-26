package com.game.src.graphics;

import java.util.List;

import org.lwjgl.opengl.GL11;

public class RenderModel {

	public static void render(List<float[]> vertices, List<float[]> normals, List<float[]> texCoords, List<int[][]> faces) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		{
			for (int NFACE = 0; NFACE < faces.size(); NFACE++) {
				int[][] face = faces.get(NFACE);

				int[] pointA = face[0];
				int[] pointB = face[1];
				int[] pointC = face[2];

				float[] pointA_vert = vertices.get(pointA[0]);
				float[] pointA_texc = texCoords.get(pointA[1]);
				float[] pointA_norm = normals.get(pointA[2]);

				float[] pointB_vert = vertices.get(pointB[0]);
				float[] pointB_texc = texCoords.get(pointB[1]);
				float[] pointB_norm = normals.get(pointB[2]);

				float[] pointC_vert = vertices.get(pointC[0]);
				float[] pointC_texc = texCoords.get(pointC[1]);
				float[] pointC_norm = normals.get(pointC[2]);

				GL11.glTexCoord2f(pointA_texc[0], pointA_texc[1]);
				GL11.glNormal3f(pointA_norm[0], pointA_norm[1], pointA_norm[2]);
				GL11.glVertex3f(pointA_vert[0], pointA_vert[1], pointA_vert[2]);

				GL11.glTexCoord2f(pointB_texc[0], pointB_texc[1]);
				GL11.glNormal3f(pointB_norm[0], pointB_norm[1], pointB_norm[2]);
				GL11.glVertex3f(pointB_vert[0], pointB_vert[1], pointB_vert[2]);

				GL11.glTexCoord2f(pointC_texc[0], pointC_texc[1]);
				GL11.glNormal3f(pointC_norm[0], pointC_norm[1], pointC_norm[2]);
				GL11.glVertex3f(pointC_vert[0], pointC_vert[1], pointC_vert[2]);
			}
		}
		GL11.glEnd();
	}

}
