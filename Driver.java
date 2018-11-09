import java.util.InputMismatchException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


/**
 * Project 5 HashTable using Binary Search Tree
 * @author Syed Muhammad Mehdi Hussain
 *
 *The Objective of this Program is to implement hashIndex with a binary search tree at each index, 
 *using a text file and then streaming into randomaccess file.
 */
public class Driver {

	static File temp;
	static RandomAccessFile raf;
	static Student rec;
	static int numOfrec=36;

	
	public static <E> void main(String[] args) throws IOException {
		System.out.println("*****Welcome to Hash Table ****");

	HashTable table = new HashTable();

		//we initialize here			
	
	File temp = null;
	RandomAccessFile raf = null;
	Student rec = null;
		

menu(raf,rec, table, temp);	
	}//end of main menu
	
	/***
	 * Main menu where user will be able to access the functions	
	 * @param raf
	 * @param rec
	 * @param bst
	 * @param temp
	 * @throws IOException
	 */
	private static void menu(RandomAccessFile raf, Student rec,HashTable<Pair> table,File temp) throws IOException {
		/*
		 * Asks the user for input
		 * we have a method for each of the 9 options 
		 */
		System.out.println("\n1- Make a random-access file\n"
				+ "2- Display a random-access file\n"
				+ "3- Build the index\n"
				+ "4- Display the index\n"
				+ "5- Retrieve a record\n"
				+ "6- Modify a record\n"
				+ "7- Add a new record\n"
				+ "8- Delete a record\n"
				+ "9- Quit ");

		/*
		 *  it takes the user input and goes to that method, if any invalid input is made,
		 *  the message will be given and the menu be displayed again		
		 */
		Scanner scan = new Scanner(System.in);
		try {
		boolean bError = true;
		int	 var = scan.nextInt();
		switch (var) {
		case 1:
		{
			createRandomAccessFile(table, raf, rec, temp);
			break;
		}

		case 2:
		{
			displayRandomAccessFile(raf, rec,table, temp);
			break;
		}

		case 3:
		{
			createIndex(raf,rec,table, temp);
			break;
		}

		case 4:
		{
			displayIndex(raf,rec, table, temp);
			break;
		}

		case 5:
		{
			retrieve(raf,rec,table, temp);
			break;	
		}

		case 6:
		{
			updateRecord(raf,rec,table,temp);
			break;
		}
		case 7:
		{
			addRecord(raf,rec,table, temp);
			break;
		}		
		case 8:
		{
			deleteRecord(raf,rec,table, temp);
			break;
		}
		case 9:
		{
			exit(temp, table);

		}
		bError = false;	
		}
		}
		catch (InputMismatchException e) {
			System.out.println("Invalid Value");
			menu(raf, rec, table, temp);
		}

	}	
	/***********************************************************************************
	 **********(1) Making a Random access file from a text file ****************************
	 * @throws IOException
	 */
	
	private static void createRandomAccessFile(HashTable<Pair> table,RandomAccessFile raf,Student rec, File temp) throws IOException {
		
	try {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.print("Input filename:");
		String filename = scan.nextLine();

		//take input from user the text file name
		Scanner fin = new Scanner(new FileInputStream(filename));

		System.out.print("Name the Randomaccess File you want to create: ");


			String rafName= scan.next();

			temp = new File(rafName);
			if (temp.exists()){
			     temp.delete();
			 }  
			

			raf = new RandomAccessFile(temp, "rw");

			rec = new Student();
			while (fin.hasNext()) {
				rec.readFromTextFile(fin);
				rec.writeToFile(raf);
				}
	} catch (FileNotFoundException e) {
	
		System.out.println("File is not Found");
		menu(raf,rec, table, temp);
	}
	System.out.println("Back To Menu");
	menu(raf, rec, table, temp);
}	
	
	/************************************************************************************************
	 * 2- Display the random-access file: here I have displayed the RandomAccess File
	 * like in the previous project
	 * @param raf
	 * @param rec
	 * @throws IOException
	 */
	private static void displayRandomAccessFile(RandomAccessFile raf,Student rec,HashTable<Pair> table,File temp) throws IOException {
		
		System.out.println("******RandomAccesFile:********");			
		try {
			
			//printing the records from the random access file one by one
			
			
			for (int i=0; i<raf.length()/92;i++)
			{	
			raf.seek(i*92);
				rec.readFromFile(raf);	
				System.out.println(rec);
			}
			
		menu(raf, rec, table, temp);	//back to menu
		 
			}catch (Exception e) {
		
			System.out.println("Back To Menu");
			menu(raf, rec, table, temp);	//back to menu
	}	
}
	
/**
 *	3- Build the index: It asks the user to enter the database name (a random-access file). It reads
the database records sequentially one at a time, hashes the KEY (student ID), and inserts the
pair (KEY, ADDRESS) in the hash table. The ADDRESS is the position of the record
containing the ID in the database (first record in the database is at position 0, second record is
at position 1, and so on). Use the following hash function in your program:
hash (key) = (key*key) >> 10 % 37
(Note: Due to the use of lazy deletions, the key and the position of a deleted record should not be
added to the hash table.)
 * @param raf
 * @param rec
 * @param table
 * @throws IOException
 */
	public static void createIndex(RandomAccessFile raf,Student rec, HashTable<Pair> table,File temp) throws IOException{
		
	try {
	
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the name of database file (randomaccess):");
		String dbName = scan.nextLine();
		
		temp = new File(dbName);
		if (temp.exists()){
		     temp.delete();
		     System.out.println("Creating Index is successfull");
		
		//making pairs of (KEY , ADDRESS) using the pair class, and then the binary search tree is made of type <Pair>
		  	
		for (int i = 0; i < raf.length()/92; i++) {
  				raf.seek((i * 92) + 80); 
   				Pair myPair =	new Pair(raf.readInt(),i);
  				
   				//check for deleted records, the deleted records will be omitted
   				
   				if(raf.readInt()==0){
  					continue;
  				}
   				//adding Node<E> to the binary search tree
  				table.add(myPair);
			}
		}	else{
			System.out.println("enter correct database name");
			
			createIndex(raf, rec, table, temp);
		}
	} catch (Exception e) {
		//will handle any exceptions in building the index
		System.out.println("Index not built");
		displayRandomAccessFile(raf, rec, table, temp);
		createIndex(raf, rec, table, temp);
	}
	menu(raf,rec, table, temp);
}

	/**
	 *4- Display the index: It asks the user for the starting and ending index values (default values are 0
and 36 respectively), and displays the non-empty hash table entries that are within that range. If the
user doesn’t enter a value (just presses the return key) for either or both of the staring and ending
values, the default values will be used instead. For each non-empty hash table entry, it prints the
index value followed by the level-by-level traversal of the associated binary search tree (or AVLtree)
with each level printed on a separate line. The information that is printed for each node is the
pair [KEY, ADDRESS].
	 * @param raf
	 * @param rec
	 * @param table
	 * @throws IOException
	 */
	@SuppressWarnings({ "resource", "unused" })
	private static void displayIndex(RandomAccessFile raf,Student rec, HashTable<Pair> table,File temp) throws IOException {
		
		try { 
			System.out.println("******* DISPLAY INDEX MENU ********");

				// here part of the key will be taken in and matched with the possible keys
				try{
				
					System.out.println("Enter the starting Index:");
					Scanner scan1 = new Scanner(System.in);
					
					Integer startIndex=0;
					
					Integer endIndex = 36;
					
					//if the input is not text then skip the next statement and capture the
					// enter key value
					try{
						startIndex = Integer.parseInt(scan1.nextLine());
					}
					catch(NumberFormatException e){
						startIndex =0;
					}
			
									
				System.out.println("Enter the Ending Index:");
				Scanner scan2 = new Scanner(System.in);
				
				try{
					endIndex = Integer.parseInt(scan2.nextLine());
				}
				catch(NumberFormatException e){
					endIndex = 36;
				}
			
				//takes the input from where the rest of the index should be printed 
					
					try {
						
						table.printNonEmpty(startIndex, endIndex);
						
					}	catch (InputMismatchException e)
						{
						System.out.println("Invalid Key,Please input correct Key!");
						}

					// this exception will catch the null pointer because the last node's address points at null 						//

					System.out.println("\nEnd of Tree!\n");
					menu(raf,rec, table, temp);
					
					}	catch (InputMismatchException e) {

				// this exception will catch the wrong input		
					System.out.println("Failure of search!");
					menu(raf,rec, table, temp);	
					//going back to main menu
				} 
				System.out.println("Back To Menu");
				menu(raf, rec, table, temp);
//			}
			menu(raf, rec, table, temp);
		}	catch (InputMismatchException e) {

			System.out.println("Failure of search!Input Correct value");
			menu(raf,rec, table, temp);	
			}
	}
	

	
	/**
	 * 5- Retrieve a record: It asks the user to enter a student ID (a key value). It uses the hash
function to transform the student ID into the hash table index. It searches the binary search
tree (or AVL-tree) referenced through that index for the key, and if the search is successful it
uses the ADDRESS associated with that key to retrieve and then display the corresponding
record. If the search is unsuccessful, it prints a message indicating the failure of the search.
	 * @param <E>
	 * @param raf
	 * @param rec
	 * @param table
	 * @throws IOException
	 */

	@SuppressWarnings("resource")
	private static <E> void retrieve(RandomAccessFile raf,Student rec, HashTable<Pair> table,File temp) throws IOException {
		System.out.println("******Retrieve a record:*****");
		try {
			try {
				//ask the user to input key to be retrieved
				
				System.out.println("Enter the Student Id (Key) to be retrieved:");	
				Scanner scan = new Scanner(System.in);
				int num	=scan.nextInt();
			
				// finds the key and returns the address where that record is in the random access file
				
				
				int address=find(num,table); //
				
				System.out.println(address);
				
				raf.seek(address*92);	//points to the starting of the record in random access file
				rec.readFromFile(raf);	//reads that particular record 
				System.out.println(rec); //prints the retrieved record
				
				menu(raf, rec, table, temp);
			} catch (InputMismatchException  e) {
				
				System.out.println("Search Failure, enter correct key");
				menu(raf, rec, table, temp);
			}
		} catch (IOException e) {
			System.out.println("Search Failure, enter correct key");
			menu(raf, rec, table, temp);
		}	
	}

	// this is a function to return the ADDRESS of a Pair(node) by taking the Key

	
	public static int find(int id,HashTable<Pair> table){
	{	
	if	(table.get(id).getKey()==id){
				return table.get(id).getAddress();
			}
		}
		return -1;
	}
	
	
/**
 * 6- Modify a record: It asks the user to enter a student ID (a key value). It uses the hash function
to transform the student ID into the hash table index. It searches the binary search tree (or AVLtree)
referenced through that index for the key, and if the search is successful it uses the
ADDRESS associated with that key to retrieve and then display the corresponding record. It
allows the user to modify any fields of this record, except the student ID field, and then it writes
the modified record over the original record in the database. Note that modifying a record
doesn’t require any changes to the index (the hash table) because the user is not allowed to
change the ID field.
 * @param raf
 * @param rec
 * @param table
 * @throws IOException 
 */
@SuppressWarnings("resource")
private static void updateRecord(RandomAccessFile raf, Student rec,HashTable<Pair> table,File temp) throws IOException {
	System.out.println("******Modify a Record:******");

	try {
		//ask the user to input key to be modified
		
		System.out.println("Enter the Student Id (Key) to be Modified:");	
		Scanner scan = new Scanner(System.in);
		int num	= scan.nextInt();
		
		int address = find(num,table);
		
		//prints the address where the record is on the random access file
		
		System.out.println(address);
		
		raf.seek(address*92);
		rec.readFromFile(raf);
		
		//prints the record before being modified
		
		System.out.println(rec);

while(true){		
		Scanner scan1 = new Scanner(System.in);
		System.out.println("\nFor modifying First name enter F\n "
				+ "\nFor modifying Last name enter L\n "
				+ "\nFor modifying GPA enter G");
		String field = scan1.next();
					// Prompt for new first name
			if(field.equalsIgnoreCase("f")){ 
					System.out.println("Please Enter the new First Name:");
					String newFirstName = new String();
					newFirstName = scan.next();
					rec.setfName(newFirstName);
				
				}
					// Prompt for new last name
			if(field.equalsIgnoreCase("l")){
					System.out.println("Please Enter the new Last Name:");
					String newLastName = new String();
					newLastName = scan.next();
					rec.setlName(newLastName);
					
			}
					// Prompt for new gpa
			if(field.equalsIgnoreCase("g")){
					System.out.println("Please Enter the new GPA number:");
					Scanner scan2 = new Scanner(System.in);
					double newGpa = scan2.nextDouble();
					rec.setGPA(newGpa);
			}
			
			System.out.println("Do you want to modify any other field?"
					+ "\nPress any key for YES, and N for NO");
			String input = scan.next();
			
			if(input.equalsIgnoreCase("n"))
				break;
		}
					
					raf.seek(address*92);
					rec.writeToFile(raf);
					
					displayRandomAccessFile(raf, rec,table, temp);
			menu(raf,rec,table, temp);
	
	} catch (InputMismatchException e) {
	
		System.out.println("Search Failure, Enter correct key");
		
		menu(raf,rec,table, temp);
	}
}


/**
 * 7- Add a new record: It asks the user to enter data for a new record. This new record will be
appended to the end of the database (the random-access file). Next, the student ID (the key
value) and the position (ADDRESS) of the record just written to the end of the database must be
added to the index. This means, the student ID must be transformed into a hash table index and a
new node containing (KEY, ADDRESS) must be inserted into the binary search tree (or AVLtree)
referenced through that index.
 * @param raf
 * @param rec
 * @param table
 * @throws IOException
 */
private static void addRecord(RandomAccessFile raf, Student rec, HashTable<Pair> table,File temp) throws IOException {
	System.out.println("******Add a Record:******");
	
	// ask the user to input new record
	
	System.out.print("Enter your first name, last name, student ID, and GPA: ");

	Scanner keyIn = new Scanner(System.in);

	rec.readFromTextFile(keyIn);

	raf.seek(raf.length());
	
	rec.writeToFile(raf);

	//now add new record entered to the binary search tree
	try {
		for (int i = numOfrec; i < raf.length()/92; i++) {
				raf.seek((i * 92) + 80); 
				Pair myPair =	new Pair(raf.readInt(),i);
				table.add(myPair);
				
		}
		numOfrec++;
	} catch (NullPointerException e) {

		System.out.println("Failed!");
	}
	
	

	//display raf after adding new record
	displayRandomAccessFile(raf, rec,table, temp);
	
menu(raf, rec, table, temp);
}


/**
 * 8- Delete a record: It asks the user to input the student ID (the key value) of the record that
needs to be deleted. It uses the hash function to transform the student ID into the hash table
index. It searches the binary search tree (or AVL-tree) referenced through that index for the
key, and if the search is successful it uses the ADDRESS associated with that key to delete the
corresponding record from the database (using lazy deletion). It also deletes the node
containing the key value (i.e., (KEY, ADDRESS)) from the binary search tree (or AVL-tree)
corresponding to that index.
 * @throws IOException 
*/
@SuppressWarnings("resource")
private static void deleteRecord(RandomAccessFile raf, Student rec, HashTable<Pair> table,File temp) throws IOException {
	System.out.println("******Delete a record:*****");
	try {
		
		// ask the user to input the record to be deleted
	
		System.out.println("Enter the Student Id (Key) to be deleted:");	
		Scanner scan = new Scanner(System.in);
		int studentId	= scan.nextInt();
		
		//(1) DELETE FROM RandomAccess File

		int address = find(studentId,table);
		
		raf.seek((address)*92);
		rec.readFromFile(raf);
		
		System.out.println("Record to be deleted:"+rec);
		
		//Now Mark the record as "delete" followed by 14 whitespaces
		
		rec.setfName("Delete              ");
		rec.setlName("DELETE              ");
		rec.setID(0000);
		rec.setGPA(00);

		raf.seek(address*92);
		rec.writeToFile(raf);
		System.out.println(rec);
		
		//(2) Now DELETE the record from binary search tree
		
		//Now using the key from remove the Node from the binary search tree

		Pair pairToBeDeleted = new Pair(address, studentId);	
		
		table.delete(pairToBeDeleted);

		//display raf after deletion
		
		displayRandomAccessFile(raf, rec,table, temp);
		
		System.out.println(rec);
menu(raf,rec,table, temp);
	} catch (InputMismatchException e) {

		System.out.println("Please enter correct Key!");
		menu(raf,rec,table, temp);
	}	
}
/*
 * 9- Quit: It displays the entire hash table and then terminates the program. The hash table entries are
printed as follows:
0- [KEY, ADDRESS] [KEY, ADDRESS] ……
1- [KEY, ADDRESS] ….
3- [KEY, ADDRESS] …..
 */
private static void exit(File temp, @SuppressWarnings("rawtypes") HashTable table) throws IOException {
	table.print();	
	System.exit(0);
		// deleting the file on exit
		
		temp.deleteOnExit();
	}

}
	
