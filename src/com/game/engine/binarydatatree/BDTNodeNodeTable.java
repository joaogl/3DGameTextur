package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

/**A node that contains a table of entry's, similar to a MySQL table.**/
public class BDTNodeNodeTable extends BDTNode{
	private int[] IdHeader;
	private String[] NameHeader;
	private ArrayList<BDTNode[]> Entrys;
	
	public BDTNodeNodeTable(String name, int[] idHeader,String[] nameHeader) {
		super(name,BDTNode.NT_NODETABLE);
		IdHeader = idHeader;
		NameHeader = nameHeader;
		Entrys = new ArrayList<BDTNode[]>(4);
		
		if(NameHeader == null){
			NameHeader = new String[IdHeader.length];
			Arrays.fill(NameHeader,"");
		}

		if(IdHeader.length != NameHeader.length)
			throw new IllegalArgumentException("ID Header and Name header must have the same Size!");
	}
	
	@Override
	public boolean isNodeContainer() {
		return true;
	}
	
	
	@Override
	public int getPayloadSize() {
		return -1;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream)
			throws IOException, Exception {
		short headerSize = stream.readShort();
		IdHeader = new int[headerSize];
		NameHeader = new String[headerSize];

		for(int n = 0; n < IdHeader.length; n++){
			IdHeader[n] = stream.read();
			NameHeader[n] = stream.readUTF();
		}
		
		int totalEntryCount = stream.readInt();
		for(int entryCount = 0; entryCount < totalEntryCount; entryCount++){
			
			BDTNode[] entry = new BDTNode[IdHeader.length];
			
			for(int i = 0; i < IdHeader.length; i++){
				BDTNode node = BDTNode.createNode(this.IdHeader[i], NameHeader[i]+entryCount);
				node.readNodeContent(reader, stream);
				
				entry[i] = node;
			}
			
			this.Entrys.add(entry);
		}
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream)
			throws IOException {
		stream.writeShort(IdHeader.length);
		for(int n = 0; n < IdHeader.length; n++){
			stream.write(IdHeader[n]);
			stream.writeUTF(NameHeader[n]);
		}
		
		stream.writeInt(this.Entrys.size());
		for(BDTNode[] nodeEntry : this.Entrys){
			for(int i = 0; i < IdHeader.length; i++){
				nodeEntry[i].writeNodeContent(writer, stream);
			}
		}
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(
			PrintStream printer, int depth, boolean b) {
		String d = makeLine(" ",depth);
		printer.println(d + " NodeTable '" + this.getName() + "' ("+this.Entrys.size() + " entry/s)");
		
		String headerI = Arrays.toString(IdHeader);
		String headerN = Arrays.toString(NameHeader);
		
		printer.println(d + "  NodeTable-IdHeader   " + headerI);
		printer.println(d + "  NodeTable-NameHeader " + headerN);
		
		//Unsorted Output
		int entryCount = 0;
		for(BDTNode[] entry : this.Entrys){
			printer.println(d + "   TableEntry"+entryCount+"[");
			for(BDTNode node : entry){
				printer.print(d + "    -[" + node.getNodeTypeLabel() + "] - [" + node.getContentStringRepresentation() + "]\n");
			}
			printer.println(d + "   ]");
			entryCount++;
		}
	}

	@Override
	public BDTNode copy() {
		throw new UnsupportedOperationException("Copying node-tables is not yet implemented.");
	}

	@Override
	public String getNodeTypeLabel() {
		return "NT_NODETABLE";
	}
	
	public void addEntry(BDTNode...bdtNodes){
		BDTNode[] entry = new BDTNode[this.IdHeader.length];
		
		int n = 0;
		for(BDTNode node : bdtNodes){
			entry[n++] = node;
		}
		
		if(n != this.IdHeader.length)
			throw new IllegalArgumentException("Illegal Entry Argument Count! ("+n+" != " + this.IdHeader.length + ")");
		
		this.Entrys.add(entry);
	}
	
	public BDTNode[] getEntry(int index){
		return this.Entrys.get(index);
	}
	
	public ArrayList<BDTNode[]> getEntrys(){
		return this.Entrys;
	}
	
	public int getTableLength(){
		return this.Entrys.size();
	}

	@Override
	public String getContentStringRepresentation() {
		return "ENTRYS="+this.Entrys.size();
	}
	
}
