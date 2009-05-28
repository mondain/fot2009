
import flash.events.*;
import flash.media.*;
import flash.net.*;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.core.*;
import mx.events.*;

import com.flashontap.*;

public var nc:NetConnection;
public var ns:NetStream;

private var fooIn:Foo;
private var fooOut:Foo;

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

// call server-side method
public function getObject():void {
	trace('Get object');
	// create a responder set to callback
	var pResponder:Responder = new Responder(handleObject, null);
	// call the server side method
	nc.call("getObject", pResponder, fooOut);
}

// call server-side method
public function setObject():void {
	trace('Set object');
	if (!fooOut) {
		fooOut = new Foo();
	}
	// call the server side method
	nc.call("setObject", null, fooOut);
}

//callback handler
public function handleObject(o:Object):void {
	trace('handleObject '+o);
	fooIn = o as Foo;
	traceObject(fooIn);
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

public function traceObject(obj:Object, indent:uint = 0):void {
    var indentString:String = "";
    var i:uint;
    var prop:String;
    var val:*;
    for (i = 0; i < indent; i++) {
        indentString += "\t";
    }
    for (prop in obj) {
        val = obj[prop];
        if (typeof(val) == "object") {
            trace(indentString + " " + i + ": [Object]");
            traceObject(val, indent + 1);
        } else {
            trace(indentString + " " + prop + ": " + val);
        }
    }
}
	
	
	
	

