package org.red5.jira;

import java.io.Serializable;
import java.util.List;

import org.red5.io.amf3.IDataInput;
import org.red5.io.amf3.IDataOutput;
import org.red5.io.amf3.IExternalizable;

public class Foo implements IExternalizable, Serializable {

	private static final long serialVersionUID = 283L;

	private int id;
	private boolean ready;
	private String name;
	private Object[] array;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object[] getArray() {
		return array;
	}

	public void setArray(Object[] array) {
		this.array = array;
	}

	/**
	 * Deserializes the client state of an instance.
	 */
	public void readExternal(IDataInput input) {
		id = input.readInt();
		ready = input.readBoolean();
		name = input.readUTF();
		Object o = input.readObject();
		if (o instanceof List) {
			List list = (List) o;
			array = new Object[list.size()];
			int i = 0;
			for (Object oo : list) {
				array[i++] = oo;
			}
		} else if (o instanceof Object[]) {
			array = (Object[]) o;
		}
	}

	/**
	 * Serializes the server state of an instance.
	 */
	public void writeExternal(IDataOutput out) {
		out.writeInt(id);
		out.writeBoolean(ready);
		out.writeUTF(name);
		out.writeObject(array);
	}
}
