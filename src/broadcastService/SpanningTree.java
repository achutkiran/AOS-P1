package broadcastService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class SpanningTree {
	public void spanning(Node input){
		if(input.getNumber()==input.getNoNodes()){ //making last node as initator for spanning tree protocol
			LinkedList<Node> neighbours = input.getNeighbours(); //reading tree neighbours 
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(Node node:neighbours){			// sending request to every tree neighbour
				sendRequestToNeighbours(node,input);
			}
			
		}
		else{
			spanningRequest(input); 
		}
		
	}
	
	public void sendRequestToNeighbours(Node output, Node input){
		if(output.getNumber()!=output.getNoNodes()){
			try {
				Socket clientSocket = new Socket(output.getHost(),output.getPort());
				InputStream is = clientSocket.getInputStream();
				OutputStream os = clientSocket.getOutputStream();
				String data = "are you in spanning tree";
				StreamUtil.writeLine(data, os);		//asking whether node is in spanning tree
				String result = StreamUtil.readLine(is);
				if(result.equalsIgnoreCase("no")){
					input.setTreeNeighbours(output);	// if output is no then adding to its tree neighbours
					StreamUtil.writeLine(String.valueOf(input.getNumber()), os);
					result = StreamUtil.readLine(is);
				}
				clientSocket.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void spanningRequest(Node input){
		try {
			ServerSocket serverSock = new ServerSocket(input.getPort());
			int num = input.getNeighbours().size();
			Thread[] t = new Thread[num];
			for(int i=0;i<num;i++){
				Socket clientSocket = serverSock.accept();
				MyRunnable runner = new MyRunnable(clientSocket,input);
				t[i] = new Thread(runner);
				t[i].start();
			}
			for(int i=0;i<num;i++){
				while(t[i].isAlive()){ //checking whether the created threads are alive or not
					
				}
			}
			serverSock.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	 
	class MyRunnable implements Runnable{
		private Socket cs;
		private Node input;
		MyRunnable(Socket cs, Node input){
			this.cs = cs;
			this.input = input;
		}
		public void run(){
			try {
				process(cs,input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Node findAdajacant(Node input, int id){
		Node output = new Node();
		for(Node node : input.getNeighbours()){
			if(node.getNumber()==id){
				output = node; 
			}
		}
		return output;
	}
	
	public void process(Socket clientSocket, Node input) throws IOException{
		InputStream is = clientSocket.getInputStream();
		OutputStream os = clientSocket.getOutputStream();
		String result = StreamUtil.readLine(is);
		if(result.contains("are you in spanning tree")){
			if(input.getTreeNeighbours().size()==0){
				StreamUtil.writeLine("no", os);
				int node =Integer.parseInt(StreamUtil.readLine(is));
				//clientSocket.close();
				Node adj = findAdajacant(input,node);
				input.setTreeNeighbours(adj);
				for(Node output : input.getNeighbours()){
						sendRequestToNeighbours(output,input);
				}
				StreamUtil.writeLine("spanning tree completed",os);
			}
			else{
				StreamUtil.writeLine("yes", os);
				clientSocket.close();
			}
		}
	}
	

}


