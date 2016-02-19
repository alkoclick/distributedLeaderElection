package node;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import electionAlgorithms.BaseAlgorithm;
import electionAlgorithms.LCR;
import electionAlgorithms.Naive;

public class ConnectionListener implements Runnable{

	private BufferedReader in;

	public ConnectionListener(Socket clientSocket) throws IOException {
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		run();
	}

	@Override
	public void run() {
		System.out.println("Node Listener started");
		BaseAlgorithm algorithm = new LCR();
//		BaseAlgorithm algorithm = new Naive();
		try {
			algorithm.onMessage(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
