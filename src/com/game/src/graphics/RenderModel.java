package com.game.src.graphics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.game.engine.objbuild.Build;
import com.game.engine.objbuild.Face;
import com.game.engine.objbuild.FaceVertex;
import com.game.engine.objbuild.Material;
import com.game.engine.objlwjgl.Scene;
import com.game.engine.objlwjgl.TextureLoader;
import com.game.engine.objlwjgl.VBO;
import com.game.engine.objlwjgl.VBOFactory;
import com.game.engine.objparser.Parse;

public class RenderModel {
	Scene scene = null;

	public RenderModel(String filename, String defaultTextureMaterial) {
		scene = new Scene();

		System.err.println("Parsing WaveFront OBJ file");
		Build builder = new Build();
		@SuppressWarnings("unused")
		Parse obj = null;
		try {
			obj = new Parse(builder, filename);
		} catch (java.io.FileNotFoundException e) {
			System.err.println("Exception loading object!  e=" + e);
			e.printStackTrace();
		} catch (java.io.IOException e) {
			System.err.println("Exception loading object!  e=" + e);
			e.printStackTrace();
		}
		System.err.println("Done parsing WaveFront OBJ file");

		System.err.println("Splitting OBJ file faces into list of faces per material");
		ArrayList<ArrayList<Face>> facesByTextureList = createFaceListsByMaterial(builder);
		System.err.println("Done splitting OBJ file faces into list of faces per material, ended up with " + facesByTextureList.size() + " lists of faces.");

		System.err.println("Loading default texture =" + defaultTextureMaterial);
		TextureLoader textureLoader = new TextureLoader();
		int defaultTextureID = setUpDefaultTexture(textureLoader, defaultTextureMaterial);
		System.err.println("Done loading default texture =" + defaultTextureMaterial);

		int currentTextureID = -1;
		for (ArrayList<Face> faceList : facesByTextureList) {
			if (faceList.isEmpty()) {
				System.err.println("ERROR: got an empty face list.  That shouldn't be possible.");
				continue;
			}
			System.err.println("Getting material " + faceList.get(0).material);
			currentTextureID = getMaterialID(faceList.get(0).material, defaultTextureID, builder, textureLoader);
			System.err.println("Splitting any quads and throwing any faces with > 4 vertices.");
			ArrayList<Face> triangleList = splitQuads(faceList);
			System.err.println("Calculating any missing vertex normals.");
			calcMissingVertexNormals(triangleList);
			System.err.println("Ready to build VBO of " + triangleList.size() + " triangles");
			;

			if (triangleList.size() <= 0) {
				continue;
			}
			System.err.println("Building VBO");

			VBO vbo = VBOFactory.build(currentTextureID, triangleList);

			System.err.println("Adding VBO with text id " + currentTextureID + ", with " + triangleList.size() + " triangles to scene.");
			scene.addVBO(vbo);
		}
		System.err.println("Finally ready to draw things.");
	}

	private static ArrayList<ArrayList<Face>> createFaceListsByMaterial(Build builder) {
		ArrayList<ArrayList<Face>> facesByTextureList = new ArrayList<ArrayList<Face>>();
		Material currentMaterial = null;
		ArrayList<Face> currentFaceList = new ArrayList<Face>();
		for (Face face : builder.faces) {
			if (face.material != currentMaterial) {
				if (!currentFaceList.isEmpty()) {
					System.err.println("Adding list of " + currentFaceList.size() + " triangle faces with material " + currentMaterial + "  to our list of lists of faces.");
					facesByTextureList.add(currentFaceList);
				}
				System.err.println("Creating new list of faces for material " + face.material);
				currentMaterial = face.material;
				currentFaceList = new ArrayList<Face>();
			}
			currentFaceList.add(face);
		}
		if (!currentFaceList.isEmpty()) {
			System.err.println("Adding list of " + currentFaceList.size() + " triangle faces with material " + currentMaterial + "  to our list of lists of faces.");
			facesByTextureList.add(currentFaceList);
		}
		return facesByTextureList;
	}

	private static void calcMissingVertexNormals(ArrayList<Face> triangleList) {
		for (Face face : triangleList) {
			face.calculateTriangleNormal();
			for (int loopv = 0; loopv < face.vertices.size(); loopv++) {
				FaceVertex fv = face.vertices.get(loopv);
				if (face.vertices.get(0).n == null) {
					FaceVertex newFv = new FaceVertex();
					newFv.v = fv.v;
					newFv.t = fv.t;
					newFv.n = face.faceNormal;
					face.vertices.set(loopv, newFv);
				}
			}
		}
	}

	private static int setUpDefaultTexture(TextureLoader textureLoader, String defaultTextureMaterial) {
		int defaultTextureID = -1;
		try {
			defaultTextureID = textureLoader.load(defaultTextureMaterial, true);
		} catch (IOException ex) {
			Logger.getLogger(RenderModel.class.getName()).log(Level.SEVERE, null, ex);
			System.err.println("ERROR: Got an exception trying to load default texture material = " + defaultTextureMaterial + " , ex=" + ex);
			ex.printStackTrace();
		}
		System.err.println("INFO:  default texture ID = " + defaultTextureID);
		return defaultTextureID;
	}

	private static int getMaterialID(Material material, int defaultTextureID, Build builder, TextureLoader textureLoader) {
		int currentTextureID;
		if (material == null) {
			currentTextureID = defaultTextureID;
		} else if (material.mapKdFilename == null) {
			currentTextureID = defaultTextureID;
		} else {
			try {
				File objFile = new File(builder.objFilename);
				File mapKdFile = new File(objFile.getParent(), material.mapKdFilename);
				System.err.println("Trying to load  " + mapKdFile.getAbsolutePath());
				currentTextureID = textureLoader.load(mapKdFile.getAbsolutePath(), true);
			} catch (IOException ex) {
				Logger.getLogger(RenderModel.class.getName()).log(Level.SEVERE, null, ex);
				System.err.println("ERROR: Got an exception trying to load  texture material = " + material.mapKdFilename + " , ex=" + ex);
				ex.printStackTrace();
				System.err.println("ERROR: Using default texture ID = " + defaultTextureID);
				currentTextureID = defaultTextureID;
			}
		}
		return currentTextureID;
	}

	private static ArrayList<Face> splitQuads(ArrayList<Face> faceList) {
		ArrayList<Face> triangleList = new ArrayList<Face>();
		int countTriangles = 0;
		int countQuads = 0;
		int countNGons = 0;
		for (Face face : faceList) {
			if (face.vertices.size() == 3) {
				countTriangles++;
				triangleList.add(face);
			} else if (face.vertices.size() == 4) {
				countQuads++;
				FaceVertex v1 = face.vertices.get(0);
				FaceVertex v2 = face.vertices.get(1);
				FaceVertex v3 = face.vertices.get(2);
				FaceVertex v4 = face.vertices.get(3);
				Face f1 = new Face();
				f1.map = face.map;
				f1.material = face.material;
				f1.add(v1);
				f1.add(v2);
				f1.add(v3);
				triangleList.add(f1);
				Face f2 = new Face();
				f2.map = face.map;
				f2.material = face.material;
				f2.add(v1);
				f2.add(v3);
				f2.add(v4);
				triangleList.add(f2);
			} else {
				countNGons++;
			}
		}
		int texturedCount = 0;
		int normalCount = 0;
		for (Face face : triangleList) {
			if ((face.vertices.get(0).n != null) && (face.vertices.get(1).n != null) && (face.vertices.get(2).n != null)) {
				normalCount++;
			}
			if ((face.vertices.get(0).t != null) && (face.vertices.get(1).t != null) && (face.vertices.get(2).t != null)) {
				texturedCount++;
			}
		}
		System.err.println("Building VBO, originally " + faceList.size() + " faces, of which originally " + countTriangles + " triangles, " + countQuads + " quads,  and  " + countNGons + " n-polygons with more than 4 vertices that were dropped.");
		System.err.println("Triangle list has " + triangleList.size() + " rendered triangles of which " + normalCount + " have normals for all vertices and " + texturedCount + " have texture coords for all vertices.");
		return triangleList;
	}

	public void render() {
		scene.render();
	}

}
