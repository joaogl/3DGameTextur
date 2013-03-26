/**
Copyright (c) 2013, Longor1996
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

    Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
    Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
**/

package com.game.engine.binarydatatree;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**Abstract BinaryDataTree Node Class.<br>
 * If you wan't to make your own node-type, it must extend this class.
 * You can then register you custom node-type in the customIdMappings HashMap.
 * Most use-cases should already be covered by the standard node-types.
 * <hr>
 * To write a BDT to a stream, you can either use the BDTWriter or you could make your own writer by implementing IBDTNodeWriter.
 * To read a BDT from a stream, you can either use the BDTReader or you could make your own reader by implementing IBDTNodeReader.
 * The highest possible node-type identifier is 255 (0xFF), if need more you have to make your own writer and reader for that.
 * <hr>
 * @author Longor1996
 * @version 1.0**/
public abstract class BDTNode implements Comparable<BDTNode> {
	//BDT Main Type Constants
	/**Internal Type. Do not use, it will break your Data.**/
	public static final int NT_END = 0x00;
	/**
	 * Compound Node that can have an infinite amount of child-nodes.<br>
	 * It can contain all kinds of nodes as children.
	 **/
	public static final int NT_COMPOUND = 0x01;
	/**
	 * List Node that can have up to 2.147.483.647 child-nodes.<br>
	 * All child-nodes are of the same type.<br>
	 * <br>
	 * All child's are written/read without their header,<br>
	 * so they do not have names.<br>
	 **/
	public static final int NT_LIST = 0x02;
	/**
	 * List Node that can have up to infinite child-nodes.<br>
	 * All child-nodes are of the same type.<br>
	 * <br>
	 * All child's are written/read without their header,<br>
	 * so they do not have names.<br>
	 **/
	public static final int NT_LONGLIST = 0x03;
	/**A table that can contain up to 2.147.483.647 multi-node entry's.<br>
	 * The type of the entry's is specified by a given header that is saved with the table.<br>
	 * All node entry's are written without their header!<br>
	 * <br>
	 * Example:<br>
	 * <pre>
int[] IdHeader = new int[]{NT_INT,NT_STRING,NT_STRING,NT_LONG};
String[] NameHeader = new String[]{"UserID","Username","Password","Money"};
BDTNodeNodeTable table = new BDTNodeNodeTable("tableName",IdHeader,NameHeader);

table.addEntry(new BDTNodeInt(0),new BDTNodeString("","Mr Lol"),new BDTNodeString("","youCanNeverGuessThisSecretPassword"),new BDTNodeLong(1337L));
	 * </pre>
	 **/
	public static final int NT_NODETABLE = 0x04;
	
	//BDT Primitive Data Type Constants
	/**Boolean**/
	public static final int NT_BOOL = 0x10;
	/**Signed Byte**/
	public static final int NT_SBYTE = 0x11;
	/**Unsigned Byte**/
	public static final int NT_UBYTE = 0x12;
	/**2-byte Signed Short**/
	public static final int NT_SSHORT = 0x13;
	/**2-byte Unsigned Short**/
	public static final int NT_USHORT = 0x14;
	/**4-Byte Signed Integer**/
	public static final int NT_INT = 0x15;
	/**4-Byte Signed Single-Precision Floating Point Number**/
	public static final int NT_FLOAT = 0x16;
	/**8-Byte Signed Double-Precision Floating Point Number**/
	public static final int NT_DOUBLE = 0x17;
	/**8-Byte Signed Long Integer**/
	public static final int NT_LONG = 0x18;
	
	//BDT BigData Primitives Type Constants
	/**
	 * UTF-8 Character Array (Aka: String).<br>
	 * Can be at max 65.535 Units long!
	 **/
	public static final int NT_STRING = 0x20;
	/**
	 * A simple Small-Scale Byte Array.<br>
	 * The Maximum Size is 65.535 Units (64 Kilobyte).
	 **/
	public static final int NT_SMALL_BYTEARRAY = 0x21;
	/**
	 * A simple Large-Scale Byte Array.<br>
	 * The Maximum Size is 2.147.483.647 Units (2 Gigabyte).
	 **/
	public static final int NT_LARGE_BYTEARRAY = 0x22;
	/**
	 * A simple Large-Scale (4-Byte Signed) Integer Array.<br>
	 * The Maximum Size is 2.147.483.647 Units (4 Gigabyte).
	 **/
	public static final int NT_INT_ARRAY = 0x23;
	/**
	 * A simple Large-Scale (4-Byte Signed) Single-Precision Floating Point Number Array.<br>
	 * The Maximum Size is 2.147.483.647 Units (4 Gigabyte).
	 **/
	public static final int NT_FLOAT_ARRAY = 0x24;
	/**
	 * A simple Large-Scale (8-Byte Signed) Double-Precision Floating Point Number Array.<br>
	 * The Maximum Size is 2.147.483.647 Units (8 Gigabyte).
	 **/
	public static final int NT_DOUBLE_ARRAY = 0x25;
	/**
	 * A simple Large-Scale (8-Byte Signed) Long Integer Array.<br>
	 * The Maximum Size is 2.147.483.647 Units (8 Gigabyte).
	 **/
	public static final int NT_LONG_ARRAY = 0x26;
	/**
	 * A node that can hold a BigInteger.
	 **/
	public static final int NT_BIGINT = 0x27;
	
	//BDT Special Purpose Type Constants
	/**A 2-Component Vector made out of Signed Single-Precision Floating Point Values. (Size: 8 Bytes)**/
	public static final int NT_VECTOR2F = 0x40;
	/**A 2-Component Vector made out of Signed Double-Precision Floating Point Values. (Size: 16 Bytes)**/
	public static final int NT_VECTOR2D = 0x41;
	/**A 3-Component Vector made out of Signed Single-Precision Floating Point Values. (Size: 12 Bytes)**/
	public static final int NT_VECTOR3F = 0x42;
	/**A 3-Component Vector made out of Signed Double-Precision Floating Point Values. (Size: 24 Bytes)**/
	public static final int NT_VECTOR3D = 0x43;
	/**A 4-Component Vector made out of Signed Single-Precision Floating Point Values. (Size: 16 Bytes)**/
	public static final int NT_VECTOR4F = 0x44;
	/**A 4-Component Vector made out of Signed Double-Precision Floating Point Values. (Size: 32 Bytes)**/
	public static final int NT_VECTOR4D = 0x45;
	
	
	/**A RGB Picture. (Size: Width*Height*3 Bytes) NOT YET IMPLEMENTED!**/
	public static final int NT_IMAGE_RGB = 0x50;
	/**A RGBA Picture. (Size: Width*Height*4 Bytes)**/
	public static final int NT_IMAGE_RGBA = 0x51;
	/**A Grayscale Picture. (Size: Width*Height Bytes) NOT YET IMPLEMENTED!**/
	public static final int NT_IMAGE_GRAYSCALE = 0x52;
	
	/**A custom ID Mapping HashMap.<br>
	 * If you wan't to add you own node types to the system,<br>
	 * just put them into this HashMap!<br>
	 * <br>
	 * Like: <i>BDTNode.customIdMappings.put(YourID,YourClass.class);</i><br>**/
	public static final HashMap<Integer,Class<BDTNode>> customIdMappings;
	static{
		customIdMappings = new HashMap<Integer,Class<BDTNode>>(1);
	}
	
	
	public static final BDTNode createNode(int type, String name) throws Exception{
		switch(type){
		case NT_COMPOUND: return new BDTNodeCompound(name);
		case NT_LIST: return new BDTNodeList(name, NT_END);
		case NT_LONGLIST: return new BDTNodeLongList(name,NT_END);
		case NT_NODETABLE: return new BDTNodeNodeTable(name,new int[0],null);
		
		//Single Byte
		case NT_BOOL: return new BDTNodeBoolean(name);
		case NT_SBYTE: return new BDTNodeSByte(name);
		case NT_UBYTE: return new BDTNodeUByte(name);
		
		//Primitive
		
		//2 Byte
		case NT_SSHORT: return new BDTNodeSShort(name);
		case NT_USHORT: return new BDTNodeUShort(name);
		
		//4 Byte
		case NT_INT: return new BDTNodeInt(name);
		case NT_FLOAT: return new BDTNodeFloat(name);
		
		//8 Byte
		case NT_DOUBLE: return new BDTNodeDouble(name);
		case NT_LONG: return new BDTNodeLong(name);
		
		//N Byte Types
		case NT_STRING: return new BDTNodeString(name);
		
			//Byte Arrays
		case NT_SMALL_BYTEARRAY: return new BDTNodeByteArray(name);
		case NT_LARGE_BYTEARRAY: return new BDTNodeLargeByteArray(name);
		
		//Big Primitive Arrays
		case NT_INT_ARRAY: return new BDTNodeIntArray(name);
		case NT_FLOAT_ARRAY: return new BDTNodeFloatArray(name);
		case NT_DOUBLE_ARRAY: return new BDTNodeDoubleArray(name);
		case NT_LONG_ARRAY: return new BDTNodeLongArray(name);
		
		//Big Value Data
		case NT_BIGINT: return new BDTNodeBigInteger(name);
		
		//Vector
		case NT_VECTOR2F: return new BDTNodeVector2f(name);
		case NT_VECTOR2D: return new BDTNodeVector2d(name);
		case NT_VECTOR3F: return new BDTNodeVector3f(name);
		case NT_VECTOR3D: return new BDTNodeVector3d(name);
		case NT_VECTOR4F: return new BDTNodeVector4f(name);
		case NT_VECTOR4D: return new BDTNodeVector4d(name);
		
		//Image
		case NT_IMAGE_RGBA: return new BDTNodeImageRGBA(name);
		
			default: {
				if(customIdMappings.containsKey(Integer.valueOf(type))){
					//Get the custom mapping for this id.
					Class<BDTNode> customClassMapping = customIdMappings.get(Integer.valueOf(type));
					try {
						//Try to get the Constructor ?(String) from the custom mapping.
						Constructor<BDTNode> constructor = customClassMapping.getConstructor(String.class);
						try {
							//Try to create a new instance with the constructor!
							return constructor.newInstance(name);
							
						} catch (InstantiationException e) {
							e.printStackTrace();
							throw new Exception("Unable to create Node from Custom Mapper '" + customClassMapping.getName() + "'.");
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							throw new Exception("Unable to create Node from Custom Mapper '" + customClassMapping.getName() + "'.");
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
							throw new Exception("Unable to create Node from Custom Mapper '" + customClassMapping.getName() + "'.");
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							throw new Exception("Unable to create Node from Custom Mapper '" + customClassMapping.getName() + "'.");
						}
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
						throw new UnsupportedOperationException("Incompatible Custom Mapping Constructor! ("+type+")");
					} catch (SecurityException e) {
						e.printStackTrace();
						throw new UnsupportedOperationException("Incompatible Custom Mapping Constructor! ("+type+")");
					}
					
				}else
				throw new UnsupportedOperationException("Node Type unknown! ("+type+")");
			}
		}
	}
	
	/**Internal protected method that is used for console output.**/
	protected static final String makeLine(String s,int n){
		StringBuffer buffer = new StringBuffer("");
		for(int i = 0; i < n; i++)
			buffer.append(s);
		return buffer.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	//BDT Node Constructor's
	
	/**Abstract Constructor**/
	protected BDTNode(String name,int type){
		this.node_name = name == null ? "" : name;
		this.node_type = type;
	}
	
	/**Abstract Constructor**/
	protected BDTNode(){
		this.node_name = null;
		this.node_type = NT_END;
	}
	
	
	
	
	
	
	
	
	
	
	//BDT Node Variable's
	
	/**
	 * The Name of the Node.<br>
	 * The Name can be Null or a length of 0.
	 **/
	private String node_name;
	/**
	 * The Type of the Node.<br>
	 * The Type is always between 0x00-0xFF.
	 **/
	private int node_type;
	
	/**
	 * The Parent of the Node
	 **/
	private BDTNode node_parent;
	
	
	
	
	
	
	
	
	
	
	
	//BDT Node Method's
	/**
	 * Get the name of this node.<br>
	 * Be aware that the name can be empty or null!
	 **/
	public String getName(){
		return this.node_name;
	}
	
	/**
	 * Get the Type Identifier of this node.
	 **/
	public int getType(){
		return this.node_type;
	}
	
	@Override
	/**
	 * Creates a short String describing the Node.
	 **/
	public String toString(){
		return this.node_name + "(" + this.node_type + ")";
	}
	
	@Override
	/**Compare Method used for sorting the nodes in a list.<br>
	 * The sorting works on top of the node's names.**/
	public int compareTo(BDTNode node){
		return this.node_name.compareToIgnoreCase(node.node_name);
	}
	
	/**
	 * This method is internally called recursively in some cases.<br>
	 * If this Method is called on a Multiple-Node-Children Node,<br>
	 * it will return the total calculated size of all payload's in this Node.<br>
	 * @return The Size of this Node's Payload, and it's Children, measured in Bytes.<br>
	 **/
	public abstract int getPayloadSize();
	
	/**Abstract Method used to read the node's content's.
	 * @throws IOException 
	 * @throws Exception **/
	public abstract void readNodeContent(IBDTNodeReader reader,DataInputStream stream) throws IOException, Exception;
	
	/**Abstract Method used to write the node's content's.**/
	public abstract void writeNodeContent(IBDTNodeWriter writer,DataOutputStream stream) throws IOException;
	
	/**Abstract Method to write a textual representation of this Node to the given printer.**/
	public abstract void printTextualNodeRepresentationToPrintStream(PrintStream printer,int depth, boolean sorted);
	
	/**Abstract Method that makes a deep copy of this node and it's contents.**/
	public abstract BDTNode copy();
	
	/**Abstract Method that returns the Type of the node as a String Label.**/
	public abstract String getNodeTypeLabel();
	
	/**Abstract Method that returns a string representation of this nodes content.**/
	public abstract String getContentStringRepresentation();
	
	public BDTNode getParent() {
		return this.node_parent;
	}
	public void setParent(BDTNode parent){
		this.node_parent = parent;
	}
	
	/**?**/
	public boolean isNodeContainer() {
		return false;
	}
	
	public void getChildrens(ArrayList<BDTNode> childs) {}
}
