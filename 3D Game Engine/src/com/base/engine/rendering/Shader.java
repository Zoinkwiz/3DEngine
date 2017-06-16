package com.base.engine.rendering;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;
import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.components.BaseLight;
import com.base.engine.components.DirectionalLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.rendering.resourceManagement.ShaderResource;
public class Shader {
	
	private static HashMap<String, ShaderResource> loadedShaders = new HashMap<String, ShaderResource>();
	
	private ShaderResource resource;
	private String fileName; //TODO: What's this for?
	
	public Shader(String fileName) {
		this.fileName = fileName;
		ShaderResource oldResource = loadedShaders.get(fileName);
		if(oldResource != null) {
			resource = oldResource;
			resource.addReference();
		} else {
			resource = new ShaderResource();
			
			String vertexShaderText = loadShader(fileName+".vs");
			String fragmentShaderText = loadShader(fileName+".fs");
			addVertexShader(vertexShaderText);
			addFragmentShader(fragmentShaderText);

			addAllAttributes(vertexShaderText);
			
			compileShader();
			
			addAllUniforms(vertexShaderText);
			addAllUniforms(fragmentShaderText);
			
			loadedShaders.put(fileName, resource);
		}
	}
	
	@Override
	protected void finalize() {
		if(resource.removeReference() && !fileName.isEmpty()) {
			loadedShaders.remove(fileName);
		}
}
	
	public void bind() {
		glUseProgram(resource.getProgram());
	}
	
	
	public void updateUniforms(Transform transform,  Material material, RenderingEngine renderingEngine) {
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f MVPMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		for(int i=0; i < resource.getUniformNames().size();i++) {
			String uniformName = resource.getUniformNames().get(i);
			String uniformType = resource.getUniformTypes().get(i);
			if(uniformType.equals("sampler2D")) {
				int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
				material.getTexture(uniformName).bind(samplerSlot);
				setUniformi(uniformName, samplerSlot);
			} else if(uniformName.startsWith("T_")) {
				if(uniformName.equals("T_MVP")) {
					setUniform(uniformName, MVPMatrix);
				} else if(uniformName.equals("T_model")) {
					setUniform(uniformName, worldMatrix);
				} else {
					throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
				} 
			} else if (uniformName.startsWith("R_")){
				String unprefixedUniformName  = uniformName.substring(2);
				if(uniformType.equals("vec3")) {
					setUniform(uniformName, renderingEngine.getVector3f(unprefixedUniformName));
				} else if(uniformType.equals("float")) {
					setUniformf(uniformName, renderingEngine.getFloat(unprefixedUniformName));
				} else if(uniformType.equals("DirectionalLight")) {
					setUniformDirectionalLight(uniformName, (DirectionalLight)renderingEngine.getActiveLight());
				} else if(uniformType.equals("PointLight")) {
					setUniformPointLight(uniformName, (PointLight)renderingEngine.getActiveLight());
				} else if(uniformType.equals("SpotLight")) {
					setUniformSpotLight(uniformName, (SpotLight)renderingEngine.getActiveLight());
				} else {
					renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
				}
			} else if(uniformName.startsWith("C_")) {
				if(uniformName.equals("C_eyePos")) {
					setUniform(uniformName,  renderingEngine.getMainCamera().getTransform().getTransformedPos());
				} else {
					throw new IllegalArgumentException(uniformName + " is not a valid component of Camera");
				}	
			} else {
				if(uniformType.equals("vec3")) {
					setUniform(uniformName, material.getVector3f(uniformName));
				} else if(uniformType.equals("float")) {
					setUniformf(uniformName, material.getFloat(uniformName));
				} else {
					throw new IllegalArgumentException(uniformType + " is not a supported type in Material");
				}
			} 
		}
	}
	
	private void addAllAttributes(String attributeText) {
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = attributeText.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while(attributeStartLocation != -1) { //While more uniforms are found
			if(!isIdentifier(attributeText, attributeStartLocation, ATTRIBUTE_KEYWORD)){
				continue;
			}
			
			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int end = attributeText.indexOf(";", begin);
			
			String attributeLine = attributeText.substring(begin, end).trim();
			String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length()).trim();
			
			setAttribLocation(attributeName, attribNumber);
			attribNumber++;
			
			attributeStartLocation = attributeText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
			
		}
	}
	
	private class GLSLStruct {
		public String name;
		public String type;
	}
	
	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String structText) {
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();
		
		final String STRUCT_KEYWORD = "struct";
		int structStartLocation = structText.indexOf(STRUCT_KEYWORD);
		while(structStartLocation != -1) { //While more uniforms are found
			if(!isIdentifier(structText, structStartLocation, STRUCT_KEYWORD)){
				continue;
			}
			
			
			int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
			int braceBegin = structText.indexOf("{", nameBegin);
			int braceEnd = structText.indexOf("}", braceBegin);

			String structName = structText.substring(nameBegin, braceBegin - 1).trim(); //TODO: Improve 
			ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();			
			
			int componentSemicolonPos = structText.indexOf(";", braceBegin);
			
			while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd) {
				
				int componentNameEnd = componentSemicolonPos + 1;
				
				while(Character.isWhitespace(structText.charAt(componentNameEnd - 1)) || structText.charAt(componentNameEnd - 1) == ';') {
					componentNameEnd--;
				}
				
				int componentNameStart = componentSemicolonPos;
				
				while(!Character.isWhitespace(structText.charAt(componentNameStart - 1))) {
					componentNameStart--;
				}
				
				int componentTypeEnd = componentNameStart; //TODO: Only supports 1 space
				
				while(Character.isWhitespace(structText.charAt(componentTypeEnd - 1))) {
					componentTypeEnd--;
				}
				
				int componentTypeStart = componentTypeEnd;
				
				while(!Character.isWhitespace(structText.charAt(componentTypeStart - 1))) {
					componentTypeStart--;
				}
				
				String componentName = structText.substring(componentNameStart, componentSemicolonPos);
				String componentType = structText.substring(componentTypeStart, componentTypeEnd);
				
				GLSLStruct glslStruct = new GLSLStruct();
				glslStruct.name = componentName;
				glslStruct.type = componentType;
				
				glslStructs.add(glslStruct);
				
				componentSemicolonPos = structText.indexOf(";", componentSemicolonPos + 1);
				
			
			}
			
			
			result.put(structName, glslStructs);
			
			structStartLocation = structText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
		}
		return result;
	}
	
	
	private void addAllUniforms(String shaderText) {
		
		HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);
		
		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while(uniformStartLocation != -1) { //While more uniforms are found
			if(!isIdentifier(shaderText, uniformStartLocation, UNIFORM_KEYWORD)){
				continue;
			}
			
			int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);
			
			String uniformLine = shaderText.substring(begin, end).trim();
			
			int whiteSpacePos = uniformLine.indexOf(' ');	 //TODO: Can still break for tab?
			String uniformName = uniformLine.substring(uniformLine.indexOf(" ") + 1, uniformLine.length()).trim();
			String uniformType = uniformLine.substring(0, whiteSpacePos).trim();
			
			resource.getUniformNames().add(uniformName);
			resource.getUniformTypes().add(uniformType); //Includes struct types here
			addUniform(uniformName, uniformType, structs);
			
			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
			
		}
	}
	
	private boolean isIdentifier(String text, int startLocation, String keyword) {
		if((startLocation != 0
		&& (Character.isWhitespace(text.charAt(startLocation - 1)) || text.charAt(startLocation - 1) == ';')
		&& (Character.isWhitespace(text.charAt(startLocation + keyword.length()))))){
			return true;
		}
		return false;
	}
	
	private void addUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs) {
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);
		
		if(structComponents != null) {
			addThis = false;
			
			for(GLSLStruct struct : structComponents) {
				addUniform(uniformName + "." + struct.name, struct.type, structs);
			}
		}
		
		if(!addThis) {
			return;
		}
		
		int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);
		
		if(uniformLocation == 0xFFFFFFFF) { //If invalid location
			System.err.println("Error: Could not find uniform " + uniformName);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		resource.getUniforms().put(uniformName, uniformLocation);
	}
	
	
	private void setAttribLocation(String attributeName, int location) {
		glBindAttribLocation(resource.getProgram(), location, attributeName); //THIS IS AS USING EARLY VERSION GSL

	}
	
	
	private void compileShader() {
		glLinkProgram(resource.getProgram());
		
		if(glGetProgrami(resource.getProgram(), GL_LINK_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
		
		glValidateProgram(resource.getProgram());
		
		if(glGetProgrami(resource.getProgram(), GL_VALIDATE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
	}
	
	private void addProgram(String text, int type) {
		int shader = glCreateShader(type);
		
		if(shader==0) {
			System.err.println("Shader creation failed: could not find valid memory location when making shader");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(resource.getProgram(), shader);
	}
	
	private void addVertexShader(String text) {
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	private void addGeometryShader(String text) {
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	private void addFragmentShader(String text) {
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	private static String loadShader(String fileName) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";
		
		try {
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;
			while((line = shaderReader.readLine()) != null) {
				if(line.startsWith(INCLUDE_DIRECTIVE)) {
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
				} else {
					shaderSource.append(line).append("\n");
				}
			}
			
			shaderReader.close();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
	

	public void setUniformi(String uniformName, int value) {
		glUniform1i(resource.getUniforms().get(uniformName), value);
		
	}	
	
	public void setUniformf(String uniformName, float value) {
		glUniform1f(resource.getUniforms().get(uniformName), value);
		
	}
	
	public void setUniform(String uniformName, Vector3f value) {
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
		
	}
	
	public void setUniform(String uniformName, Matrix4f value) {
		glUniformMatrix4fv(resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
	}
	
	
	public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
		setUniform(uniformName + ".colour", baseLight.getColour());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight) {
		setUniformBaseLight(uniformName + ".base", directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}	
	
	public void setUniformPointLight(String uniformName, PointLight pointLight) {
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getAttenuation().getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getAttenuation().getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getAttenuation().getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
	public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
		setUniformPointLight(uniformName + ".pointLight", (PointLight)spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());	
	}
	
}
