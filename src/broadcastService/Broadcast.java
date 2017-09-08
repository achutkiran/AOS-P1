package broadcastService;



public class Broadcast {
	
	public static void main(String[] args){
		Broadcast bc = new Broadcast();
		SpanningTree s = new SpanningTree();
		ReadInput ri =  new ReadInput();
		Node input = ri.topology(Integer.parseInt(args[0]));
		s.spanning(input);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bc.printTreeNeighbours(input);
		Broadcasting broadcast = new Broadcasting(input);
		broadcast.startBroadcast();
		System.out.println("count for Node "+input.getNumber()+": "+input.getCount());
	}

	public void printTreeNeighbours(Node input) {
		System.out.print("\nSpanning tree is completed and its tree neighbours for node "+input.getNumber()+" are:\n ");
		for(Node node:input.getTreeNeighbours()){
			System.out.print(node.getNumber()+" ");
		}
		
	}

}
