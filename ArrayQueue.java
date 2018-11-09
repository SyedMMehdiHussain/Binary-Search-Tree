import java.util.AbstractQueue;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Queue;

public class ArrayQueue<E> extends AbstractQueue<E> implements Queue<E> {
	private int front;	
	private int rear;
	private int size;
	private int capacity;
	private static final int DEFAULT_CAPACITY=10;
	private E[] theData;
	
	//constructor
	public ArrayQueue() {
		
		this(DEFAULT_CAPACITY);
		
	}
	
	@SuppressWarnings("unchecked")
	public  ArrayQueue(int cap) {
		capacity=cap;	
		front=0;
		rear=this.capacity-1;
		size=0;
		theData=(E[]) new Object [cap];
	}

/**
 * adds the event to the circular array which is a wait queue, if the user has entered -1 
 * for the waitQsize then it will become an infinite array queue	
 */
@Override
public boolean offer(E item) {
		if (size==capacity){
		reallocate();}
		size++;
		rear = (rear+1) % capacity;
		theData[rear] = item;
		return true;
	}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return "RegularWaitQueue [front=" + front + ", rear=" + rear + ", size=" + size + ", capacity=" + capacity
			+ ", theData=" + Arrays.toString(theData) + "]";
}

/**
 * this reallocates the array if the array has been selected to be an infinite array
 */
@SuppressWarnings("unchecked")
private void reallocate() {
		int newCapacity = 2*capacity;
		E[] newData = (E[]) new Object [newCapacity];
		int i=front;
		for(int k = 0; k < size; k++){
			newData[k] = theData[i];
			i = (i+1) % capacity;
		}
		capacity = newCapacity;
		theData = newData;
		front = 0;
		rear = size-1;
	}

@Override
public E peek() {
if (isEmpty()){
	try {
		throw new Exception();
	} catch (Exception e) {
		System.out.println("Queue is Empty");
		}
	}
		return theData[front];
}

public E remove() {
	if (isEmpty()){
		try {
			throw new Exception();
		} catch (Exception e) {
			System.out.println("Queue is Empty");
			}
		}
	E result = theData[front];
	front = (front+1) % capacity;
	size--;
	return result;
}
@Override
public E poll() {

	return null;
	}

@Override
public Iterator<E> iterator() {
		return null;
	}

public int getSize(){
	return size;
}

public boolean isEmpty(){
	return size ==0;
	}

@Override
public int size() {
	return size;
}

}


