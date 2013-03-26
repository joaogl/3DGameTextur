package com.game.engine.objbuild;

import com.game.engine.objparser.BuilderInterface;

import java.util.*;

public class Build implements BuilderInterface {

	public String objFilename = null;
	public ArrayList<VertexGeometric> verticesG = new ArrayList<VertexGeometric>();
	public ArrayList<VertexTexture> verticesT = new ArrayList<VertexTexture>();
	public ArrayList<VertexNormal> verticesN = new ArrayList<VertexNormal>();
	HashMap<String, FaceVertex> faceVerticeMap = new HashMap<String, FaceVertex>();
	public ArrayList<FaceVertex> faceVerticeList = new ArrayList<FaceVertex>();
	public ArrayList<Face> faces = new ArrayList<Face>();
	public HashMap<Integer, ArrayList<Face>> smoothingGroups = new HashMap<Integer, ArrayList<Face>>();
	private int currentSmoothingGroupNumber = NO_SMOOTHING_GROUP;
	private ArrayList<Face> currentSmoothingGroup = null;
	public HashMap<String, ArrayList<Face>> groups = new HashMap<String, ArrayList<Face>>();
	private ArrayList<String> currentGroups = new ArrayList<String>();
	private ArrayList<ArrayList<Face>> currentGroupFaceLists = new ArrayList<ArrayList<Face>>();
	public String objectName = null;
	private Material currentMaterial = null;
	private Material currentMap = null;
	public HashMap<String, Material> materialLib = new HashMap<String, Material>();
	private Material currentMaterialBeingParsed = null;
	public HashMap<String, Material> mapLib = new HashMap<String, Material>();
	@SuppressWarnings("unused")
	private Material currentMapBeingParsed = null;
	public int faceTriCount = 0;
	public int faceQuadCount = 0;
	public int facePolyCount = 0;
	public int faceErrorCount = 0;

	public Build() {
	}

	public void setObjFilename(String filename) {
		this.objFilename = filename;
	}

	public void addVertexGeometric(float x, float y, float z) {
		verticesG.add(new VertexGeometric(x, y, z));
	}

	public void addVertexTexture(float u, float v) {
		verticesT.add(new VertexTexture(u, v));
	}

	public void addVertexNormal(float x, float y, float z) {
		verticesN.add(new VertexNormal(x, y, z));
	}

	public void addPoints(int[] values) {
		System.err.println("Build.addPoints: @TODO: Got " + values.length + " points in builder, ignoring");
	}

	public void addLine(int[] values) {
		System.err.println("Build.addLine: @TODO: Got a line of " + values.length + " segments in builder, ignoring");
	}

	public void addFace(int[] vertexIndices) {
		Face face = new Face();

		face.material = currentMaterial;
		face.map = currentMap;

		int loopi = 0;
		while (loopi < vertexIndices.length) {

			FaceVertex fv = new FaceVertex();
			int vertexIndex;
			vertexIndex = vertexIndices[loopi++];
			if (vertexIndex < 0) {
				vertexIndex = vertexIndex + verticesG.size();
			}
			if (((vertexIndex - 1) >= 0) && ((vertexIndex - 1) < verticesG.size())) {
				fv.v = verticesG.get(vertexIndex - 1);
			} else {
				System.err.println("Build.addFace: ERROR Index for geometric vertex=" + vertexIndex + " is out of the current range of geometric vertex values 1 to " + verticesG.size() + ", ignoring");
			}

			vertexIndex = vertexIndices[loopi++];
			if (vertexIndex != EMPTY_VERTEX_VALUE) {
				if (vertexIndex < 0) {
					vertexIndex = vertexIndex + verticesT.size();
				}
				if (((vertexIndex - 1) >= 0) && ((vertexIndex - 1) < verticesT.size())) {
					fv.t = verticesT.get(vertexIndex - 1);
				} else {
					System.err.println("Build.addFace: ERROR Index for texture vertex=" + vertexIndex + " is out of the current range of texture vertex values 1 to " + verticesT.size() + ", ignoring");
				}
			}

			vertexIndex = vertexIndices[loopi++];
			if (vertexIndex != EMPTY_VERTEX_VALUE) {
				if (vertexIndex < 0) {
					vertexIndex = vertexIndex + verticesN.size();
				}
				if (((vertexIndex - 1) >= 0) && ((vertexIndex - 1) < verticesN.size())) {
					fv.n = verticesN.get(vertexIndex - 1);
				} else {
					System.err.println("Build.addFace: ERROR Index for vertex normal=" + vertexIndex + " is out of the current range of vertex normal values 1 to " + verticesN.size() + ", ignoring");
				}
			}

			if (fv.v == null) {
				System.err.println("Build.addFace: ERROR Can't add vertex to face with missing vertex!  Throwing away face.");
				faceErrorCount++;
				return;
			}

			String key = fv.toString();
			FaceVertex fv2 = faceVerticeMap.get(key);
			if (null == fv2) {
				faceVerticeMap.put(key, fv);
				fv.index = faceVerticeList.size();
				faceVerticeList.add(fv);
			} else {
				fv = fv2;
			}

			face.add(fv);
		}
		if (currentSmoothingGroup != null) {
			currentSmoothingGroup.add(face);
		}

		if (currentGroupFaceLists.size() > 0) {
			for (loopi = 0; loopi < currentGroupFaceLists.size(); loopi++) {
				currentGroupFaceLists.get(loopi).add(face);
			}
		}

		faces.add(face);

		if (face.vertices.size() == 3) {
			faceTriCount++;
		} else if (face.vertices.size() == 4) {
			faceQuadCount++;
		} else {
			facePolyCount++;
		}
	}

	public void setCurrentGroupNames(String[] names) {
		currentGroups.clear();
		currentGroupFaceLists.clear();
		if (null == names) {
			return;
		}
		for (int loopi = 0; loopi < names.length; loopi++) {
			String group = names[loopi].trim();
			currentGroups.add(group);
			if (null == groups.get(group)) {
				groups.put(group, new ArrayList<Face>());
			}
			currentGroupFaceLists.add(groups.get(group));
		}
	}

	public void addObjectName(String name) {
		this.objectName = name;
	}

	public void setCurrentSmoothingGroup(int groupNumber) {
		currentSmoothingGroupNumber = groupNumber;
		if (currentSmoothingGroupNumber == NO_SMOOTHING_GROUP) {
			return;
		}
		if (null == smoothingGroups.get(currentSmoothingGroupNumber)) {
			currentSmoothingGroup = new ArrayList<Face>();
			smoothingGroups.put(currentSmoothingGroupNumber, currentSmoothingGroup);
		}
	}

	public void addMapLib(String[] names) {
		if (null == names) {
			System.err.println("Build.addMapLib: @TODO: ERROR! Got a maplib line with null names array - blank group line? (i.e. \"g\\n\" ?)");
			return;
		}
		if (names.length == 1) {
			System.err.println("Build.addMapLib: @TODO: Got a maplib line with one name=|" + names[0] + "|");
			return;
		}
		System.err.println("Build.addMapLib: @TODO: Got a maplib line;");
		for (int loopi = 0; loopi < names.length; loopi++) {
			System.err.println("        names[" + loopi + "] = |" + names[loopi] + "|");
		}
	}

	public void setCurrentUseMap(String name) {
		currentMap = mapLib.get(name);
	}

	public void setCurrentUseMaterial(String name) {
		currentMaterial = materialLib.get(name);
	}

	public void newMtl(String name) {
		currentMaterialBeingParsed = new Material(name);
		materialLib.put(name, currentMaterialBeingParsed);
	}

	public void setXYZ(int type, float x, float y, float z) {
		ReflectivityTransmiss rt = currentMaterialBeingParsed.ka;
		if (type == MTL_KD) {
			rt = currentMaterialBeingParsed.kd;
		} else if (type == MTL_KS) {
			rt = currentMaterialBeingParsed.ks;
		} else if (type == MTL_TF) {
			rt = currentMaterialBeingParsed.tf;
		}

		rt.rx = x;
		rt.gy = y;
		rt.bz = z;
		rt.isXYZ = true;
		rt.isRGB = false;
	}

	public void setRGB(int type, float r, float g, float b) {
		ReflectivityTransmiss rt = currentMaterialBeingParsed.ka;
		if (type == MTL_KD) {
			rt = currentMaterialBeingParsed.kd;
		} else if (type == MTL_KS) {
			rt = currentMaterialBeingParsed.ks;
		} else if (type == MTL_TF) {
			rt = currentMaterialBeingParsed.tf;
		}

		rt.rx = r;
		rt.gy = g;
		rt.bz = b;
		rt.isRGB = true;
		rt.isXYZ = false;
	}

	public void setIllum(int illumModel) {
		currentMaterialBeingParsed.illumModel = illumModel;
	}

	public void setD(boolean halo, float factor) {
		currentMaterialBeingParsed.dHalo = halo;
		currentMaterialBeingParsed.dFactor = factor;
		System.err.println("Build.setD: @TODO: got a setD call!");
	}

	public void setNs(float exponent) {
		currentMaterialBeingParsed.nsExponent = exponent;
		System.err.println("Build.setNs: @TODO: got a setNs call!");
	}

	public void setSharpness(float value) {
		currentMaterialBeingParsed.sharpnessValue = value;
	}

	public void setNi(float opticalDensity) {
		currentMaterialBeingParsed.niOpticalDensity = opticalDensity;
	}

	public void setMapDecalDispBump(int type, String filename) {
		if (type == MTL_MAP_KA) {
			currentMaterialBeingParsed.mapKaFilename = filename;
		} else if (type == MTL_MAP_KD) {
			currentMaterialBeingParsed.mapKdFilename = filename;
		} else if (type == MTL_MAP_KS) {
			currentMaterialBeingParsed.mapKsFilename = filename;
		} else if (type == MTL_MAP_NS) {
			currentMaterialBeingParsed.mapNsFilename = filename;
		} else if (type == MTL_MAP_D) {
			currentMaterialBeingParsed.mapDFilename = filename;
		} else if (type == MTL_DECAL) {
			currentMaterialBeingParsed.decalFilename = filename;
		} else if (type == MTL_DISP) {
			currentMaterialBeingParsed.dispFilename = filename;
		} else if (type == MTL_BUMP) {
			currentMaterialBeingParsed.bumpFilename = filename;
		}
	}

	public void setRefl(int type, String filename) {
		currentMaterialBeingParsed.reflType = type;
		currentMaterialBeingParsed.reflFilename = filename;
	}

	public void doneParsingMaterial() {
		currentMaterialBeingParsed = null;
	}

	public void doneParsingObj(String filename) {
		System.err.println("Build.doneParsing: Loaded filename '" + filename + "' with " + verticesG.size() + " verticesG, " + verticesT.size() + " verticesT, " + verticesN.size() + " verticesN and " + faces.size() + " faces, of which " + faceTriCount + " triangles, " + faceQuadCount + " quads, and " + facePolyCount + " with more than 4 points, and faces with errors " + faceErrorCount);
	}
}