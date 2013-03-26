package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**A Node that contains an undefined amount of non-equal-type Nodes.**/
public class BDTNodeCompound extends BDTNode{
	private HashMap<String,BDTNode> childs;
	
	/**Creates a new BDTCompoundNode with the given name.**/
	public BDTNodeCompound(String name){
		super(name,BDTNode.NT_COMPOUND);
		this.childs = new HashMap<String,BDTNode>();
	}

	/**Creates a new BDTCompoundNode with a empty name.**/
	public BDTNodeCompound(){
		this("");
	}
	
	@Override
	public boolean isNodeContainer() {
		return true;
	}
	
	@Override
	public int getPayloadSize() {
		int size = 0;

		for(String name : this.childs.keySet()){
			BDTNode node = this.childs.get(name);
			size += node.getPayloadSize();
		}
		
		return size;
	}

	@Override
	public void readNodeContent(IBDTNodeReader reader, DataInputStream stream) throws Exception {
		
		while(true){
			BDTNode node = reader.readNode();
			
			if(node.getType() == BDTNode.NT_END)
				break;
			else
				this.childs.put(node.getName(),node);
		}
		
	}

	@Override
	public void writeNodeContent(IBDTNodeWriter writer, DataOutputStream stream) throws IOException {
		
		for(String name : this.childs.keySet()){
			BDTNode node = this.childs.get(name);
			writer.writeNode(node);
		}
		writer.writeNode(new BDTNodeEnd());
	}

	@Override
	public void printTextualNodeRepresentationToPrintStream(PrintStream printer,int depth, boolean b) {
		
		printer.println(makeLine(" ",depth) + " CompoundNode '" + this.getName() + "' ("+this.childs.size() + " child/s)");
		
		if(b){
			//Sorted Output
			ArrayList<BDTNode> childs_copy = new ArrayList<BDTNode>();
			for(String name : this.childs.keySet()){
				childs_copy.add(this.childs.get(name));
			}
			
			Collections.sort(childs_copy);
			
			for(BDTNode node : childs_copy){
				node.printTextualNodeRepresentationToPrintStream(printer, depth + 1,b);
			}
		}else{
			//Unsorted Output
			for(String name : this.childs.keySet()){
				BDTNode node = this.childs.get(name);
				node.printTextualNodeRepresentationToPrintStream(printer, depth + 1,b);
			}
		}
	}
	
	
	
	/**Put's the given Node in this Compound.**/
	public void putNode(BDTNode node) {
		if(node == null)
			throw new IllegalArgumentException("Input cannot be Null!");
		
		this.childs.put(node.getName(),node);
	}
	/**@return The node with the given name or null if it does not exist.**/
	public BDTNode getNode(String name){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		return this.childs.get(name);
	}
	
	
	
	
	
	/**Creates and puts/replaces the given named node with the given value.**/
	public void putBool(String name,boolean value){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeBoolean(name,value));
	}
	/**@return The value of the given node-name or the given default value in case the node does not exist.**/
	public boolean getBool(String name,boolean def){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		if(this.childs.containsKey(name)){
			BDTNode node = this.childs.get(name);
			if(node.getType() == BDTNode.NT_BOOL){
				BDTNodeBoolean rnode = (BDTNodeBoolean) node;
				return rnode.getValue();
			}
		}
		return def;
	}
	
	
	
	

	/**Creates and puts/replaces the given named node with the given value.**/
	public void putByte(String name,int i){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeSByte(name,(byte) i));
	}
	/**@return The value of the given node-name or the given default value in case the node does not exist.**/
	public byte getByte(String name,byte def){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		if(this.childs.containsKey(name)){
			BDTNode node = this.childs.get(name);
			if(node.getType() == BDTNode.NT_SBYTE){
				BDTNodeSByte rnode = (BDTNodeSByte) node;
				return rnode.getValue();
			}
		}
		return def;
	}

	/**Creates and puts/replaces the given named node with the given value.**/
	public void putInt(String name,int i){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeInt(name,i));
	}
	/**@return The value of the given node-name or the given default value in case the node does not exist.**/
	public int getInt(String name,int def){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		if(this.childs.containsKey(name)){
			BDTNode node = this.childs.get(name);
			if(node.getType() == BDTNode.NT_INT){
				BDTNodeInt rnode = (BDTNodeInt) node;
				return rnode.getValue();
			}
		}
		return def;
	}
	
	
	

	/**Creates and puts/replaces the given named node with the given value.**/
	public void putFloat(String name,float i){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeFloat(name,i));
	}
	/**@return The value of the given node-name or the given default value in case the node does not exist.**/
	public float getFloat(String name,float def){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		if(this.childs.containsKey(name)){
			BDTNode node = this.childs.get(name);
			if(node.getType() == BDTNode.NT_FLOAT){
				BDTNodeFloat rnode = (BDTNodeFloat) node;
				return rnode.getValue();
			}
		}
		return def;
	}
	

	/**Creates and puts/replaces the given named node with the given value.**/
	public void putDouble(String name,double i){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeDouble(name,i));
	}
	/**@return The value of the given node-name or the given default value in case the node does not exist.**/
	public double getDouble(String name,double def){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		if(this.childs.containsKey(name)){
			BDTNode node = this.childs.get(name);
			if(node.getType() == BDTNode.NT_DOUBLE){
				BDTNodeDouble rnode = (BDTNodeDouble) node;
				return rnode.getValue();
			}
		}
		return def;
	}

	/**Creates and puts/replaces the given named node with the given value.**/
	public void putString(String name,String i){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeString(name,i));
	}
	/**@return The value of the given node-name or the given default value in case the node does not exist.**/
	public String getString(String name,String def){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		if(this.childs.containsKey(name)){
			BDTNode node = this.childs.get(name);
			if(node.getType() == BDTNode.NT_STRING){
				BDTNodeString rnode = (BDTNodeString) node;
				return rnode.getValue();
			}
		}
		return def;
	}

	/**Creates and puts/replaces the given named node with the given value.**/
	public void putLong(String name,long i){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeLong(name,i));
	}
	/**@return The value of the given node-name or the given default value in case the node does not exist.**/
	public long getLong(String name,long def){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		if(this.childs.containsKey(name)){
			BDTNode node = this.childs.get(name);
			if(node.getType() == BDTNode.NT_LONG){
				BDTNodeLong rnode = (BDTNodeLong) node;
				return rnode.getValue();
			}
		}
		return def;
	}
	
	
	/**Creates a new byteArray-node with the given name and value/s and puts it into this compound.**/
	public void putByteArray(String name,byte... vals){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeByteArray(name,vals));
	}
	
	/**Creates a new largeByteArray-node with the given name and value/s and puts it into this compound.**/
	public void putLargeByteArray(String name,byte... vals){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeLargeByteArray(name,vals));
	}
	
	/**Creates a new intArray-node with the given name and value/s and puts it into this compound.**/
	public void putIntArray(String name,int... is){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeIntArray(name,is));
	}
	
	/**Creates a new floatArray-node with the given name and value/s and puts it into this compound.**/
	public void putFloatArray(String name,float... is){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeFloatArray(name,is));
	}
	
	/**Creates a new doubleArray-node with the given name and value/s and puts it into this compound.**/
	public void putDoubleArray(String name,double... is){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeDoubleArray(name,is));
	}
	
	/**Creates a new floatArray-node with the given name and value/s and puts it into this compound.**/
	public void putLongArray(String name,long... is){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeLongArray(name,is));
	}
	
	/**Creates a new vector2D-node with the given name and value/s and puts it into this compound.**/
	public void putVector2d(String name,double x,double y){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeVector2d(name,x,y));
	}
	
	/**Creates a new vector2f-node with the given name and value/s and puts it into this compound.**/
	public void putVector2f(String name,float x,float y){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeVector2f(name,x,y));
	}
	
	
	/**Creates a new vector3D-node with the given name and value/s and puts it into this compound.**/
	public void putVector3d(String name,double x,double y,double z){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeVector3d(name,x,y,z));
	}
	
	/**Creates a new vector3f-node with the given name and value/s and puts it into this compound.**/
	public void putVector3f(String name,float x,float y,float z){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeVector3f(name,x,y,z));
	}
	
	/**Creates a new vector4D-node with the given name and value/s and puts it into this compound.**/
	public void putVector4d(String name,double x,double y,double z,double w){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeVector4d(name,x,y,z,w));
	}
	
	/**Creates a new vector4f-node with the given name and value/s and puts it into this compound.**/
	public void putVector4f(String name,float x,float y,float z,float w){
		if(name == null)
			throw new IllegalArgumentException("Name cannot be Null!");
		
		this.childs.put(name, new BDTNodeVector4f(name,x,y,z,w));
	}
	
	/**Removes the node with the given name.
	 * @return The removed node or null if there wasn't a node with that name.**/
	public BDTNode removeNode(String name){
		return this.childs.remove(name);
	}
	
	/**Returns a set that contains all names of all nodes in this compound.**/
	public Set<String> getNameList(){
		return this.childs.keySet();
	}
	
	/**Returns the internal Map that is used to store this compounds nodes.**/
	public HashMap<String,BDTNode> getInternalMap(){
		return this.childs;
	}
	
	@Override
	/**Warning: Using the copy method on this node will make a deep copy of all its children's too!**/
	public BDTNode copy() {
		BDTNodeCompound comp = new BDTNodeCompound(this.getName());
		
		for(String name : this.childs.keySet()){
			BDTNode node = this.childs.get(name);
			comp.putNode(node.copy());
		}
		
		return comp;
	}
	@Override
	public String getNodeTypeLabel() {
		return "NT_COMPOUND";
	}
	
	@Override
	public String getContentStringRepresentation() {
		return "CHILDREN_COUNT="+this.childs.size();
	}
	
	@Override
	public void getChildrens(ArrayList<BDTNode> childs) {
		childs.addAll(this.childs.values());
	}
}
