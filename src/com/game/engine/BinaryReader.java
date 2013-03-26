package com.game.engine;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class BinaryReader {

	public DataInputStream input;
    
    public BinaryReader(InputStream input){
        this.input = new DataInputStream(input);
    }
    
    public void read(List<float[]> vertices,List<float[]> normals,List<float[]> texCoords,List<int[][]> faces) throws IOException{
        
        int vertices_len = input.readInt();
        int normals_len = input.readInt();
        int texcoords_len = input.readInt();
        int faces_len = input.readInt();
        
        for(int n = 0; n < vertices_len; n++){
            float x = input.readFloat();
            float y = input.readFloat();
            float z = input.readFloat();
            float w = input.readFloat();
            
            vertices.add(new float[]{x,y,z,w});
        }
        
        for(int n = 0; n < normals_len; n++){
            float x = input.readFloat();
            float y = input.readFloat();
            float z = input.readFloat();
            
            normals.add(new float[]{x,y,z});
        }
        
        for(int n = 0; n < texcoords_len; n++){
            float x = input.readFloat();
            float y = input.readFloat();
            
            texCoords.add(new float[]{x,y});
        }
        
        for(int n = 0; n < faces_len; n++){
            int points_len = input.readInt();
            int[][] face = new int[points_len][];
            
            for(int n_point = 0; n_point < points_len; n_point++){
                int[] point = new int[3];
                
                point[0] = input.readInt();
                point[1] = input.readInt();
                point[2] = input.readInt();
                
                face[n_point] = point;
            }
            
            faces.add(face);
        }
    }
	
}
