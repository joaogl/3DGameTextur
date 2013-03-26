package com.game.engine.binarydatatree.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.game.engine.binarydatatree.BDTNode;
import com.game.engine.binarydatatree.BDTNodeBoolean;
import com.game.engine.binarydatatree.BDTNodeCompound;
import com.game.engine.binarydatatree.BDTNodeInt;
import com.game.engine.binarydatatree.BDTNodeList;
import com.game.engine.binarydatatree.BDTNodeLong;
import com.game.engine.binarydatatree.BDTNodeNodeTable;
import com.game.engine.binarydatatree.BDTNodeString;
import com.game.engine.binarydatatree.BDTNodeUShort;
import com.game.engine.binarydatatree.BDTReader;
import com.game.engine.binarydatatree.BDTWriter;

/**Class to test the BDT System.**/
public class BDTSystemTest {
	public static void main(String[] args){
		
		System.out.println("// Tree Creation");
		
		BDTNodeCompound comp = new BDTNodeCompound("Root");
		
		BDTNodeCompound lvl = new BDTNodeCompound("Level");
		lvl.putNode(new BDTNodeUShort("char0",'L'));
		lvl.putNode(new BDTNodeUShort("char1",'o'));
		lvl.putNode(new BDTNodeUShort("char2",'L'));
		lvl.putNode(new BDTNodeInt("int0",1337));
		lvl.putNode(new BDTNodeInt("int1",-1337));
		lvl.putNode(new BDTNodeInt("int2",20000000));
		lvl.putNode(new BDTNodeLong("time",34573457348975L));
		
		{
			int[] IdHeader = new int[]{BDTNode.NT_INT,BDTNode.NT_STRING,BDTNode.NT_STRING,BDTNode.NT_LONG};
			String[] NameHeader = new String[]{"UserID","Username","Password","Money"};
			BDTNodeNodeTable table = new BDTNodeNodeTable("tableName",IdHeader,NameHeader);
			
			table.addEntry(new BDTNodeInt(0),new BDTNodeString("","Mr Test"),new BDTNodeString("","secretPassword"),new BDTNodeLong(1337L));
			table.addEntry(new BDTNodeInt(0),new BDTNodeString("","Mr Test"),new BDTNodeString("","secretPassword"),new BDTNodeLong(1337L));
			table.addEntry(new BDTNodeInt(0),new BDTNodeString("","Mr Test"),new BDTNodeString("","secretPassword"),new BDTNodeLong(1337L));
			table.addEntry(new BDTNodeInt(0),new BDTNodeString("","Mr Test"),new BDTNodeString("","secretPassword"),new BDTNodeLong(1337L));
			table.addEntry(new BDTNodeInt(0),new BDTNodeString("","Mr Test"),new BDTNodeString("","secretPassword"),new BDTNodeLong(1337L));
			
			lvl.putNode(table);
		}
		
		BDTNodeList list = new BDTNodeList("truth", BDTNode.NT_BOOL);
		list.add(new BDTNodeBoolean(true));
		list.add(new BDTNodeBoolean(true));
		list.add(new BDTNodeBoolean(false));
		list.add(new BDTNodeBoolean(true));
		
		comp.putNode(list);
		comp.putNode(lvl);
		comp.putByte("byteA",27);
		comp.putByte("byteB",42);
		comp.putByte("byteC",-127);
		comp.putNode(new BDTNodeString("Username","Mr Test"));
		comp.putNode(new BDTNodeString("Password","testomatiko"));
		comp.printTextualNodeRepresentationToPrintStream(System.out,0,true);
		
		System.out.println("// Tree Stream Writing");
		
		{
			try {
				BDTWriter writer = new BDTWriter(new FileOutputStream(new File("test.dat")),false, true);
				writer.writeNode(comp);
				writer.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("// Tree Stream Reading");
		{
			try {
				BDTReader reader = new BDTReader(new FileInputStream(new File("test.dat")));
				BDTNode node = reader.readNode();
				int rb = reader.getTotalReadNodes();
				
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				
				System.out.println("// Tree Console Viewing ("+rb+" Nodes)");
				node.printTextualNodeRepresentationToPrintStream(System.out,0,true);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
