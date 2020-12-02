package pl.kmiecik.Utils;

import org.junit.jupiter.api.Test;

public class RS232Test {

    public static void main(String[] args) {
        /*RS232 rs232 = new RS232("COM3", 9600);
        rs232.open();
        String str="";
        char terminator=Character.LINE_SEPARATOR;
        // "\n".charAt(0); //"\n".charAt(0)'';

        try {
            str = rs232.writeAndRead("OLAAAAAAAAAAAAAAAA",terminator,5000);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/


    }

    @Test
    void validate() {
        String comPort = "com2";
        String regex = "^COM\\d";
        if (comPort.trim().toUpperCase().matches(regex)) {

        }
    }


}
