import java.util.Random;

public class ESC extends PageReplacementAlgorithm{

    // this class implements the enhanced second chance algorithm
    // input: pages to get from storage
    // output: the hit / miss status
    //         the 2-dimensional hit/miss matrix for each page requested
    //         the 2-dimensional page frame matrix showing the page contents
    //         other outputs inherited from the PageReplacementAlgorithm class 
    boolean[][] hitMatrix;
    int[][] framesMatrix;
    int[] modifyBits; 
    int[] refBits; // reference bits for second chance
    // circular linked list needed to easily perform ESC

    public ESC(int[] pages, int numOfPages, int frameSize){
        super(frameSize);
        framesMatrix = new int[numOfPages][frameSize];
        hitMatrix = new boolean[numOfPages][frameSize];
        // execute the algorithm
    }

    public boolean[][] getHitMatrix() {
        return hitMatrix;
    }

    public int[][] getFramesMatrix() {
        return framesMatrix;
    }

    public int[] getModifyBits() {
        return modifyBits;
    }

}