
import flash.events.*;
import flash.media.*;
import flash.net.*;

import mx.collections.ArrayCollection;
import mx.controls.Alert;
import mx.core.*;
import mx.events.*;

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


