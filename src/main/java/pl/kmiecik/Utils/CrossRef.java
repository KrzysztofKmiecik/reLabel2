package pl.kmiecik.Utils;

public class CrossRef {


    private String currentAPN = null;
    private String newAPN = null;
    private int startIndex=0;


    public String getCurrentAPN() {
        return currentAPN;
    }

    public void setCurrentAPN(String currentAPN) {
        this.currentAPN = currentAPN;
    }

    public String getNewAPN() {
        return newAPN;
    }

    public void setNewAPN(String newAPN) {
        this.newAPN = newAPN;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }
    @Override
    public String toString() {
        return "CrossRef{" +
                "currentAPN='" + currentAPN + '\'' +
                ", newAPN='" + newAPN + '\'' +
                ", startIndex=" + startIndex +
                '}';
    }
}
