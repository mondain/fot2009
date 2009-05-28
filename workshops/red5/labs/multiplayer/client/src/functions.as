
import flash.events.*;
import flash.media.*;
import flash.net.*;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.core.*;
import mx.events.*;

import com.flashontap.lab.multiplayer.*;

private var nc:NetConnection;
private var ns:NetStream;
private var chatSO:SharedObject;

[Bindable]
public var clientId:String = '';

[Bindable]
public var gameName:String = 'lab';
	
[Bindable]	
public var userName:String = 'player';

[Bindable]
private var hostString:String = 'localhost';

public function init():void {
	Security.allowDomain("*");
	
	var pattern:RegExp = new RegExp("http://([^/]*)/");				
	if (pattern.test(Application.application.url) == true) {
		var results:Array = pattern.exec(Application.application.url);
		hostString = results[1];
		//need to strip the port to avoid confusion
		if (hostString.indexOf(":") > 0) {
			hostString = hostString.split(":")[0];
		}
	}
	trace('Host: ' + hostString);	

}

public function connect():void {
	if (connectBtn.label === 'Connect') {
		trace('Connecting...');
		//  create the netConnection
		nc = new NetConnection();
		nc.objectEncoding = ObjectEncoding.AMF3
		//  set it's client/focus to this
		nc.client = this;

		// add listeners for netstatus and security issues
		nc.addEventListener(NetStatusEvent.NET_STATUS, onStatus);
		nc.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
		nc.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
		nc.addEventListener(AsyncErrorEvent.ASYNC_ERROR, asyncErrorHandler);

	    nc.connect(location.text, null);
	} else if (connectBtn.label === 'Disconnect') {
		trace('Disconnecting...');
		if (nc.connected) {
	  		if (ns) {
				ns.close();
	  		}				
			nc.close();
		}
	}		
}

public function onStatus(evt:NetStatusEvent):void {
	trace("NetConnection.onStatus " + evt);
	if (evt.info !== '' || evt.info !== null) {	
		switch (evt.info.code) {
        case "NetConnection.Connect.Success": 
        	trace("Connected");
        	connectBtn.label = "Disconnect";
        	
			ns = new NetStream(nc);
			ns.addEventListener(NetStatusEvent.NET_STATUS, onStatus);
			ns.addEventListener(AsyncErrorEvent.ASYNC_ERROR, asyncErrorHandler);
			ns.client = this;		
			
			chatSO = SharedObject.getRemote("chat-" + gameName, nc.uri, false);
			chatSO.client = this;
			chatSO.addEventListener(SyncEvent.SYNC, onSync);
			chatSO.connect(nc);					
			
			break;
        case "NetConnection.Connect.Closed":	                
        	trace("Disconnected");
			connectBtn.label = 'Connect';	
			break;
		}			
	}
}

public function onBWDone():void {
	// have to have this for an RTMP connection
}

public function onBWCheck(... rest):uint {
	//have to return something, so returning anything :)
	return 0;
}

public function setClientId(cid:String):void {
	clientId = cid;
}

public function onMessage(txt:String):void {
	chatIn.htmlText += '<b><font color="#000000">' + txt + '</font></b>\n';			
}

public function onSystemMessage(txt:String):void {
	chatIn.htmlText += '<b><font color="#FF0000">' + txt + '</font></b>\n';		
}

private function onSync(event:SyncEvent):void {
	trace("onSync code: " + event.changeList[0].code);	
	switch(event.changeList[0].code) {
		case "success":
			//chat from bc or other viewers
			recvChat(event.target.data["message"]);
			break;
		case "change":	
			//chat from self
			recvChat(event.target.data["message"]);
			break;
		default:
	}		
}

private function sendChat():void {
	var text:String = chatOut.text;
	if (chatSO && text !== "") {
		var msg:ChatMessage = new ChatMessage();
		msg.clientId = clientId;
		//msg.color = "#" + chatColor;
		msg.userName = userName;
		msg.text = text;
		chatSO.setProperty("message", msg);
	}
	chatOut.text = "";
}

private function recvChat(msg:ChatMessage):void {
	chatIn.htmlText += msg.toHtmlString();
}

public function securityErrorHandler(e:SecurityErrorEvent):void {
	trace('Security Error: '+e);
}

public function ioErrorHandler(e:IOErrorEvent):void {
	trace('IO Error: '+e);
}

public function asyncErrorHandler(e:AsyncErrorEvent):void {
	trace('Async Error: '+e);
}	

