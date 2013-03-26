package com.game.engine.binarydatatree;

import java.io.IOException;

public interface IBDTNodeWriter {
	public abstract void writeNode(BDTNode node) throws IOException;
}
