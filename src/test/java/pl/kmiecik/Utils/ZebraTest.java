package pl.kmiecik.Utils;

import org.junit.jupiter.api.Test;

class ZebraTest {

    @Test
    void sendAndReceiveData() {
        String zpl = "^XA\n" +
                "^LH25,15^MMP\n" +
                "^BY144,144^FT120,230^BXN,12,200,0,0,1\n" +
                "^FH\\^FD<BARCODE>^FS\n" +
                "^FT20,300^A0N,45,33^FH\\^FD<DPN>^FS\n" +
                "^XZ";
//FD20111602641T28738159;FD20111992641T28742559
        String input2DCode="FD20111602641T28112233";
        FormatToPrintString formatToPrintString = new FormatToPrintString();
        String prepareStringToPrint =  formatToPrintString.prepareStringToPrint(input2DCode);;
        RS232 rs232 = new RS232("COM3", 9600);
        Zebra zebra = new Zebra(zpl, rs232);
        zebra.open();
        String acknoladge = zebra.sendAndReceiveData(prepareStringToPrint);
        System.out.println(acknoladge);
     /*   String printerIsReady = zebra.sendAndReceivedCommand("~HS");
        if (printerIsReady.equals("")) {
            System.out.println("printer is ready");

            String acknoladge = zebra.sendAndReceiveData(prepareStringToPrint);
            System.out.println("label was printed");
        }*/
        zebra.close();

    }

    @Test
    void sendAndReceivedCommand() {
    }
}