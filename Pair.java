public class Pair implements Comparable<Pair> , Square<Pair> {
	private int key;
	private int address;
	
	// constructors
	// note that constructor headings don't include the type parameter in angular brackets 

	
	public Pair (int k, int a) {
		this.key = k;
		this.address = a;
	}
	
	// accessory and mutators
	public void setData (int k, int a) {
		key = k;
		address = a;
	}
	
	public void setKey (int k) {
		key = k;
	}
	
	public void setAddress (int a) {
		address = a;
	}
	
	public int getKey() {
		return key;
	}
	
	public int getAddress() {
		return address;
	}
	
	// return a string representation of a pair
	public String toString() {
		return "Key = " + key + "\tAddress = " + address+"\t";
	}

	public boolean equals(Object other)
	{
		if ((int)other == this.key)
			return true;
	else if (getClass() != other.getClass())
			return false;
		else {
			Pair otherPair = (Pair) other;
		return key==(otherPair.key) ;
		}	
	}
	

	public int compareTo(Pair arg0) {
		
		if (this.key > (arg0.key))
		{
			return 1;
		}
		else if (this.key==arg0.key)
			return 0;
		else return -1;
	}

	public Pair() {
	}

	public int getKey(int num) {
		return key;
	}

	public int getAddress(int num) {
		return address;
	}

	public int square() {
	int squareKey = this.key*this.key;
		return squareKey;
	}

}
