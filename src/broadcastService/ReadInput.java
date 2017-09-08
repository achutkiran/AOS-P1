package broadcastService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInput {
	ArrayList<Node> input = new ArrayList<Node>() ;
	public Node topology(int name){
		BufferedReader br;
		Node output = null;
		
		int n=0,index=0,dur=0,num=0;
		try {
			br = new BufferedReader(new FileReader("demo.txt"));
			String line = null;
			while ((line = br.readLine()) != null) {
				if((!line.contains("#")) && (!line.equalsIgnoreCase(""))){
					if(n==0){
						String[] dura = line.split(" ");
						n = Integer.parseInt(dura[0]);
						dur = Integer.parseInt(dura[1]);
						num = Integer.parseInt(dura[2]);
						for(int i=0;i<n;i++){
							Node node = new Node();
							node.setNumber(i+1);
							node.setDur(dur);
							node.setN(num);
							node.setNoNodes(n);
							input.add(node);
						}
					}
					else{
						Node in = input.get(index);
						String[] splits = line.split(" ");
						String  host = splits[0];
						in.setHost(host+".utdallas.edu");
						int port = Integer.parseInt(splits[1]);
						in.setPort(port);
						for(int i = 2;i<splits.length;i++){
							int neigh = Integer.parseInt(splits[i]);
							in.setNeighbours(input.get(neigh-1));
						}
						index++;
						in.setNumber(index);
						if(name==index){
							output = in;
						}
					}
				}
			}
		 
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return output;
		
	}
	
}
