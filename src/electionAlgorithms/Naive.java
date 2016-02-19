package electionAlgorithms;

import java.util.Arrays;
import java.util.List;

import node.Node;

public class Naive extends BaseAlgorithm {

	@Override
	public void onMessage(String message) {

		String messageHeader = message.split(":")[0];
		List<String> messageBody = Arrays.asList(message.replace(messageHeader+":", "").split(":"));
		
		if (messageHeader.equals("Election")){
			if (!messageBody.contains(Node.getMyID()+"")){ // If we are not contained in the list
				System.out.println("I " + Node.getMyID() + " am not contained in this message "+message);
				Node.sendMsgToNextNode(message + ":" + Node.getMyID()); // Suggest us for election
			}
			else { // If we are in the list
				System.out.println("I " + Node.getMyID() + " am contained in this message");
				String newLeader = findLeaderInBody(messageBody);
				Node.sendMsgToNextNode("Leader" + ":" + newLeader); // Announce the new leader
			}
		}
		
		else if (messageHeader.equals("Leader")){
			String leaderBody = message.split(":")[1];
			System.out.println(leaderBody + " is the new leader");
			leaderID = leaderBody;
			if (Integer.parseInt(leaderBody.trim()) != Node.getMyID()) Node.sendMsgToNextNode(message);
		}
	}

	private String findLeaderInBody(List<String> messageBody) {
		int maxID = 0;
		if (messageBody.size() > 0){
			for (String leaderCandidate : messageBody){
				if (Integer.parseInt(leaderCandidate.trim()) > maxID) {
					maxID = Integer.parseInt(leaderCandidate.trim());
				}
			}
		}
		return maxID+"";
	}

}
