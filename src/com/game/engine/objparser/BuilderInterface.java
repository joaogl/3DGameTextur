package com.game.engine.objparser;

public interface BuilderInterface {

    public final int NO_SMOOTHING_GROUP = 0;
    public final int EMPTY_VERTEX_VALUE = Integer.MIN_VALUE;
    public final int MTL_KA = 0;
    public final int MTL_KD = 1;
    public final int MTL_KS = 2;
    public final int MTL_TF = 3;
    public final int MTL_MAP_KA = 0;
    public final int MTL_MAP_KD = 1;
    public final int MTL_MAP_KS = 2;
    public final int MTL_MAP_NS = 3;
    public final int MTL_MAP_D = 4;
    public final int MTL_DECAL = 5;
    public final int MTL_DISP = 6;
    public final int MTL_BUMP = 7;
    public final int MTL_REFL_TYPE_UNKNOWN = -1;
    public final int MTL_REFL_TYPE_SPHERE = 0;
    public final int MTL_REFL_TYPE_CUBE_TOP = 1;
    public final int MTL_REFL_TYPE_CUBE_BOTTOM = 2;
    public final int MTL_REFL_TYPE_CUBE_FRONT = 3;
    public final int MTL_REFL_TYPE_CUBE_BACK = 4;
    public final int MTL_REFL_TYPE_CUBE_LEFT = 5;
    public final int MTL_REFL_TYPE_CUBE_RIGHT = 6;

    public void setObjFilename(String filename);

    public void addVertexGeometric(float x, float y, float z);

    public void addVertexTexture(float u, float v);

    public void addVertexNormal(float x, float y, float z);

    public void addPoints(int values[]);

    public void addLine(int values[]);

    public void addFace(int vertexIndices[]);

    public void addObjectName(String name);

    public void addMapLib(String[] names);

    public void setCurrentGroupNames(String[] names);

    public void setCurrentSmoothingGroup(int groupNumber);

    public void setCurrentUseMap(String name);

    public void setCurrentUseMaterial(String name);

    public void newMtl(String name);

    public void setXYZ(int type, float x, float y, float z);

    public void setRGB(int type, float r, float g, float b);

    public void setIllum(int illumModel);

    public void setD(boolean halo, float factor);

    public void setNs(float exponent);

    public void setSharpness(float value);

    public void setNi(float opticalDensity);

    public void setMapDecalDispBump(int type, String filename);

    public void setRefl(int type, String filename);

    public void doneParsingMaterial();

    public void doneParsingObj(String filename);
}