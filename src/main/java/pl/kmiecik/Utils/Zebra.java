package pl.kmiecik.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class Zebra {


    private String zpl;
    final private RS232 rs232;

    public Zebra(String zpl, final RS232 rs232) {
        this.zpl = zpl;
        this.rs232 = rs232;
    }

    public Zebra(final RS232 rs232) {
        this.zpl = "";
        this.rs232 = rs232;
    }

    public void close() {
        rs232.close();
    }

    public void open() {
        rs232.open();
    }

    public String replaceInZpl(final String dataToSend) {
        return sendAndReceive(zpl.replace("<BARCODE>", dataToSend));
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

    public String print(final String prepareStringToPrint) {
        this.open();
        String result = replaceInZpl(prepareStringToPrint);
        this.close();
        return result;
    }

    public void loadZplFromFile(final String path) {

        try {
            List<String> zplList = Files.readAllLines(Paths.get(path));
            this.zpl = zplList.stream().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
