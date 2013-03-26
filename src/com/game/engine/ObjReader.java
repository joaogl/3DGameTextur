package com.game.engine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple Wavefront Object reader.
 * 
 * @author Longor
 **/
public class ObjReader implements Runnable {

	private Scanner scanner;
	public boolean isDoneReading;
	public ArrayList<float[]> vertices;
	public ArrayList<float[]> normals;
	public ArrayList<float[]> texCoords;
	public ArrayList<int[][]> faces;

	public ObjReader(InputStream in) {
		scanner = new Scanner(in);
		isDoneReading = false;

		vertices = new ArrayList<float[]>(8);
		normals = new ArrayList<float[]>(8);
		texCoords = new ArrayList<float[]>(8);
		faces = new ArrayList<int[][]>(4);

		vertices.add(new float[] { 0, 0, 0, 1 });
		normals.add(new float[] { 0, 1, 0 });
		texCoords.add(new float[] { 0, 0 });
	}

	public void run() {

		String line = null;
		while (scanner.hasNextLine()) {
			line = scanner.nextLine().trim();

			if (line.isEmpty()) continue;

			if (line.length() < 4) continue;

			if (line.startsWith("#")) continue;

			if (line.indexOf(' ') == -1) continue;

			processLine(line);
		}

		scanner.close();
		isDoneReading = true;
	}

	private void processLine(String line) {
		String[] lineSplit = line.split(" ");
		String lineCommand = lineSplit[0];

		if (lineCommand.equalsIgnoreCase("v") && lineSplit.length >= 4) {
			float x = Float.parseFloat(lineSplit[1]);
			float y = Float.parseFloat(lineSplit[2]);
			float z = Float.parseFloat(lineSplit[3]);

			if (lineSplit.length > 4) {
				// Vertice has a W component.
				float w = Float.parseFloat(lineSplit[4]);
				vertices.add(new float[] { x, y, z, w });
			} else {
				// Vertice hasn't a W component.
				vertices.add(new float[] { x, y, z, 0 });
			}
			return;
		}
		if (lineCommand.equalsIgnoreCase("vn") && lineSplit.length == 4) {
			float x = Float.parseFloat(lineSplit[1]);
			float y = Float.parseFloat(lineSplit[2]);
			float z = Float.parseFloat(lineSplit[3]);

			normals.add(new float[] { x, y, z });
			return;
		}
		if (lineCommand.equalsIgnoreCase("vt") && lineSplit.length == 3) {
			float x = Float.parseFloat(lineSplit[1]);
			float y = Float.parseFloat(lineSplit[2]);

			texCoords.add(new float[] { x, y });
			return;
		}

		if (lineCommand.equalsIgnoreCase("f") && lineSplit.length >= 4) {
			int point_amount = lineSplit.length - 1;
			int[][] pointBuffer = new int[point_amount][];
			for (int I = 1; I < lineSplit.length; I++) {
				String vertex = lineSplit[I];
				String[] vertexSplit = vertex.split("/");

				// Vertex
				if (vertexSplit.length == 1) {
					int vertice = Integer.parseInt(vertexSplit[0]);

					pointBuffer[I] = new int[] { vertice, 0, 0 };
				}
				// Vertex/UV
				if (vertexSplit.length == 2) {
					int vertice = Integer.parseInt(vertexSplit[0]);
					int uv = Integer.parseInt(vertexSplit[1]);

					pointBuffer[I] = new int[] { vertice, uv, 0 };
				}
				// Vertex/UV/Normal
				// Vertex//Normal
				if (vertexSplit.length == 3) {
					int vertice = Integer.parseInt(vertexSplit[0]);

					if (vertex.contains("//")) {
						// Vertex//Normal
						int normal = Integer.parseInt(vertexSplit[1]);

						pointBuffer[I] = new int[] { vertice, 0, normal };
					} else {
						// Vertex/UV/Normal
						int uv = Integer.parseInt(vertexSplit[1]);

						pointBuffer[I] = new int[] { vertice, uv, 0 };
					}
				}
			}

			faces.add(pointBuffer);
			return;
		}
	}
}
