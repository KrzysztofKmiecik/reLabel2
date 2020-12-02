package pl.kmiecik.Utils;

import gnu.io.NRSerialPort;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class RS232 {
    private final NRSerialPort serial;

    public RS232(String port, int baudRate) {
        serial = new NRSerialPort(port, baudRate);
    }

    public void open() {
        serial.connect();
    }

    public void close() {
        serial.disconnect();
    }

    public void write(String data) {
        try (DataOutputStream outs = new DataOutputStream(serial.getOutputStream());) {
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                outs.write(c);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String readAll() {
        String str = "";
        try (DataInputStream ins = new DataInputStream(serial.getInputStream());) {
            while (!Thread.interrupted()) {// read all bytes
                if (ins.available() > 0) {
                    char b = (char) ins.read();
                    System.out.print(b);
                    Thread.sleep(5);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return str;
    }

    public String writeAndRead(String dataToWrite) throws TimeoutException {
        String responseStr = "";
        this.write(dataToWrite);
        //   System.out.println(addCharEnd("12345",'a'));
        responseStr = read(responseStr);
        return responseStr;
    }

    public String writeAndReadWithTerminatorDelay(String dataToWrite, char terminator, long timeoutMiliseconds) throws TimeoutException {
        String responseStr = "";
        this.write(dataToWrite);
        //   System.out.println(addCharEnd("12345",'a'));
        responseStr = readWithTerminatorAndDelay(responseStr, terminator, timeoutMiliseconds);
        return responseStr;
    }

    private String read(String responseStr) throws TimeoutException {
        try (DataInputStream ins = new DataInputStream(serial.getInputStream())) {
            char b;
            if (ins.available() > 0) {
                b = (char) ins.read();
                responseStr = addCharEnd(responseStr, b);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return responseStr;
    }

    private String readWithTerminatorAndDelay(String responseStr, char terminator, long timeoutMiliseconds) throws TimeoutException {
        try (DataInputStream ins = new DataInputStream(serial.getInputStream())) {
            char b;
            long start = System.currentTimeMillis();
            long stop = 0;
            while (!Thread.interrupted()) {
                if (stop - start > timeoutMiliseconds) {
                    throw new TimeoutException("Receive too long ");
                }
                if (ins.available() > 0) {
                    b = (char) ins.read();
                    if (b == terminator) {
                        break;
                    }
                    responseStr = addCharEnd(responseStr, b);
                }
                stop = System.currentTimeMillis();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return responseStr;
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

  /*  public String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }*/

    public String addCharEnd(String str, char ch) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, len, updatedArr, 0);
        updatedArr[updatedArr.length - 1] = ch;
        return new String(updatedArr);
    }

   /* public String addChar(String str, char ch, int position) {
        return str.substring(0, position) + ch + str.substring(position);
    }*/
/*
    public String addCharOnEnd(String str, char ch) {
        return str.substring(0, str.length()) + ch ;
    }*/


   /* public String addChar(String str, char ch, int position) {
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return sb.toString();
    }*/


}
