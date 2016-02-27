package com.breakout.game;

import java.io.Serializable;
import javafx.scene.input.KeyCode;

public class RequestMessage implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	public final KeyCode[]keys;

	public RequestMessage(KeyCode[] keys) 
	{
		this.keys = keys;
	}
	
	

}
