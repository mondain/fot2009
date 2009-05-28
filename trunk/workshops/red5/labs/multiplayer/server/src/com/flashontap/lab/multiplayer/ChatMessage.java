package com.flashontap.lab.multiplayer;

import java.io.Serializable;

import org.red5.io.amf3.IDataInput;
import org.red5.io.amf3.IDataOutput;
import org.red5.io.amf3.IExternalizable;

/**
 * 
 * Represents a chat message going to or from a flash client.
 * 
 */

public class ChatMessage implements IExternalizable, Serializable {

	private static final long serialVersionUID = 20080912L;

	private String clientId;

	private long timeStamp;

	private String color = "#000000";

	private String userName;

	private String text;

	public String getClientId() {

		return clientId;

	}

	public void setClientId(String clientId) {

		this.clientId = clientId;

	}

	public long getTimeStamp() {

		return timeStamp;

	}

	public void setTimeStamp(long timeStamp) {

		this.timeStamp = timeStamp;

	}

	public String getColor() {

		return color;

	}

	public void setColor(String color) {

		this.color = color;

	}

	public String getUserName() {

		return userName;

	}

	public void setUserName(String userName) {

		this.userName = userName;

	}

	public String getText() {

		return text;

	}

	public void setText(String text) {

		this.text = text;

	}

	/**
	 * 
	 * Deserializes the client state of an instance.
	 * 
	 */

	public void readExternal(IDataInput in) {

		clientId = in.readUTF();

		timeStamp = in.readUnsignedInt();

		color = in.readUTF();

		userName = in.readUTF();

		text = in.readUTF();

	}

	/**
	 * 
	 * Serializes the server state of an instance.
	 * 
	 */

	public void writeExternal(IDataOutput out) {

		out.writeUTF(clientId);

		out.writeUnsignedInt(timeStamp);

		out.writeUTF(color);

		out.writeUTF(userName);

		out.writeUTF(text);

	}

}
