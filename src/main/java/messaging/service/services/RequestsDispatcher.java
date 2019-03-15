package messaging.service.services;

import org.dom4j.Element;
import org.jamppa.component.XMPPComponent;
import org.springframework.stereotype.Service;
import org.xmpp.component.ComponentException;
import org.xmpp.packet.IQ;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;
import messaging.service.model.Request;
import messaging.service.workers.ReceiveRequest;
import messaging.service.workers.GetRequest;

@Service
public class RequestsDispatcher extends XMPPComponent {
	private static Dotenv dotenv = Dotenv.load();

	public RequestsDispatcher() throws ComponentException {
		super(
			dotenv.get("JID"), 
			dotenv.get("PASSWORD"), 
			dotenv.get("XMPP_SERVER_IP"), 
			Integer.parseInt(dotenv.get("XMPP_SERVER_PORT"))
		);
		addSetHandler(new ReceiveRequest());
		addGetHandler(new GetRequest());
		this.connect();
	}
	
	public void dispatch(Request request) {
        IQ iq = new IQ(IQ.Type.set);
        iq.setTo(request.getRecipient());
        iq.setID(request.getOrder().get("id").toString());
        
        Element queryElement = iq.getElement().addElement("query", "create");

        Element orderElement = queryElement.addElement("request");

        String orderJson = new Gson().toJson(request);
        orderElement.setText(orderJson);
        
        this.syncSendPacket(iq);
	}

}
