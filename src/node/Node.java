package node;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class Node {
	private static Scanner input;
	private static String nextNode;
	private static String nextNodePort;
	private static int myID;

	public static void main(String[] args) throws Exception {
		input = new Scanner(System.in);
		init();
		new Thread(new ServerListener()).start();
		System.out.println("Press enter to initiate election");
		input.nextLine();
		connect("Election:"+myID,nextNode,nextNodePort);
	}
	
	private static void init() throws Exception{
		myID = new Random().nextInt(1000);
//		System.out.println(new IPhandler().getGlobalIp());
		System.out.println("Chosen ID is "+myID);
		System.out.println("Next Node IP: ");
		nextNode = input.nextLine();
		System.out.println("Next Node Port: ");
		nextNodePort = input.nextLine();
	}
	
	/**
	 * Connect to given parameters
	 * @param Ip Connect to this IP
	 * @param Port Connect to this port, in the above IP
	 */
	public static void connect(String msg,String Ip,String Port){
		Socket s = null;
		try{
			s = new Socket(Ip, Integer.valueOf(Port));
			PrintStream out = new PrintStream( s.getOutputStream());
			out.println( msg );
		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
     }
	
	public static void sendMsgToNextNode(String msg){
		connect( msg, nextNode, nextNodePort );
	}

	public static int getMyID() {
		return myID;
	}

}
