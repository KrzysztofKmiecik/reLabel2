package pl.kmiecik.Utils;

public class Parameters {
    public Parameters(String ipAddress, int ipPort, String comPort, int boundRate) {
        this.ipAddress = ipAddress;
        this.ipPort = ipPort;
        this.comPort = comPort;
        this.boundRate = boundRate;
    }

    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getIpPort() {
        return ipPort;
    }

    public void setIpPort(int ipPort) {
        this.ipPort = ipPort;
    }

    public String getComPort() {
        return comPort;
    }

    public void setComPort(String comPort) {
        this.comPort = comPort;
    }

    public int getBoundRate() {
        return boundRate;
    }

    public void setBoundRate(int boundRate) {
        this.boundRate = boundRate;
    }

    private int ipPort;
    private String comPort;
    private int boundRate;


}
