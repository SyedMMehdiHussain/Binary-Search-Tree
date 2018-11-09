import java.io.*;
import java.util.*;

/** Class that represents a student  
 *  @author  COSC 311, Winter 2017
 **/
public class Student {
	
	/** A student has a first name (fName), a last name (lName),
	 * an id (ID), and a gpa (GPA).
	 */
	private final int LENGTH = 20;
	private String fName;
	private String lName;
	private int ID;
	private double GPA;
	private final int RECSIZE = 92;
	

	/**
	 * Set the student info
	 * @param first the first name
	 * @param last the last name
	 * @param id the ID
	 * @param gpa the GPA
	 */
	public void setData (String first, String last, int id, double gpa){
		fName = first;
		lName = last;
		ID = id;
		GPA = gpa;
		fName = pad (fName, LENGTH);
	}

	/**
	 * get the student record size
	 * @return an int representing the record size
	 */
	public int size(){
		return RECSIZE;
	}
	
	/**
	 * Determine if two students have the same ID
	 * @return true if same ID, otherwise return false
	 */
	public boolean equals(Object other){
		if (other == null)
			return false;
		else if (getClass() != other.getClass())
			return false;
		else
			return ID == ((Student)other).ID;
	}

	/**
	 * Write a student record to the random access file
	 * @param out the random access file
	 * @throws IOException
	 */
	public void writeToFile(RandomAccessFile out) throws IOException {
		writeString(out, fName,LENGTH );
		writeString(out, lName, LENGTH);
		out.writeInt(ID);
		out.writeDouble(GPA);
		
	}
	
	/**
	 * Read a student record from the random access file
	 * @param in the random access file
	 * @throws IOException
	 */
	public void readFromFile(RandomAccessFile in)throws IOException {
		fName = readString(in, LENGTH);
		lName = readString(in, LENGTH);
		ID = in.readInt();
		GPA = in.readDouble();
	}
	
	/**
	 * Read size characters from the random access file
	 * @param in the random access file
	 * @param size the number of characters to read
	 * @return a string representing the characters read
	 * @throws IOException
	 */
	private String readString(RandomAccessFile in, int size)throws IOException{
		char [] str = new char [size];
		for (int i =0; i<str.length; i++)
			str[i] = in.readChar();
		return new String(str);
	}
	
	/**
	 * Write size characters to the random access file
	 * @param out the random access file
	 * @param name the characters to write
	 * @param size number of character s to write
	 * @throws IOException
	 */
	private void writeString(RandomAccessFile out, String name, int size)throws IOException{
		char [] str = new char [size];
		str = name.toCharArray();
		for (int i=0; i<str.length; i++)
			out.writeChar(str[i]);
	}
	/**
	 * Read a student info from a text file and pad the first and last names with a blank(s) if needed
	 * @param in the text file
	 * @throws IOException
	 */
	public void readFromTextFile(Scanner in) throws IOException {
		fName = in.next();
		lName = in.next();
		ID = in.nextInt();
		GPA = in.nextDouble();
		fName = pad (fName, LENGTH);
		lName = pad (lName, LENGTH);
	}
	
	/**
	 * Padding a string with trailing blank(s) 
	 * @param s the string
	 * @param size the length of the resulting string
	 * @return a string of length size
	 */
	public static String pad (String s, int size){
		for (int i = s.length(); i <size; i++)
			s += ' ';
		return s;
	}
	
	/**
	 * Create and return a string that represents a student 
	 * @return a string representing a student
	 */
	public String toString(){
		return String.format("%-20s",fName) + String.format("%-20s",lName) +  
				   String.format("%-10s",ID) + String.format("%-10s",GPA);
	}

//getter for first name
	public String getfName() {
		return fName;
	}

	//setter for first name
	public void setfName(String fName) {
		this.fName = fName;
		this.fName = pad (fName, LENGTH);
	}

	//getter for last name
	public String getlName() {
		return lName;
	}

	//setter for last name
	public void setlName(String lName) {
		this.lName = lName;
		this.lName = pad (lName, LENGTH);
	}

	//getter for ID
	public int getID() {
		return ID;
	}

	//setter for ID
	public void setID(int iD) {
		ID = iD;
	}

	//getter for gpa
	public double getGPA() {
		return GPA;
	}

	//setter for gpa
	public void setGPA(double gPA) {
		GPA = gPA;
	}


	public void setData(String newFirstName, String newLastName, double newGpa) {
		fName = newFirstName;
		lName = newLastName; 
		GPA = newGpa;
		fName = pad (fName, LENGTH);
		lName = pad (lName, LENGTH);
		
	}


}
