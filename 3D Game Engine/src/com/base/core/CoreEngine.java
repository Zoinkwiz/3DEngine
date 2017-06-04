package com.base.core;

import com.base.rendering.Window;

//import static org.lwjgl.opengl.GL11.glClearColor;

//import org.lwjgl.opengl.GL;

public class CoreEngine {
	private boolean isRunning;
	private Game game;
	private RenderingEngine renderingEngine;
	private int width;
	private int height;
	private double frameTime;
	
	public CoreEngine(int width, int height, double frameRate, Game game) {
		this.game = game;
		this.isRunning = false;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0/frameRate;
	}

	public void createWindow(String title) {
		Window.createWindow(width, height, title);
		this.renderingEngine = new RenderingEngine();
	}
	
	public void start() {
		if(isRunning){
			return;
		}
		run();
	}
	
	public void stop() {
		if(!isRunning){
			return;
		}
		isRunning = false;
		
	}
	
	private void run() {
		isRunning = true;
		int frames = 0;
		long frameCounter = 0;
		
		game.init();
		
		//final double frameTime = 1.0 / FRAME_CAP;
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(isRunning){
			boolean render = false;
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			while(unprocessedTime > frameTime){
				render = true;
				unprocessedTime -=frameTime;
				
				//if closed, stop
				if(Window.closed()){
					stop();
				}
				
				Input.update();
				game.input((float)frameTime);
				renderingEngine.input((float)frameTime);
				
				game.update((float)frameTime);
				
				if(frameCounter >= Time.SECOND) {
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			
			if(render) {
				renderingEngine.render(game.getRootObject());
				Window.render();
				frames++;
			} else {
				try {
				Thread.sleep(1);
				} catch(InterruptedException e){
					//TODO AUto-generated catch block
					e.printStackTrace();
				}
			}
		}
		cleanUp();
	}
	
	

	private void cleanUp() {
		Window.dispose();
	}
}
