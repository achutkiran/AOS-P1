package broadcastService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;

public class Broadcasting {
	int neigh;
	int time;
	Node node;
	Random rand = new Random();
	Broadcasting(Node input){
		this.node = input;
		neigh =(node.getNoNodes()-1)*node.getN(); // how many time does the server must start
		time = node.getDur();
	}
	public void startBroadcast(){
		MyRunnable runner = new MyRunnable();
		Thread t = new Thread(runner);
		t.start();
		try {
			ServerSocket serverSocket = new ServerSocket(node.getPort());
			while(neigh!=0){
				Socket clientSocket = serverSocket.accept(); //accepting client connetions
				MyRunnable2 runner2 = new MyRunnable2(clientSocket);
				Thread t1 = new Thread(runner2);
				t1.start();
				neigh--;
			}
			while(t.isAlive()){ //checking whether thread is alive or not
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class MyRunnable2 implements Runnable{
		Socket clientSocket;
		MyRunnable2(Socket cs){
			clientSocket =cs;
		}
		@Override
		public void run() {
			InputStream is;
			try {
				is = clientSocket.getInputStream();
				OutputStream os = clientSocket.getOutputStream();
				String message = StreamUtil.readLine(is); //reading message
				String[] inNode = message.split(":");
				System.out.print("\nNode "+node.getNumber()+":"+inNode[1].substring(0, inNode[1].length()-3)+" from "+inNode[0]);
				int count = node.getCount();
				count += Character.getNumericValue(message.charAt(message.length()-2)); //adding recieved number to count
				node.setCount(count);
				message = broadcast(message); //broadcasting message
				StreamUtil.writeLine(message, os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	class MyRunnable implements Runnable{

		@Override
		public void run() {
			int k=node.getN();
			try {
				Thread.sleep(3000); //waiting for servers to activate
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(k!=0){
				int r = rand.nextInt(10); //creating random number
				int count = node.getCount();
				count += r;
				node.setCount(count);
				String message = "node "+node.getNumber()+": "; 
				message += "Hi"+","+r;
				message += node.getNumber();
				broadcast(message); //broadcasting message
				System.out.print("\nBroadcast by node "+node.getNumber()+" is completed");
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//time = time*node.getNumber();
				k--;
			}
		}
		
	}
	 
	public String broadcast(String message){
		String output="";
		if(node.getTreeNeighbours().size()==1 && Character.getNumericValue(message.charAt(5))!=node.getNumber()){
			output =message.substring(0, 7)+"broadcast completed";
		}
		else{
			int p = Character.getNumericValue(message.charAt(message.length()-1));
			for(Node input : node.getTreeNeighbours()){
				try {
					if(input.getNumber()!=p){
						Socket clientSocket = new Socket(input.getHost(),input.getPort());
						InputStream is = clientSocket.getInputStream();
						OutputStream os = clientSocket.getOutputStream();
						message = message.substring(0,message.length()-1)+node.getNumber();
						StreamUtil.writeLine(message, os);
						output = StreamUtil.readLine(is);
						clientSocket.close();
					}
				} catch (IOException e) {
					System.out.println("Connection refused by"+input.getNumber());
					e.printStackTrace();
				}
			}
		}
		return output;
	}


}
