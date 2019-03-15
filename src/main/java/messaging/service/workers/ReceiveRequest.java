package messaging.service.workers;

import org.dom4j.Element;
import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;

import com.google.gson.Gson;

import messaging.service.model.Request;

public class ReceiveRequest extends AbstractQueryHandler {

	public ReceiveRequest() {
		super("REMOTE_CREATE_ORDER");
	}

	public IQ handle(IQ iq) {
        Element queryElement = iq.getElement().element("query");
        Element orderElement = queryElement.element("request");
        String orderJsonStr = orderElement.getText();
        
        IQ response = IQ.createResultIQ(iq);
        Gson gson = new Gson();
        try {
        	@SuppressWarnings("unused")
			Request request = (Request) gson.fromJson(orderJsonStr, Class.forName(Request.class.getName()));
        	// TODO: re-send request to network of favors.
        } catch (Throwable e) {
            throw new RuntimeException();
        }
        return response;
	}

}
