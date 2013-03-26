package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**A Node that contains an defined amount of equal-type Nodes.**/
public class BDTNodeList extends BDTNode{
	private int NodeType = BDTNode.NT_END;
	private ArrayList<BDTNode> list;
	
	public BDTNodeList(String name,int type){
		super(name,BDTNode.NT_LIST);
		NodeType = type;
		list = new ArrayList<BDTNode>();
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
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream) throws Exception {
		int size = stream.readInt();
		this.NodeType = stream.read();
		
		for(int i = 0; i < size; i++){
			BDTNode node = BDTNode.createNode(this.NodeType, "listEntry" + i);
			node.readNodeContent(reader, stream);
			this.list.add(node);
		}
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream) throws IOException {
		
		stream.writeInt(this.list.size());
		stream.write(this.NodeType);
		
		for(BDTNode node : this.list){
			node.writeNodeContent(writer, stream);
		}
	}
	
	@Override
	public void printTextualNodeRepresentationToPrintStream(PrintStream printer, int depth, boolean b) {
		printer.println(makeLine(" ",depth) + " ListNode '" + this.getName() + "' ("+this.list.size() + " entry/s)");
		
		for(int i = 0; i < this.list.size(); i++){
			this.list.get(i).printTextualNodeRepresentationToPrintStream(printer, depth + 1,b);
		}
	}
	
	public int add(BDTNode node) {
		this.list.add(node);
		return this.list.size() - 1;
	}
	
	public BDTNode get(int id){
		return this.list.get(id);
	}
	
	public List<BDTNode> getList(){
		return this.list;
	}

	@Override
	public BDTNode copy() {
		BDTNodeList copy = new BDTNodeList(this.getName(), NodeType);
		
		for(int i = 0; i < this.list.size(); i++){
			copy.add(this.list.get(i).copy());
		}
		
		return copy;
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_LIST";
	}

	@Override
	public String getContentStringRepresentation() {
		return "LIST_SIZE="+this.list.size();
	}
	
	@Override
	public void getChildrens(ArrayList<BDTNode> childs) {
		childs.addAll(this.list);
	}
}
