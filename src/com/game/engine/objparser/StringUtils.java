package com.game.engine.objparser;

import java.util.*;

public class StringUtils {

	public static void printErrMsg(String methodName, String errorMsg, int mCount, char message[]) {
		System.err.println("ERROR: " + methodName + ": " + errorMsg);
		System.err.print("ERROR: " + methodName + ": msg=\\");
		for (int loopi = 0; loopi < message.length; loopi++) {
			System.err.print(message[loopi]);
		}
		System.err.println("\\");
		System.err.print("ERROR: " + methodName + ":      ");
		for (int i = 0; i < mCount; i++) {
			System.err.print(" ");
		}
		System.err.println("^");
	}

	public static int skipWhiteSpace(int mCount, char messageChars[], String errMsg) {
		while (mCount < messageChars.length) {
			if (messageChars[mCount] == ' ' || messageChars[mCount] == '\n' || messageChars[mCount] == '\t') {
				mCount++;
			} else {
				break;
			}
		}
		if (errMsg != null) {
			if (mCount >= messageChars.length) {
				printErrMsg("RString.skipWhiteSpace", errMsg, mCount, messageChars);
				return -1;
			}
		}
		return mCount;
	}

	public static float[] parseFloatList(int numFloats, String list, int startIndex) {
		if (list == null) {
			return null;
		}
		if (list.equals("")) {
			return null;
		}

		float[] returnArray = new float[numFloats];
		int returnArrayCount = 0;

		char listChars[];
		listChars = new char[list.length()];
		list.getChars(0, list.length(), listChars, 0);
		int listLength = listChars.length;

		int count = startIndex;
		int itemStart = startIndex;
		int itemEnd = 0;
		int itemLength = 0;

		while (count < listLength) {
			itemEnd = skipWhiteSpace(count, listChars, null);
			count = itemEnd;
			if (count >= listLength) {
				break;
			}
			itemStart = count;
			itemEnd = itemStart;
			while (itemEnd < listLength) {
				if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
					itemEnd++;
				} else {
					break;
				}
			}
			itemLength = itemEnd - itemStart;
			returnArray[returnArrayCount++] = Float.parseFloat(new String(listChars, itemStart, itemLength));
			if (returnArrayCount >= numFloats) {
				break;
			}

			count = itemEnd;
		}
		return returnArray;
	}

	public static int[] parseIntList(String list, int startIndex) {
		if (list == null) {
			return null;
		}
		if (list.equals("")) {
			return null;
		}

		ArrayList<Integer> returnList = new ArrayList<Integer>();

		char listChars[];
		listChars = new char[list.length()];
		list.getChars(0, list.length(), listChars, 0);
		int listLength = listChars.length;

		int count = startIndex;
		int itemStart = startIndex;
		int itemEnd = 0;
		int itemLength = 0;

		while (count < listLength) {
			itemEnd = skipWhiteSpace(count, listChars, null);
			count = itemEnd;
			if (count >= listLength) {
				break;
			}
			itemStart = count;
			itemEnd = itemStart;
			while (itemEnd < listLength) {
				if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
					itemEnd++;
				} else {
					break;
				}
			}
			itemLength = itemEnd - itemStart;
			returnList.add(Integer.parseInt(new String(listChars, itemStart, itemLength)));

			count = itemEnd;
		}

		int returnArray[] = new int[returnList.size()];
		for (int loopi = 0; loopi < returnList.size(); loopi++) {
			returnArray[loopi] = returnList.get(loopi);
		}
		return returnArray;
	}

	public static int[] parseListVerticeNTuples(String list, int expectedValuesPerTuple) {
		if (list == null) {
			return null;
		}
		if (list.equals("")) {
			return null;
		}

		String[] vertexStrings = parseWhitespaceList(list);

		ArrayList<Integer> returnList = new ArrayList<Integer>();
		Integer emptyMarker = new Integer(BuilderInterface.EMPTY_VERTEX_VALUE);

		for (int loopi = 0; loopi < vertexStrings.length; loopi++) {
			parseVerticeNTuple(vertexStrings[loopi], returnList, emptyMarker, expectedValuesPerTuple);
		}

		int returnArray[] = new int[returnList.size()];
		for (int loopi = 0; loopi < returnList.size(); loopi++) {
			returnArray[loopi] = returnList.get(loopi);
		}
		return returnArray;
	}

	private static void parseVerticeNTuple(String list, ArrayList<Integer> returnList, Integer emptyMarker, int expectedValueCount) {
		String[] numbers = parseList('/', list);
		int foundCount = 0;

		int index = 0;
		while (index < numbers.length) {
			if (numbers[index].trim().equals("")) {
				returnList.add(emptyMarker);
			} else {
				returnList.add(Integer.parseInt(numbers[index]));
			}
			foundCount++;
			index++;
		}
		while (foundCount < expectedValueCount) {
			returnList.add(emptyMarker);
			foundCount++;
		}
	}

	public static String[] parseList(char delim, String list) {
		if (list == null) {
			return null;
		}
		if (list.equals("")) {
			return null;
		}

		ArrayList<String> returnVec = new ArrayList<String>();
		String[] returnArray = null;

		char listChars[];
		listChars = new char[list.length()];
		list.getChars(0, list.length(), listChars, 0);

		int count = 0;
		int itemStart = 0;
		int itemEnd = 0;
		String newItem = null;

		while (count < listChars.length) {
			count = itemEnd;
			if (count >= listChars.length) {
				break;
			}
			itemStart = count;
			itemEnd = itemStart;
			while (itemEnd < listChars.length) {
				if (delim != listChars[itemEnd]) {
					itemEnd++;
				} else {
					break;
				}
			}
			newItem = new String(listChars, itemStart, itemEnd - itemStart);
			itemEnd++;
			count = itemEnd;
			returnVec.add(newItem);
		}

		returnArray = new String[1];
		returnArray = (String[]) returnVec.toArray((Object[]) returnArray);
		return returnArray;
	}

	public static String[] parseWhitespaceList(String list) {
		if (list == null) {
			return null;
		}
		if (list.equals("")) {
			return null;
		}

		ArrayList<String> returnVec = new ArrayList<String>();
		String[] returnArray = null;

		char listChars[];
		listChars = new char[list.length()];
		list.getChars(0, list.length(), listChars, 0);

		int count = 0;
		int itemStart = 0;
		int itemEnd = 0;
		String newItem = null;

		while (count < listChars.length) {
			itemEnd = skipWhiteSpace(count, listChars, null);
			count = itemEnd;
			if (count >= listChars.length) {
				break;
			}
			itemStart = count;
			itemEnd = itemStart;
			while (itemEnd < listChars.length) {
				if ((listChars[itemEnd] != ' ') && (listChars[itemEnd] != '\n') && (listChars[itemEnd] != '\t')) {
					itemEnd++;
				} else {
					break;
				}
			}
			newItem = new String(listChars, itemStart, itemEnd - itemStart);
			itemEnd++;
			count = itemEnd;
			returnVec.add(newItem);
		}
		returnArray = new String[1];
		returnArray = (String[]) returnVec.toArray((Object[]) returnArray);
		return returnArray;
	}
}