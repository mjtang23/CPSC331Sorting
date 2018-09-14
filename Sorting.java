/* Marcus Tang 10086730 CPSC 331 */

import java.util.*;
/* This section of the code deals with printing
	and comparing the algorithms. Seeing if they have 
	the right values. As well, it checks how long it takes
	to sort the inputs given with the alogrithms created.*/
	
public class Sorting {
    
	public static void main(String args[]) {
		AbstractSort sorting;
		Scanner reader = new Scanner(System.in);
		
		// read size of input
		int n = reader.nextInt();
		int[] numbers = new int[n];
		
		// read numbers
		for(int i = 0; i < n; i++){
			numbers[i] = reader.nextInt();
		}
		
		
		// run sorting algorithms
		sorting = new InsertionSort();
		run(sorting, numbers);

		sorting = new HeapSort();
		run(sorting, numbers);

		sorting = new QuickSort();
		run(sorting, numbers);

		sorting = new QuickSortImproved();
		run(sorting, numbers);
	}

	private static void run(AbstractSort sorting, int[] numbers) {
		int[] temp = Arrays.copyOf(numbers, numbers.length);
		long startTime = System.currentTimeMillis();
		sorting.sort(temp);
		long endTime = System.currentTimeMillis();
		System.err.println("Duration: " + (endTime-startTime) + " ms");
		System.out.println(Arrays.toString(temp));
		int[] temp2 = Arrays.copyOf(numbers, numbers.length);
		Arrays.sort(temp2);
		if(Arrays.toString(temp).equals(Arrays.toString(temp2)))
			System.err.println("Correct");
		else
			System.err.println("Wrong");
	}
}

interface AbstractSort {
	public void sort(int[] numbers);
}

class InsertionSort implements AbstractSort {
	public void sort(int[] numbers) {
		// TODO implement insertion sort
		// Insert main 
        
		int high = numbers.length - 1; // Size of the array given
		for(int i = 1; i <= high; i++){
			    // J becomes a temp for i, and checks numbers from that point and downwards
				int j = i; 
				//swaps if the lower index element is larger than the higher index element
				while((j > 0) && (numbers[j] < numbers[j - 1])){
					int temp = numbers[j];
					numbers[j] = numbers[j-1];
					numbers[j-1] = temp;
					j = j - 1;
	    	}
   	}
 	}
}

class HeapSort implements AbstractSort {
	// Deals with finding the left child of the parent
	public int l_side(int i){
		i = 2*i + 1;
		return i;
	}
    // Deals with finding the right child of the parent
	public int r_side(int i){
		i = 2*i + 2;
		return i;
	}
    // Where the main of this heap sort begins
	public void sort(int[] numbers){

		int length = numbers.length;
		// To create a max heap sort to get tree from highest to lowest
		for(int parent = (length/2) - 1; parent >= 0; parent--){
			rebuild(numbers, parent, length);
		}
        // To take the max heap and create the sorted array
		for(int size = length; size > 0; size--){
			deleteMax(numbers, size);

		}



	}
	    // This swaps the highest value to the end of the array
		//Then, remakes the tree again and makes the high boundry smaller
		// Therefore, the highest parts of the array go untouched and are sorted.
	    public void deleteMax(int[] array, int size){
			int max = 0;
			int last = size - 1;

			max = array[0];
			array[0] = array[last];
			array[last] = max;
			size--;
            // Where the tree is remade
			for(int parent = (size/2) - 1; parent >= 0; parent--){
				rebuild(array, parent, size);
			}
		}





        // Creates the max heap tree, finding the larger values
		// in the array and placing them higher in the tree.
		public void rebuild(int array[], int parent, int size){
			int j = 0;

			if(j < size){
				int left = l_side(parent); // gets index value of left child
			    int right = r_side(parent); // gets index value of the right child
				int largest = j; // the largest value of the 3 elements

				if((left < size) && (array[left] > array[largest])){
					largest = left;
				}

				if((right < size) && (array[right] > array[largest])){
					largest = right;
				}
                // swaps if parent is smaller than the child
				if(largest != j){
					int temp = array[j];
					array[j] = array[largest];
					array[largest] = temp;
					j = largest;
				}

				else{
					j = size; // Breaks the loop
				}

			}

		}

    }


class QuickSort implements AbstractSort {
	// Picks a partition, then splits the array 
	// depending on if greater or less than array.
	public int Dpartition(int[] array, int low, int high){

		int i = low;
		int j = high - 1;
		int p = array[high];// partition always last in the array

		while(i <= j){
			// finds an element that should be greater than partition
			while(i <= j && array[i] <= p){
				i++;
			}
			// finds partition that is less than partition
			while(j >= i && array[j] >= p){
				j--;
			}
			// swaps the elements that i and j are pointed to 
			if(i < j){
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
			}

		}
		// swaps the partition with the i value. 
		// Hopefully somewhere in the middle of the array
		int temp = array[high];
		array[high] = array[i];
		array[i] = temp;

		return i;
	}
    // The quick sort algorithm. where it somewhat sort the array into
	// hopefully even sub arrays. 
	public void quicksort(int [] A, int low, int high){
		if (low < high){
			int q = Dpartition(A, low, high);

			quicksort(A, low, q-1);
			quicksort(A, q+1, high);
		}
	}

		// TODO implement quick sort
		// The main for the quick sort
		public void sort(int[] numbers){

			int low = 0;
			int high = numbers.length - 1;

			quicksort(numbers, low, high);

		}




}

class QuickSortImproved implements AbstractSort {
        // Picks a partition, then splits the array 
	    // depending on if greater or less than array.
		public int Dpartition(int[] array, int low, int high){

			int i = low;
			int j = high - 1;
			int p = findPartition(array, low, high); // Find partition in imporved method

			while(i <= j){
				    // finds an element greater than partition
					while(i <= j && array[i] <= p){
						i++;
					}
					// finds an element less than partition 
					while(j >= i && array[j] >= p){
						j--;
					}
					// swaps the elements pointed at i and j
					if(i < j){
						int temp = array[i];
						array[i] = array[j];
						array[j] = temp;
					}

			}
			// swaps i with the 2nd last element in the array
			int temp = array[high - 1];
			array[high - 1] = array[i];
			array[i] = temp;

			return i;
		}
    // The method in finding a better partition
    public int findPartition(int[] array, int low, int high){
		
		        // Fills the first, middle, and last elements from array given
				// placed into a temp array
				int median = (high + low)/2;
				int first = array[low];
				int middle = array[median];
				int last = array[high];
				
                // the creation of temp array
				int[] cmpArray = new int[3];
				cmpArray[0] = first;
				cmpArray[1] = middle;
				cmpArray[2] = last;
				
                // sorts the temp array in increasing order
				insertSort(cmpArray);
				array[low] = cmpArray[0];
				array[median] = cmpArray[1];
				array[high] = cmpArray[2];
				
                // takes the middle value of the array 
				// and swaps with the second last element in given array
				int temp = array[high - 1];
				array[high - 1] = array[median];
				array[median] = temp;

				return array[median];


		}
		// Improved quick sort algorithm
		public void quicksort(int [] A, int low, int high){
			int size = high - low;
			int threshHold = 10;
            // Decides when to switch from quick sort to insert sort
			if (size > threshHold){
				if (low < high){

					int q = Dpartition(A, low, high);
					quicksort(A, low, q-1);
					quicksort(A, q+1, high);
				}
			}


		}

		public void insertSort(int[] array) {
			// insert method that takes in the array from quick sort
			int high = array.length - 1;
			for(int i = 1; i <= high; i++){
				int j = i;
				while((j > 0) && (array[j] < array[j - 1])){
					int temp = array[j];
					array[j] = array[j-1];
					array[j-1] = temp;
					j = j - 1;
				}

			}
		}


			
			// Quick sort main
			public void sort(int[] numbers){
                // lowest and highest index values
				int low = 0;
				int high = numbers.length - 1;

				quicksort(numbers, low, high);
				insertSort(numbers);


			}

}
