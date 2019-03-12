package messaging.service.workers;

import org.jamppa.component.PacketSender;
import org.jamppa.component.handler.QueryHandler;
import org.xmpp.packet.IQ;

public class GetRequest implements QueryHandler {

	public IQ handle(IQ iq) {
		return null;
	}

	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPacketSender(PacketSender packetSender) {
		// TODO Auto-generated method stub

	}

}
