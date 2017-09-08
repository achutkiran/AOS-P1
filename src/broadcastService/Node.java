package broadcastService;

import java.util.LinkedList;

public class Node {
	private String host; 
	private int port;
	private int number;
	private LinkedList<Node> neighbours = new LinkedList<Node>();
	private LinkedList<Node> treeNeighbours = new LinkedList<Node>();
	private int dur;
	private int n;
	private int noNodes;
	private int count;
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count; 
	}
	
	public int getNoNodes(){
		return noNodes;
	}
	
	public void setNoNodes(int noNodes){
		this.noNodes = noNodes;
	}
	
	public int getDur(){
		return dur;
	}
	
	public void setDur(int dur){
		this.dur = dur;
	}
	
	public int getN(){
		return n;
	}
	
	public void setN(int n){
		this.n = n;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public LinkedList<Node> getNeighbours() {
		return neighbours;
	}
	public void setNeighbours(Node neighbour) {
		neighbours.add(neighbour);
	}
	
	public LinkedList<Node> getTreeNeighbours() {
		return treeNeighbours;
	}
	public void setTreeNeighbours(Node treeNeighbour) {
		treeNeighbours.add(treeNeighbour);
	}

}
