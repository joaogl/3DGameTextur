package com.game.engine.objlwjgl;

import com.game.engine.objbuild.Face;
import com.game.engine.objbuild.FaceVertex;

import java.util.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;

public class VBOFactory {

	public static VBO build(int textureID, ArrayList<Face> triangles) {

		if (triangles.size() <= 0) {
			throw new RuntimeException("Can not build a VBO if we have no triangles with which to build it.");
		}

		HashMap<FaceVertex, Integer> indexMap = new HashMap<FaceVertex, Integer>();
		int nextVertexIndex = 0;
		ArrayList<FaceVertex> faceVertexList = new ArrayList<FaceVertex>();
		for (Face face : triangles) {
			for (FaceVertex vertex : face.vertices) {
				if (!indexMap.containsKey(vertex)) {
					indexMap.put(vertex, nextVertexIndex++);
					faceVertexList.add(vertex);
				}
			}
		}

		int verticeAttributesCount = nextVertexIndex;
		int indicesCount = triangles.size() * 3;

		int numMIssingNormals = 0;
		int numMissingUV = 0;
		FloatBuffer verticeAttributes;
		System.err.println("VBOFactory.build: Creating buffer of size " + verticeAttributesCount + " vertices at " + VBO.ATTR_SZ_FLOATS + " floats per vertice for a total of " + (verticeAttributesCount * VBO.ATTR_SZ_FLOATS) + " floats.");
		verticeAttributes = BufferUtils.createFloatBuffer(verticeAttributesCount * VBO.ATTR_SZ_FLOATS);
		if (null == verticeAttributes) {
			System.err.println("VBOFactory.build: ERROR Unable to allocate verticeAttributes buffer of size " + (verticeAttributesCount * VBO.ATTR_SZ_FLOATS) + " floats.");
		}
		for (FaceVertex vertex : faceVertexList) {
			verticeAttributes.put(vertex.v.x);
			verticeAttributes.put(vertex.v.y);
			verticeAttributes.put(vertex.v.z);
			if (vertex.n == null) {
				verticeAttributes.put(1.0f);
				verticeAttributes.put(1.0f);
				verticeAttributes.put(1.0f);
				numMIssingNormals++;
			} else {
				verticeAttributes.put(vertex.n.x);
				verticeAttributes.put(vertex.n.y);
				verticeAttributes.put(vertex.n.z);
			}
			if (vertex.t == null) {
				verticeAttributes.put((float) Math.random());
				verticeAttributes.put((float) Math.random());
				numMissingUV++;
			} else {
				verticeAttributes.put(vertex.t.u);
				verticeAttributes.put(vertex.t.v);
			}
		}
		verticeAttributes.flip();

		System.err.println("Had " + numMIssingNormals + " missing normals and " + numMissingUV + " missing UV coords");

		IntBuffer indices;
		indices = BufferUtils.createIntBuffer(indicesCount);
		for (Face face : triangles) {
			for (FaceVertex vertex : face.vertices) {
				int index = indexMap.get(vertex);
				indices.put(index);
			}
		}
		indices.flip();

		IntBuffer verticeAttributesIDBuf = BufferUtils.createIntBuffer(1);

		ARBVertexBufferObject.glGenBuffersARB(verticeAttributesIDBuf);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, verticeAttributesIDBuf.get(0));
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, verticeAttributes, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

		IntBuffer indicesIDBuf = BufferUtils.createIntBuffer(1);
		ARBVertexBufferObject.glGenBuffersARB(indicesIDBuf);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indicesIDBuf.get(0));
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, indices, ARBVertexBufferObject.GL_STATIC_DRAW_ARB);

		verticeAttributes = null;
		indices = null;

		return new VBO(textureID, verticeAttributesIDBuf.get(0), indicesIDBuf.get(0), indicesCount);
	}
}
