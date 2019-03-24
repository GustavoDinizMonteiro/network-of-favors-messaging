package messaging.service.services;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Element;
import org.jamppa.component.XMPPComponent;
import org.springframework.stereotype.Service;
import org.xmpp.component.ComponentException;
import org.xmpp.packet.IQ;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;
import messaging.service.model.Request;
import messaging.service.workers.GetRequest;
import messaging.service.workers.ReceiveRequest;

@Service
public class RequestsDispatcher extends XMPPComponent {
	private static Dotenv dotenv = Dotenv.load();

	public RequestsDispatcher() throws ComponentException {
		super(
			dotenv.get("JID"), 
			dotenv.get("PASSWORD"), 
			dotenv.get("XMPP_SERVER_IP"), 
			Integer.parseInt(dotenv.get("XMPP_SERVER_PORT")),
			Integer.parseInt(dotenv.get("TIMEOUT"))
		);
		addSetHandler(new ReceiveRequest());
		addGetHandler(new GetRequest());
		connect();
	}
	
	public void dispatch(Request request) {
        IQ iq = new IQ(IQ.Type.set);
        iq.setTo(request.getRecipient());
        iq.setID(request.getOrder().get("id").toString());
        
        Element queryElement = iq.getElement().addElement("query", "REMOTE_CREATE_ORDER");

        Element orderElement = queryElement.addElement("request");

        String orderJson = new Gson().toJson(request);
        orderElement.setText(orderJson);
        
        syncSendPacket(iq);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> get(Integer id, String requester,  String token, String provider) {
		IQ iq = new IQ(IQ.Type.get);
		iq.setTo(provider);

        Element queryElement = iq.getElement().addElement("query", "REMOTE_GET_ORDER");

        Element orderIdElement = queryElement.addElement("id");
        orderIdElement.setText(id.toString());
        
        IQ response = (IQ) syncSendPacket(iq);
        String order = response.getElement().element("order").getText();
        
		return (Map<String, Object>) new Gson().fromJson(order, HashMap.class); 
	}

}
