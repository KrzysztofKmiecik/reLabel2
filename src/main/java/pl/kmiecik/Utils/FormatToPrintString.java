package pl.kmiecik.Utils;

import java.util.Optional;

public class FormatToPrintString {

    private String currentAPN;
    private String newAPN;

    public static void main(String[] args) {
        final String input = "FD20111602641T28112233"; ////FD20111602641T28738159;FD20111992641T28742559
        final String currentAPN = "28112233";
        final String newAPN = "28114444";
        FormatToPrintString formatToPrintString = new FormatToPrintString();
        String output = formatToPrintString.prepareStringToPrint(input);

        System.out.println(output);
    }


    public String prepareStringToPrint(final String input) {
        if (input.startsWith("28")) {
            currentAPN = input.substring(0, 8);
        } else {
            currentAPN = input.substring(14, 22);

        }

        CrossRef newApn = this.getNewApn(currentAPN);
        newAPN = newApn.getNewAPN();

        return input.replace(currentAPN, newAPN);
    }


    private CrossRef getNewApn(final String currentAPN) {

        MyJson myJson = new MyJson();
        String path = "src/main/resources/CrossRefFile.json";
        Optional<CrossRef> readOptionalCrossRef = myJson.read(currentAPN, path);
        if (readOptionalCrossRef.isPresent()) {
            return readOptionalCrossRef.get();
        }

        return null;
    }

    public String getCurrentAPN() {
        return currentAPN;
    }

    public String getNewAPN() {
        return newAPN;
    }
}
