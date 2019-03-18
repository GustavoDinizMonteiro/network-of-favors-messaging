package messaging.service.workers;

import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import io.github.cdimascio.dotenv.Dotenv;

public class GetRequest extends AbstractQueryHandler {
	private static Dotenv dotenv = Dotenv.load();

	public GetRequest() {
		super("REMOTE_GET_ORDER");
	}

	public IQ handle(IQ iq) {
		String orderId = iq.getElement()
									.element("query")
									.element("id")
									.getText();
		String endpoint = dotenv.get("XMPP_SERVER_IP");
		IQ responseIQ = IQ.createResultIQ(iq);
		try {
			String response = Unirest.get(endpoint.concat(orderId))
						.asJson().getBody().toString();
			responseIQ.getElement().addElement("response", response);
		} catch (UnirestException e) {
			System.err.println(e.getMessage());
		}
		return responseIQ;
	}

}
