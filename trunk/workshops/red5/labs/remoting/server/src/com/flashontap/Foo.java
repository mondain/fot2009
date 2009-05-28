package com.flashontap;

import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.ArrayList;

import org.red5.io.amf3.IDataInput;
import org.red5.io.amf3.IDataOutput;
import org.red5.io.amf3.IExternalizable;

public class Foo implements IExternalizable {

	private int id = 42;
	private boolean ready = true;
	private String name = "test";
	private ArrayList<String> array = new ArrayList<String>();
	
	@Override
	public void readExternal(IDataInput in) {
		id = in.readInt();
		ready = in.readBoolean();
		name = in.readUTF();
		array = (ArrayList<String>) in.readObject();
	}
	
	@Override
	public void writeExternal(IDataOutput out) {
		out.writeInt(id);
		out.writeBoolean(ready);
		out.writeUTF(name);
		out.writeObject(array);		
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);		
	} 	
	
	
	
}
