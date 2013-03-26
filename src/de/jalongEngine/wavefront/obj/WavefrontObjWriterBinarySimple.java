/**
Copyright (c) 2013, Longor1996
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/


package de.jalongEngine.wavefront.obj;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Longor
 **/
public class WavefrontObjWriterBinarySimple {
    public DataOutputStream output;
    
    public WavefrontObjWriterBinarySimple(OutputStream output){
        this.output = new DataOutputStream(output);
    }
    
    public void write(List<float[]> vertices,List<float[]> normals,List<float[]> texCoords,List<int[][]> faces) throws IOException{
        
        // Write the amount of vertices:
        output.writeInt(vertices.size());
        
        // Write the amount of normals
        output.writeInt(normals.size());
        
        // Write the amount of texCoords
        output.writeInt(texCoords.size());
        
        // Write the amount of faces
        output.writeInt(faces.size());
        
        // Write the vertices. Each vertice is made up of 4 floats.
        for(int n = 0; n < vertices.size(); n++){
            float[] vertice = vertices.get(n);
            
            output.writeFloat(vertice[0]);
            output.writeFloat(vertice[1]);
            output.writeFloat(vertice[2]);
            output.writeFloat(vertice[3]);
        }
        
        // Write the normals. Each normal is made up of 3 floats.
        for(int n = 0; n < normals.size(); n++){
            float[] normal = normals.get(n);
            
            output.writeFloat(normal[0]);
            output.writeFloat(normal[1]);
            output.writeFloat(normal[2]);
        }
        
        // Write the texCoords. Each texCoord is made up of 3 floats.
        for(int n = 0; n < texCoords.size(); n++){
            float[] texCoord = texCoords.get(n);
            
            output.writeFloat(texCoord[0]);
            output.writeFloat(texCoord[1]);
        }
        
        // Write the faces. Each face is made up of N points.
        for(int n_face = 0; n_face < faces.size(); n_face++){
            int[][] face = faces.get(n_face);
            
            // Write the amount of points this face has
            output.writeInt(face.length);
            
            // Write the points. Each point is made up of 3 integers.
            for(int n_point = 0; n_point < face.length; n_point++){
                int[] point = face[n_point];
                
                output.writeInt(point[0]);
                output.writeInt(point[1]);
                output.writeInt(point[2]);
            }
        }
    }
    
}
