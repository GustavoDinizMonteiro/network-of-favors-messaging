package messaging.service.workers;

import org.dom4j.Element;
import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;

public class GetRequest extends AbstractQueryHandler {

	public GetRequest() {
		super("REMOTE_GET_ORDER");
	}

	public IQ handle(IQ iq) {
		@SuppressWarnings("unused")
		Element queryElement = iq.getElement().element("query")
									.element("id");
		// TODO: get order info from cloud provider
		return null;
	}

}
