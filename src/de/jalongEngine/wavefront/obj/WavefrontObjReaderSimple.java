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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A simple Wavefront Object reader.
 * @author Longor
 **/
public class WavefrontObjReaderSimple implements Runnable{
    
    private Scanner scanner;
    public boolean isDoneReading;
    
    /**List of float{x,y,z,w} vertices.**/
    public ArrayList<float[]> vertices;
    /**List of float{x,y,z} normals.**/
    public ArrayList<float[]> normals;
    /**List of float{u,v} texture coordinates.**/
    public ArrayList<float[]> texCoords;
    
    /**List of:
     * int[AmountOfPointsOfFace][3]
     * 
     * int[?][0] = Vertice Index
     * int[?][1] = Texture Coordinate Index
     * int[?][2] = Normal Index
     **/
    public ArrayList<int[][]> faces;
    
    public WavefrontObjReaderSimple(InputStream in){
        scanner = new Scanner(in);
        isDoneReading = false;
        
        vertices = new ArrayList<float[]>(8);
        normals  = new ArrayList<float[]>(8);
        texCoords= new ArrayList<float[]>(8);
        faces    = new ArrayList<int[][]>(4);
        
        vertices.add(new float[]{0,0,0,1});
        normals.add(new float[]{0,1,0});
        texCoords.add(new float[]{0,0});
    }
    
    public void run(){
        
        String line = null;
        while(scanner.hasNextLine()){
            line = scanner.nextLine().trim();
            
            if(line.isEmpty())
                continue;
            
            if(line.length() < 4)
                continue;
            
            if(line.startsWith("#"))
                continue;
            
            if(line.indexOf(' ') == -1)
                continue;
            
            processLine(line);
        }
        
        scanner.close();
        isDoneReading = true;
    }
    
    private void processLine(String line) {
        String[] lineSplit = line.split(" ");
        String lineCommand = lineSplit[0];
        
        if(lineCommand.equalsIgnoreCase("v") && lineSplit.length >= 4){
            float x = Float.parseFloat(lineSplit[1]);
            float y = Float.parseFloat(lineSplit[2]);
            float z = Float.parseFloat(lineSplit[3]);
            
            if(lineSplit.length > 4){
                // Vertice has a W component.
                float w = Float.parseFloat(lineSplit[4]);
                vertices.add(new float[]{x,y,z,w});
            }else{
                // Vertice hasn't a W component.
                vertices.add(new float[]{x,y,z,0});
            }
            return;
        }
        if(lineCommand.equalsIgnoreCase("vn") && lineSplit.length == 4){
            float x = Float.parseFloat(lineSplit[1]);
            float y = Float.parseFloat(lineSplit[2]);
            float z = Float.parseFloat(lineSplit[3]);
            
            normals.add(new float[]{x,y,z});
            return;
        }
        if(lineCommand.equalsIgnoreCase("vt") && lineSplit.length == 3){
            float x = Float.parseFloat(lineSplit[1]);
            float y = Float.parseFloat(lineSplit[2]);
            
            texCoords.add(new float[]{x,y});
            return;
        }
        
        if(lineCommand.equalsIgnoreCase("f") && lineSplit.length >= 4){
            int point_amount = lineSplit.length - 1;
            int[][] pointBuffer = new int[point_amount][];
            for(int I = 1; I < lineSplit.length; I++){
                String vertex = lineSplit[I];
                String[] vertexSplit = vertex.split("/");
                
                // Vertex
                if(vertexSplit.length == 1){
                    int vertice = Integer.parseInt(vertexSplit[0]);
                    
                    pointBuffer[I] = new int[]{vertice,0,0};
                }
                // Vertex/UV
                if(vertexSplit.length == 2){
                    int vertice = Integer.parseInt(vertexSplit[0]);
                    int uv = Integer.parseInt(vertexSplit[1]);
                    
                    pointBuffer[I] = new int[]{vertice,uv,0};
                }
                // Vertex/UV/Normal
                // Vertex//Normal
                if(vertexSplit.length == 3){
                    int vertice = Integer.parseInt(vertexSplit[0]);
                    
                    if(vertex.contains("//")){
                        // Vertex//Normal
                        int normal = Integer.parseInt(vertexSplit[1]);
                        
                        pointBuffer[I] = new int[]{vertice,0,normal};
                    }else{
                        // Vertex/UV/Normal
                        int uv = Integer.parseInt(vertexSplit[1]);
                        int normal = Integer.parseInt(vertexSplit[2]);
                        
                        pointBuffer[I] = new int[]{vertice,uv,normal};
                    }
                }
            }
            
            faces.add(pointBuffer);
            return;
        }
    }
}
