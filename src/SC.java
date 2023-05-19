
public class SC extends PageReplacementAlgorithm{

    // this class implements the second chance algorithm (FIFO variant)
    // input: pages to get from storage
    // output: the hit / miss status
    //         the 2-dimensional hit/miss matrix for each page requested
    //         the 2-dimensional page frame matrix showing the page contents
    //         other outputs inherited from the PageReplacementAlgorithm class 
    boolean[] hitMatrix;
    int[][] framesMatrix;
    // circular linked list needed to easily perform SC
    int head = 0; // moving head to mimic a circular queue
    int[] refBits; // reference bits for second chance

    public SC(int[] pages, int numOfPages, int frameSize){
        super(frameSize);
        framesMatrix = new int[numOfPages][frameSize];
        hitMatrix = new boolean[numOfPages];
        // execute the algorithm
        for(int iter = 0; iter < numOfPages; iter++){
            // if page is found in the pageFrames array don't insert
            if(foundPage(pages[iter],iter)){
                hitMatrix[iter] = true;
                // find where to give second chance
                for(int i = 0; i < pageCount; i++){
                    if(pages[iter] == pageFrames[i]){
                        refBits[i] = 1; // second chance creation
                        break;
                    }
                }
            }else{
                // search for the page to be replaced
                // start at head
                int index = head;
                // first pass: search for frame with refBit = 0
                int frameChosen = -1;
                for(int i = 0; i < pageCount; i++){
                    int frame = (index+i) % frameSize;
                    if(refBits[frame] == 0){
                        frameChosen = frame;
                        break;
                    }
                }
                // first pass fails or not, now decrease all second chances
                for(int i = 0; i < pageCount; i++){
                    // use the second chance for the rest of pages
                    if(refBits[i] == 1){
                        refBits[i] = 0;
                    }
                }
                // second pass: search for frame with refBit = 0 
                // only run this if frameChosen has no valid value
                for(int i = 0; (i < pageCount && frameChosen == -1); i++){
                    int frame = (index+i) % frameSize;
                    if(refBits[frame] == 0){
                        frameChosen = frame;
                        break;
                    }
                }
                // else insert it to the chosen page frame
                pageFrames[frameChosen] = pages[iter];
                refBits[frameChosen] = 0;
                if(pageCount < (frameSize - 1)){
                    pageCount++;
                }
                hitMatrix[iter] = false;
            }
            // move the circular queue head. page hit or page fault
            head = (head + 1) % frameSize;
            // then save it to the matrix for the iteration
            for(int i = 0; i < frameSize; i++){
                framesMatrix[iter][i] = pageFrames[i];
            }
        }
    }

    public boolean[] getHitMatrix() {
        return hitMatrix;
    }

    public int[][] getFramesMatrix() {
        return framesMatrix;
    }

}