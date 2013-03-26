package com.game.engine;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Longor
 **/
public class ObjWriter {
	public DataOutputStream output;

	public ObjWriter(OutputStream output) {
		this.output = new DataOutputStream(output);
	}

	public void write(List<float[]> vertices, List<float[]> normals, List<float[]> texCoords, List<int[][]> faces) throws IOException {

		// Write the amount of vertices:
		output.writeInt(vertices.size());

		// Write the amount of normals
		output.writeInt(normals.size());

		// Write the amount of texCoords
		output.writeInt(texCoords.size());

		// Write the amount of faces
		output.writeInt(faces.size());

		// Write the vertices. Each vertice is made up of 4 floats.
		for (int n = 0; n < vertices.size(); n++) {
			float[] vertice = vertices.get(n);

			output.writeFloat(vertice[0]);
			output.writeFloat(vertice[1]);
			output.writeFloat(vertice[2]);
			output.writeFloat(vertice[3]);
		}

		// Write the normals. Each normal is made up of 3 floats.
		for (int n = 0; n < normals.size(); n++) {
			float[] normal = normals.get(n);

			output.writeFloat(normal[0]);
			output.writeFloat(normal[1]);
			output.writeFloat(normal[2]);
		}

		// Write the texCoords. Each texCoord is made up of 3 floats.
		for (int n = 0; n < texCoords.size(); n++) {
			float[] texCoord = texCoords.get(n);

			output.writeFloat(texCoord[0]);
			output.writeFloat(texCoord[1]);
		}

		// Write the faces. Each face is made up of N points.
		for (int n_face = 0; n_face < faces.size(); n_face++) {
			int[][] face = faces.get(n_face);

			// Write the amount of points this face has
			output.writeInt(face.length);

			// Write the points. Each point is made up of 3 integers.
			for (int n_point = 0; n_point < face.length; n_point++) {
				int[] point = face[n_point];

				output.writeInt(point[0]);
				output.writeInt(point[1]);
				output.writeInt(point[2]);
			}
		}
	}

}