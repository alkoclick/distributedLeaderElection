package electionAlgorithms;

public abstract class BaseAlgorithm {
	boolean participant = false;
	String leaderID = "";
	
	public abstract void onMessage(String message);
	
	
}
