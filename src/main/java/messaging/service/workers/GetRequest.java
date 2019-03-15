package messaging.service.workers;

import org.jamppa.component.handler.AbstractQueryHandler;
import org.xmpp.packet.IQ;

public class GetRequest extends AbstractQueryHandler {

	public GetRequest() {
		super("REMOTE_GET_ORDER");
	}

	public IQ handle(IQ iq) {
		return null;
	}

}
