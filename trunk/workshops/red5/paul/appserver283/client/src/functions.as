

	/*
		Refs: 
		http://jira.red5.org/confluence/display/streaming/Red5+and+NetStream
	
		@author Paul Gregoire
	*/
	import org.red5.jira.*;
	
	import flash.events.*;
	import flash.media.*;
	import flash.net.*;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.*;
	import mx.events.*;

	private var nc:NetConnection;
	private var ns:NetStream;
		
	private var fooIn:Foo;
	private var fooOut:Foo;

	[Bindable]
	private var hostString:String = 'localhost';

	[Bindable]
	public var clientId:String = '';
	
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
		log('Host: ' + hostString);	

		//  create the netConnection
		nc = new NetConnection();
		nc.objectEncoding = ObjectEncoding.AMF3;
		//  set it's client/focus to this
		nc.client = this;

		// add listeners for netstatus and security issues
		nc.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
		nc.addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
		nc.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
		nc.addEventListener(AsyncErrorEvent.ASYNC_ERROR, asyncErrorHandler);

	}

	public function doConnection():void {
		if (connector.label === 'Connect') {
			var params:Array = new Array();
			params.push(userId.text);
		    try {
				// do the connection - called by the Connect Button's click event
			    nc.connect(givenPath.text, params);   			    
			    log('Connecting...');
		    } catch(e) {
			    log('Connect failed'+e);
		    }	
		} else if (connector.label === 'Disconnect') {
			if (nc.connected) {
				nc.close();
			}
		}
	}

	public function onBWDone():void {
		// have to have this for an RTMP connection
		log('onBWDone');
	}

	public function onBWCheck(... rest):uint {
		log('onBWCheck');
		//have to return something, so returning anything :)
		return 0;
	}

	private function netStatusHandler(event:NetStatusEvent):void {
		log('Net status: '+event.info.code);
        switch (event.info.code) {
            case "NetConnection.Connect.Success":
				// set connection indicator
				connector.label = 'Disconnect';
				indicatorOn.visible=true;
				indicatorOff.visible=false;
                break;
            case "NetConnection.Connect.Closed":	                
				connector.label = 'Connect';		
				indicatorOn.visible=false;
				indicatorOff.visible=true;					                	        	
            	break;	               
            default:
            
        }				
    }

	// call server-side method
    public function getObject():void {
    	log('Get object');
		// create a responder set to callback
		var pResponder:Responder = new Responder(handleObject, null);
		// call the server side method
		nc.call("getObject", pResponder, fooOut);
	}

	// call server-side method
    public function setObject():void {
    	log('Set object');
    	if (!fooOut) {
    		fooOut = new Foo();
    	}
		// call the server side method
		nc.call("setObject", null, fooOut);
	}

	//callback handler
    public function handleObject(o:Object):void {
		log('handleObject '+o);
		fooIn = o as Foo;
		traceObject(fooIn);
	}
    
	//called by the server
	public function setClientId(param:Object):void {
		log('Set client id called: '+param);
		clientId = param as String;
		log('Setting client id: '+clientId);
	}
	
	//called by the server in the event of a server side error
	public function onAlert(alert:Object):void {
		log('Got an alert: '+alert);
		Alert.show(String(alert), 'Alert');
	}			

    public function onMetaData(info:Object):void {
    	log('Got meta data');
   		var key:String;
    	for (key in info) {
        	log('Meta: '+ key + ': ' + info[key]);
    	}
	}
	
    public function onCuePoint(info:Object):void {
        log("Cuepoint: time=" + info.time + " name=" + info.name + " type=" + info.type);
    }	
    
    public function onPlayStatus(info:Object):void {
    	log('Got play status');
    }
    
	private function securityErrorHandler(e:SecurityErrorEvent):void {
		log('Security Error: '+e);
	}

	private function ioErrorHandler(e:IOErrorEvent):void {
		log('IO Error: '+e);
	}
	
	private function asyncErrorHandler(e:AsyncErrorEvent):void {
		log('Async Error: '+e);
	}
	
	private function log(text:String):void {
		var tmp:String = String(messages.data);
		tmp += text + '\n';
		messages.data = tmp;
		messages.verticalScrollPosition = messages.maxVerticalScrollPosition + 1;
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
	            log(indentString + " " + i + ": [Object]");
	            traceObject(val, indent + 1);
	        } else {
	            log(indentString + " " + prop + ": " + val);
	        }
	    }
	}
