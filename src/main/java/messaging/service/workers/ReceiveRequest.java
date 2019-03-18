package messaging.service.workers;

import org.dom4j.Element;
import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;

import com.mashape.unirest.http.Unirest;

import io.github.cdimascio.dotenv.Dotenv;

public class ReceiveRequest extends AbstractQueryHandler {
	private static Dotenv dotenv = Dotenv.load();
	
	public ReceiveRequest() {
		super("REMOTE_CREATE_ORDER");
	}

	public IQ handle(IQ iq) {
        Element queryElement = iq.getElement().element("query");
        Element orderElement = queryElement.element("request");
        String orderJsonStr = orderElement.getText();
        
        IQ responseIQ = IQ.createResultIQ(iq);
        try {
        	String endpoint = dotenv.get("XMPP_SERVER_IP");
        	Unirest.post(endpoint).body(orderJsonStr).asJson();
        } catch (Throwable e) {
            throw new RuntimeException();
        }
        return responseIQ;
	}

}
