import javax.jms.Queue;

import org.hornetq.api.core.HornetQException;
import org.hornetq.api.core.SimpleString;
import org.hornetq.api.core.client.ClientMessage;
import org.hornetq.api.core.client.ClientProducer;
import org.hornetq.api.core.client.ClientSession;
import org.hornetq.api.core.client.ClientSession.QueueQuery;
import org.hornetq.api.core.client.HornetQClient;
import org.hornetq.api.jms.HornetQJMSClient;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HornetQTest {

	public static AbstractApplicationContext context;
	
	@BeforeClass
	public static void inicializeServer() {
		context = new ClassPathXmlApplicationContext(new String[] {"/test.xml", "/hornetQ.xml"});
	}
	
	@AfterClass() 
	public static void closeServer() {
		context.close();		
	}
	
	@Test
	public void sendMessage() {
		ClientProducer producer = (ClientProducer)context.getBean("producer");
		ClientSession session = (ClientSession)context.getBean("clientSession");

		ClientMessage message = session.createMessage(false);
		message.putStringProperty("data", "leandro,20000,2000,2,0,105");	
		
		
		try {
			producer.send(message);
			SimpleString simpleStr = new SimpleString("eventQueue");
			QueueQuery query = session.queueQuery(simpleStr);
			Assert.assertTrue("Message Sent SuccessFully",query.getMessageCount() == 1);
		} catch (HornetQException e) {
			e.printStackTrace();
		}		
	}
}
