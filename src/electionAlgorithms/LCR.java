package electionAlgorithms;

import node.Node;

/*Assume clockwise unidirectional ring.
One or more pis can take the initiative and start an election, by
sending an election message containing their id to pi+1.
When a pi spontaneously or upon receiving a message goes in an
election, it marks itself as a participant.
If the pi
receiving an election message has a greater id and is not
already a participant, then it sends an election message with its
own id to pi+1.
If its own id is smaller, it forwards the message with the id it has
received.
If it receives a message with its own id then it declares itself as
the leader.*/


public class LCR extends BaseAlgorithm{

	
	public void onMessage(String message) {
		
		String messageHeader = message.split(":")[0];
		String messageBody = message.split(":")[1];
		
		if (messageHeader.equals("Election")){
			if (Integer.parseInt(messageBody.trim()) < Node.getMyID() // If we are a better candidate
					&& !participant){
				System.out.println("I " + Node.getMyID() + " am a better candidate than "+messageBody);
				Node.sendMsgToNextNode("Election" + ":" + Node.getMyID()); // Suggest us for election
			}
			else if (Integer.parseInt(messageBody.trim()) == Node.getMyID())  { // If this is our ID
				System.out.println("I " + Node.getMyID() + " received my own pebble, so I am the new leader");
				Node.sendMsgToNextNode("Leader" + ":" + Node.getMyID()); // Announce us as the new leader
			}
			else { // The current candidate is better
				System.out.println("I " + Node.getMyID() + " am a worse candidate than "+messageBody);
				Node.sendMsgToNextNode(message); // Forward his candidancy
			}
			participant = true;
		}
		
		else if (messageHeader.equals("Leader")){
			System.out.println(messageBody + " is the new leader");
			leaderID = messageBody;
			if (Integer.parseInt(messageBody.trim()) != Node.getMyID()) Node.sendMsgToNextNode(message);
		}
	}
	
	
}
