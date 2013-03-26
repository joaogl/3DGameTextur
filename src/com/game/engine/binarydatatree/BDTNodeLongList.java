package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**A node that can contain an very large amount of equal-type entry's.**/
public class BDTNodeLongList extends BDTNode {
	private int NodeType = BDTNode.NT_END;
	private ArrayList<BDTNode> list;
	
	public BDTNodeLongList(String name,int type){
		super(name,BDTNode.NT_LONGLIST);
		this.NodeType = type;
		this.list = new ArrayList<BDTNode>();
	}
	
	@Override
	public boolean isNodeContainer() {
		return true;
	}
	
	
	@Override
	public int getPayloadSize() {
		int size = 0;

		for(int i = 0; i < this.list.size(); i++){
			BDTNode node = this.list.get(i);
			size += node.getPayloadSize();
		}
		
		return size;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException, Exception {

		this.NodeType = stream.read();
		
		int count = 0;
		while(true){
			BDTNode node = BDTNode.createNode(this.NodeType, "listEntry"+count);
			node.readNodeContent(reader, stream);
			
			if(node.getType() == BDTNode.NT_END)
				break;
			else
				this.list.add(node);
			count++;
		}
		
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		
		stream.write(this.NodeType);
		
		for(BDTNode node : this.list){
			writer.writeNode(node);
		}
		writer.writeNode(new BDTNodeEnd());
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " LongList Node '" + this.getName() + "' ("+this.list.size() + " entry/s)");
		
		for(int i = 0; i < this.list.size(); i++){
			this.list.get(i).printTextualNodeRepresentationToPrintStream(printer, depth + 1,b);
		}
	}

	@Override
	public BDTNode copy() {
		BDTNodeLongList copy = new BDTNodeLongList(this.getName(), this.NodeType);
		
		for(int i = 0; i < this.list.size(); i++){
			copy.add(this.list.get(i).copy());
		}
		
		return copy;
	}

	private void add(BDTNode copy) {
		this.list.add(copy);
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_LONGLIST";
	}

	@Override
	public String getContentStringRepresentation() {
		return "LIST_ENTRYS="+this.list.size();
	}
	
	@Override
	public void getChildrens(ArrayList<BDTNode> childs) {
		childs.addAll(this.list);
	}
}
