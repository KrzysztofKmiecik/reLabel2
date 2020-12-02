package pl.kmiecik.Utils;

import java.util.concurrent.TimeoutException;

public class Zebra {

    private String zpl;
    private RS232 rs232;
    private char terminator = Character.LINE_SEPARATOR;
    private char timeout=15000;

    public Zebra(String zpl, RS232 rs232) {
        this.zpl = zpl;
        this.rs232 = rs232;


    }

    public void close() {
        rs232.close();
    }
    public void open() {  rs232.open();
    }

    public String sendAndReceiveData(String dataToSend) {
        zpl=zpl.replace("<BARCODE>", dataToSend);
        return sendAndReceive(zpl);


    }

    public String sendAndReceivedCommand(String command) {
        return sendAndReceive(command);
    }

    private String sendAndReceive(String command) {
        String receivedStr = "";
        try {
            receivedStr = rs232.writeAndRead(command);
        } catch (TimeoutException e) {
            System.out.println(e.getMessage());

        }
        return receivedStr;
    }


}
