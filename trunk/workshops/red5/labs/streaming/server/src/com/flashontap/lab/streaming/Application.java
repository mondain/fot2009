package com.flashontap.lab.streaming;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IPlayItem;
import org.red5.server.api.stream.IPlaylistSubscriberStream;
import org.red5.server.api.stream.IStreamAwareScopeHandler;
import org.red5.server.api.stream.ISubscriberStream;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Simple ApplicationAdapter
 *  
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class Application extends MultiThreadedApplicationAdapter implements
		ApplicationContextAware, IStreamAwareScopeHandler {

	private static Logger log = Red5LoggerFactory.getLogger(Application.class, "streaming");

	private static ApplicationContext applicationContext;
	
	public Application() {
		log.info("Instanced");
	}

	/**
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		Application.applicationContext = applicationContext;
		log.debug("setApplicationContext {}", Application.applicationContext);
	}

	@Override
	public void streamPublishStart(IBroadcastStream stream) {
		log.debug("Publish start");
		super.streamPublishStart(stream);
	}	
	
	@Override
	public void streamBroadcastStart(IBroadcastStream stream) {
		log.debug("Broadcast start");
		super.streamBroadcastStart(stream);
	}
	
	@Override
	public void streamBroadcastClose(IBroadcastStream arg0) {
		log.debug("Broadcast close");
		super.streamBroadcastClose(arg0);
	}

	@Override
	public void streamPlaylistItemPlay(IPlaylistSubscriberStream stream,
			IPlayItem item, boolean isLive) {
		log.debug("Play");
		super.streamPlaylistItemPlay(stream, item, isLive);
	}

	@Override
	public void streamPlaylistItemStop(IPlaylistSubscriberStream arg0,
			IPlayItem arg1) {
		log.debug("Play stop");
		super.streamPlaylistItemStop(arg0, arg1);
	}

	@Override
	public void streamSubscriberStart(ISubscriberStream stream) {
		log.debug("Subscriber start");
		super.streamSubscriberStart(stream);
	}	
	
	@Override
	public void streamSubscriberClose(ISubscriberStream stream) {
		log.debug("Subscriber close");
		super.streamSubscriberClose(stream);
	}	
	
	
}
