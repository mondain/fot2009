package appserver261;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.red5.server.adapter.ApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.IClient;
import org.red5.server.api.Red5;
import org.red5.server.api.service.ServiceUtils;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.ISubscriberStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * Main application for APPSERVER-261
 * 
 * @author Paul Gregoire
 */
public class Application extends ApplicationAdapter {

	protected static Logger log = LoggerFactory.getLogger(Application.class);

    @Override
    public boolean roomJoin(IClient client, IScope scope) {  
        if (scope.getName().equals("room1")) { 
            scope.disconnect(Red5.getConnectionLocal());
            return false; 
        } 
        return super.roomJoin(client, scope);
    } 
    			
}
