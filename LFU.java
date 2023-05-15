import java.util.Iterator;
import java.util.PriorityQueue;

public class LFU extends PageReplacementAlgorithm{

    // this class implements the least frequently used algorithm
    // input: pages to get from storage
    // output: the hit / miss status
    //         the 2-dimensional hit/miss matrix for each page requested
    //         the 2-dimensional page frame matrix showing the page contents
    //         other outputs inherited from the PageReplacementAlgorithm class 
    boolean[][] hitMatrix;
    int[][] framesMatrix;
    // priority queue needed to easily perform LFU
    PriorityQueue<IntegerEntry> queue = new PriorityQueue<>();

    public LFU(int[] pages, int numOfPages, int frameSize){
        super(frameSize);
        framesMatrix = new int[numOfPages][frameSize];
        hitMatrix = new boolean[numOfPages][frameSize];
        // execute the algorithm
        for(int iter = 0; iter < numOfPages; iter++){
            // check if array is full 
            // if array is full, remove least frequently used page first
            if(pageCount == frameCount - 1){
                int minVal = queue.poll().getKey();
                // remove the page with least frequency of use
                for (int i = 0; i < pageCount; i++){
                    if(pageFrames[i] == minVal){
                        pageFrames[i] = 0;
                        break;
                    }
                }
            }
            // if page is found in the priority queue don't insert
            Integer pageNum = Integer.valueOf(pages[iter]);
            Boolean isFound = false;
            Iterator values = queue.iterator();
            while(values.hasNext()){
                IntegerEntry value = ((IntegerEntry) values.next());
                if( value.getKey() == pageNum){
                    hits[iter] = true;
                    isFound = true;
                    // remove the element from queue
                    queue.remove(value);
                    // increment the frequency
                    value.setValue(value.getValue() + 1);
                    // insert to priority queue
                    queue.add(value);
                    break;
                }
            }
            
            if(!isFound){
                // else insert it to end of linked list
                queue.add(new IntegerEntry(pageNum, 1));
                pageFrames[pageCount] = pageNum;
                pageCount++;
                hits[iter] = false;
            }
            // then save it to the matrix for the iteration
            for(int i = 0; i < frameSize; i++){
                framesMatrix[iter] = pageFrames;
                hitMatrix[iter][i] = hits[i];
            }
        }
    }

    public boolean[][] getHitMatrix() {
        return hitMatrix;
    }

    public int[][] getFramesMatrix() {
        return framesMatrix;
    }

}