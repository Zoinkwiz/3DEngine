package com.base.engine.generation;

import java.util.ArrayList;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

public class TerrainGeneration {

	private static float tileScale = 0.01f;
	private static float scaling;
	public static Mesh generatePerlin(int width, int length, float scalingCurves) {
		scaling = scalingCurves;
		Vertex[] vertices2 = new Vertex[(width)*(length)];
		float tileWidth = tileScale * width;
		float tileLength = tileScale* length;
		int countPos = 0;
		for(int x=0; x<width;x++) {
			for(int y=0;y<length;y++) {
				vertices2[countPos] =  new Vertex(new Vector3f(tileWidth*x, combineCurves(x*tileScale, y*tileScale), tileLength*y), new Vector2f((float)x/(float)width,(float)y/(float)length));
				countPos++;
			}
		}
		
		ArrayList<Integer> indicesList = new ArrayList<Integer>();
		
		for(int y=0;y<length-1;y++) {
			for(int x=0;x<width-1;x++) {
				indicesList.add(y+length*x);			
				indicesList.add(y+1+length*x);
				indicesList.add(length*(x+1)+y);
				
				indicesList.add(length*(x+1)+y+1);
				indicesList.add(length*(x+1)+y);
				indicesList.add(y+1+length*x);
			}
		}
		
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
//		float result = (float)(Math.sin(x/10f)*10*Math.sin(y/10f));
//		
//		result+= (float)(10*Math.cos(x/10f)*Math.cos(y/10f));
		return result*scaling;
	}
}
