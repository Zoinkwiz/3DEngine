package com.base.game;

import com.base.core.CoreEngine;

public class Main {
	public static void main(String[] args) {
		CoreEngine engine = new CoreEngine(800, 600, 600, new TestGame());
		engine.createWindow("WAOW ENGINE");
		engine.start();
	}
}
