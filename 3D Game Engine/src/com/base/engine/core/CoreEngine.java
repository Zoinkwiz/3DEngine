package com.base.engine.core;

import com.base.engine.core.input.Input;
import com.base.engine.rendering.RenderingEngine;
import com.base.engine.rendering.Window;

/** Core Engine contains the main loop of the game. A game is passed in, and run. **/
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
		game.setEngine(this);
	}
	
	/** Creates a graphical window through**/
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
		
		game.init();
		
		//final double frameTime = 1.0 / FRAME_CAP;
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		double frameCounter = 0;
		int frames = 0;
		
		while(isRunning){
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;		
			if(frameCounter >= 1.0) {
				System.out.println("Frames are : " + frames);
				frames = 0;
				frameCounter = 0;
			}
			
			while(unprocessedTime > frameTime){
				render = true;
				
				//if closed, stop
				if(Window.closed()){
					stop();
				}
				
				Input.update();
				
				game.input((float)frameTime);
				game.update((float)frameTime);
				
				unprocessedTime -=frameTime;
				
			}
			
			if(render) {
				game.render(renderingEngine);
				Window.render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch(InterruptedException e){
					e.printStackTrace();
				}
			}
		}
		cleanUp();
	}
	

	private void cleanUp() {
		Window.dispose();
	}
	
	public RenderingEngine getRenderingEngine() {
		return renderingEngine;
	}
	
}
