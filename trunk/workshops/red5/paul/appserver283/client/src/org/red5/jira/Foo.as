package org.red5.jira {
	
	import flash.utils.IDataInput;
	import flash.utils.IDataOutput;
	import flash.utils.IExternalizable;
	
	
	[Bindable]
	[RemoteClass(alias="org.red5.jira.Foo")]
	public class Foo implements IExternalizable {

		public var id:int = 42;
		public var ready:Boolean = true;
		public var name:String = "test";
		public var array:Array = ["one", "two", "three", "four"]; 

		public function readExternal(input:IDataInput):void {	 		
			id = input.readInt();
			ready = input.readBoolean();	
			name = input.readUTF();
			array = input.readObject() as Array;
		}

		public function writeExternal(out:IDataOutput):void {
			out.writeInt(id);
			out.writeBoolean(ready);	
			out.writeUTF(name);
			out.writeObject(array);		
		}
	}

}