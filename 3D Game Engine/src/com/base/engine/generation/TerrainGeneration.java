package com.base.engine.generation;

import java.util.ArrayList;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

public class TerrainGeneration {
	public static final int PERLIN = 0;
	public static final int BILLOW = 1;
	public static final int RIDGED = 2;
	
	private static float scaling;
	public static Mesh generatePerlinNoise(int startX, int endX, int startZ, int endZ, float scalingCurves, int divisions, int type) {
		int width = Math.abs(startX - endX);
		int length = Math.abs(startZ - endZ);
		
		int divisionsX = divisions;
		int divisionsZ = divisions; 
		float yPosMax = -Integer.MAX_VALUE;
		float yPosMin = -Integer.MAX_VALUE;
		scaling = scalingCurves;
		Vertex[] vertices2 = new Vertex[(divisionsX)*(divisionsZ)];
		float tileSizeX = (float)width/(float)divisions;
		float tileSizeZ = (float)length/(float)divisions;
		int countPos = 0;
		for(int x=0; x<divisionsX;x++) {
			for(int z=0;z<divisionsZ;z++) {
				Vector2f colour;
				float yPos = combineCurves((float)x*0.01f, (float)z*0.01f);
				if(type==BILLOW) {
					yPos = Math.abs(yPos);
				} else if(type==RIDGED) {
					yPos = 1.0f-Math.abs(yPos);
				}
				
				if(yPos<(-500f)) {
					colour = new Vector2f(0f, 0f);
				} else if(yPos >= (-500f)) {
					colour = new Vector2f(1f, 0f);
				} else {
					System.out.println("WOW");
					colour = new Vector2f(0.5f, 0f);
				}
				
				if(yPosMax < yPos) {
					yPosMax = yPos;
				}
				if(yPosMin > yPos) {
					yPosMin = yPos;
				}
				
				
				vertices2[countPos] =  new Vertex(new Vector3f(startX+tileSizeX*x, yPos, startZ+tileSizeZ*z), colour);
				countPos++;
			}
		}
		System.out.println(yPosMin);
		System.out.println(yPosMax);
		
		ArrayList<Integer> indicesList = generateIndices(divisionsX, divisionsZ);
		
		int size = indicesList.size();
		int[] indices2 = new int[size];
		Integer[] temp = indicesList.toArray(new Integer[size]);
		for (int n = 0; n < size; ++n) {
		    indices2[n] = temp[n];
		}

		return new Mesh(vertices2, indices2, true);
	}
	
	private static float combineCurves(float x, float y) {
		
		Perlin perlin = new Perlin();
		float result = (float)perlin.perlin(x, y, (0.0f));
//		float result = (float)(Math.sin(x)*10*Math.sin(y));
		
//		result+= (float)(10*Math.cos(x/10f)*Math.cos(y/10f));
		return result*scaling;
	}
	
	private static ArrayList<Integer> generateIndices(int divisionsX, int divisionsZ) {	
	
		ArrayList<Integer> indicesList = new ArrayList<Integer>();
		
		for(int z=0;z<divisionsZ-1;z++) {
			for(int x=0;x<divisionsX-1;x++) {
				indicesList.add(z+divisionsZ*x);			
				indicesList.add(z+1+divisionsZ*x);
				indicesList.add(divisionsZ*(x+1)+z);
				
				indicesList.add(divisionsZ*(x+1)+z+1);
				indicesList.add(divisionsZ*(x+1)+z);
				indicesList.add(z+1+divisionsZ*x);
			}
		}
		return indicesList;
	}
}
