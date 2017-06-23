package com.base.game;

import com.base.engine.core.CoreEngine;

/** Main class. Initialised and starts the CoreEngine**/
public class Main {
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(800, 600, 90, new TestGame());
		engine.createWindow("WAOW ENGINE");
		engine.start();
	}
}
