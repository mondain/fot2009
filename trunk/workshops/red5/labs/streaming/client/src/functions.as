//
// Simple publisher
// @author Paul Gregoire (paul@infrared5.com)
//
import flash.events.*;
import flash.media.*;
import flash.net.*;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.core.*;
import mx.events.*;

public var nc:NetConnection;
public var ns:NetStream;

private var camera:Camera;
private var mic:Microphone;

private var publisherVideo:Video;	


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

public function onBWDone():void {
	// have to have this for an RTMP connection
}

public function onBWCheck(... rest):uint {
	//have to return something, so returning anything :)
	return 0;
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
				publishDisplay.removeChild(publisherVideo);
				publisherVideo.attachCamera(null);
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
							
			callLater(setupCamera);    				
			
			break;
        case "NetStream.Publish.BadName":
        	Alert.show("A stream already exists for the name requested: " + streamName.text, "Error");
        	break;
        case "NetStream.Publish.Stop":
        case "NetStream.Play.Stop":
        case "NetStream.Play.UnpublishNotify":
            trace("Playback has ended or broadcaster disconnected");
			break;				
        case "NetStream.Play.StreamNotFound":
            trace("Unable to locate video: " + location.text);
            break;
        case "NetConnection.Connect.Failed":
        case "NetConnection.Connect.Rejected":
        	trace("Connection failed");
        	break;
        case "NetConnection.Connect.Closed":	                
        	trace("Disconnected");
			connectBtn.label = 'Connect';	
			break;
		}			
	}
}

private function setupCamera():void {
	trace("Setting up camera");
	if (Camera.names.length > 0) {
		//Security.showSettings(SecurityPanel.CAMERA);
		//if no camera setup then set one up
		if (!camera) {
			if (Camera.names.length === 1) {
				camera = Camera.getCamera();    
			} else {
				//get the "first" camera so out cbox will work
				camera = Camera.getCamera("0"); 
			}
		}
		//check camera before trying to configure
        if (camera) {
			//TODO make camera settings configurable
	        camera.setMode(320, 240, 15);
	        camera.setQuality(0, 70);
	        
        	if (!publisherVideo) {
        		publisherVideo = new Video();
        		publishDisplay.addChild(publisherVideo);
        	}
    		publisherVideo.attachCamera(camera);
        
        	if (ns) {
       			ns.attachCamera(camera);
       		}
        
			//publisherVideo.width = 320;
			//publisherVideo.height = 240;
    	} else {
            Alert.show("There was a problem connecting to your camera");
	    }

	} else {
        Alert.show("No cameras detected");
	}
	
   	if (Microphone.names.length > 0) {
		//Security.showSettings(SecurityPanel.MICROPHONE);
		//if no microphone setup then set one up
		if (!mic) {			
			if (Microphone.names.length === 1) {
				mic = Microphone.getMicrophone(); 
			} else {
				mic = Microphone.getMicrophone(0); 			
			}
		}
		//check microphone before trying to configure
		if (mic) {
			//TODO make microphone settings configurable
			//mic.gain = 60;
			mic.rate = 22;
	        mic.setLoopBack(false);
	        mic.setUseEchoSuppression(true);
	        //mic.addEventListener(StatusEvent.STATUS, micStatusHandler);
			if (ns) {
				ns.attachAudio(mic);
			}
    	} else {
            Alert.show("There was a problem connecting to your microphone");
	    }
	}
    
}   
	
public function publish():void {
	//if we are connected
	if (nc.connected) {
		//if a cam or mic were found
		if (camera || mic) {
			if (ns) {
				ns.publish(streamName.text, "publish"); //publish, append, record
			}
		}
	}
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

