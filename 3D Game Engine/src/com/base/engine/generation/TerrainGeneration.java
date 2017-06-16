package com.base.engine.generation;

import java.util.ArrayList;

import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Vertex;

public class TerrainGeneration {

	private static float tileScale = 0.1f;
	public static Mesh generateSin(int height, int length) {
		Vertex[] vertices2 = new Vertex[(length)*(length)];
		float tileWidth = tileScale * length;
		
		for(int x=0; x<length;x++) {
			for(int y=0;y<length;y++) {
				vertices2[x*length+y] =  new Vertex(new Vector3f(tileWidth*x, combineCurves(x, y), tileWidth*y), new Vector2f(0.0f,0.0f));
			}
		}
		
		ArrayList<Integer> indicesList = new ArrayList<Integer>();
		
		for(int x=0;x<length-1;x++) {
			for(int y=0;y<length-1;y++) {
//				indicesList.add(y+length*x);
//				indicesList.add(length*(x+1)+y);
//				indicesList.add(y+1+length*x);
				
				indicesList.add(y+1+length*x);
				indicesList.add(length*(x+1)+y);
				indicesList.add(y+length*x);
				
//				indicesList.add(y+1+length*x);
//				indicesList.add(length*(x+1)+y);
//				indicesList.add(length*(x+1)+y+1);
				
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
	
	private static float combineCurves(int x, int y) {
		
		Perlin perlin = new Perlin();
		float result = (float)perlin.perlin(x/10.0f, y/10.0f, (y/10.0f));
//		float result = (float)(Math.sin(x/10f)*10*Math.sin(y/10f));
//		
//		result+= (float)(10*Math.cos(x/10f)*Math.cos(y/10f));
		return result*10;
	}
}
