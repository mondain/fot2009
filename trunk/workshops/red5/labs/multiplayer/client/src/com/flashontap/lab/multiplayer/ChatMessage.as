package com.flashontap.lab.multiplayer {
	
	import flash.utils.*;
	
	[Bindable]
	[RemoteClass(alias="com.flashontap.lab.multiplayer.ChatMessage")]
	public class ChatMessage implements IExternalizable {
	
		public var clientId:String;
		public var timeStamp:uint;
		public var color:String = "#000000";
		public var userName:String;
		public var text:String;
		
		public function ChatMessage() {
			timeStamp = getTimer();
		}
				
		public function toHtmlString():String {
			if (userName && userName !== null) {
				return '<font color="' + color + '">' + userName + ': ' + text + '</font>\n';
			} else {
				return '<font color="' + color + '">' + text + '</font>\n';
			}
		}
		
		public function readExternal(input:IDataInput):void {
			clientId = input.readUTF();
			timeStamp = input.readUnsignedInt();
			color = input.readUTF();
			userName = input.readUTF();
			text = input.readUTF();
		}
	
		public function writeExternal(out:IDataOutput):void {
			out.writeUTF(clientId);
			out.writeUnsignedInt(timeStamp);
			out.writeUTF(color);
			out.writeUTF(userName);
			out.writeUTF(text);
		}
		
	}	
	
}
