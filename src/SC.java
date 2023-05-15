package src;
public class SC extends PageReplacementAlgorithm{

    // this class implements the second chance algorithm (FIFO variant)
    // input: pages to get from storage
    // output: the hit / miss status
    //         the 2-dimensional hit/miss matrix for each page requested
    //         the 2-dimensional page frame matrix showing the page contents
    //         other outputs inherited from the PageReplacementAlgorithm class 
    boolean[][] hitMatrix;
    int[][] framesMatrix;
    // circular linked list needed to easily perform SC
    int head = 0; // moving head to mimic a circular queue
    int[] refBits; // reference bits for second chance

    public SC(int[] pages, int numOfPages, int frameSize){
        super(frameSize);
        framesMatrix = new int[numOfPages][frameSize];
        hitMatrix = new boolean[numOfPages][frameSize];
        // execute the algorithm
        for(int iter = 0; iter < numOfPages; iter++){
            // if page is found in the pageFrames array don't insert
            if(foundPage(pages[iter])){
                hits[iter] = true;
                // find where is the index
                int index = 0;
                for(int i = 0; i < pageCount; i++){
                    if(pages[iter] == pageFrames[i]){
                        index = i;
                        refBits[i] = 1; // second chance
                        break;
                    }
                }
            }else{
                // search for the page to be replaced
                for(int i = 0; i < pageCount; i++){
                    // use the second chance 
                    if(refBits[i] == 1){
                        refBits[i] -= 1;
                    }

                }
                // else insert it to topmost frame
                pageFrames[pageCount] = pages[iter];
                pageCount++;
                hits[iter] = false;
            }
            // then save it to the matrix for the iteration
            for(int i = 0; i < frameSize; i++){
                framesMatrix[iter][i] = pageFrames[i];
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

    // moves the circular queue head per iteration
    public void moveHead(int offset){
        head = (head + 1) % frameSize;
    }

}