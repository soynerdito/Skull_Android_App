package com.soynerdito.skullmaster;

import org.json.JSONException;
import org.json.JSONObject;

public class Command {
	private final String mCmd;

	public final static String BLINK_LEFT = "BL";
	public final static String BLINK_RIGHT = "BR";

	public Command(String command) {
		mCmd = command;
	}

	public JSONObject toJSon() throws JSONException {
		JSONObject object = new JSONObject();

		object.put("cmd", mCmd);

		return object;
	}
}
