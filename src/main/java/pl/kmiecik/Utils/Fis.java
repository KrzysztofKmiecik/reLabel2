package pl.kmiecik.Utils;

public class Fis {

    private final  String ipAdress;
    private final  int port;
    private boolean communicationResultOK;

    public String getIpAdress() {
        return ipAdress;
    }

    public int getPort() {
        return port;
    }

    public boolean isCommunicationResultOK() {
        return communicationResultOK;
    }


    /**
     * FIS server  "10.235.241.235", 24401
     * <p>
     * Check if newid wasn't used
     * request   BREQ|id=125|process=REFLASH|station=BMW_RM_REFLASH_1|newid=124
     * response  BCNF|id=125|status=PASS|
     * <p>
     * Send
     * request   BCMP|id=125|newid=124|station=BMW_RM_REFLASH_1|process=REFLASH|status=PASS|endmodel=11111111|errorcode=
     * response  BACK|id=125|status=PASS
     */
    public Fis(final String ipAdress, final int port) {
        this.ipAdress = ipAdress;
        this.port = port;
        communicationResultOK = false;
    }

    public String sendMessageToFis(final String old2DCode, final String new2DCode, final String newAPN) {

        String messageBREQ = "NOmessageBREQ";
        String responseBREQ = "NOresponseBREQ";
        String messageBCMP = "NOmessageBCMP";
        String responseBCMP = "NOresponseBCMP";
        try (IpClient ipClient = new IpClient()) {
            messageBREQ = "BREQ|id=" + old2DCode + "|process=REFLASH|station=BMW_RM_REFLASH_1|newid=" + new2DCode;
            responseBREQ = ipClient.sendAndReceiveIPMessage(ipAdress, port, messageBREQ);


            if (responseBREQ.contains("status=PASS")) {
                messageBCMP = "BCMP|id=" + old2DCode + "|newid=" + new2DCode + "|station=BMW_RM_REFLASH_1|process=REFLASH|status=PASS|endmodel=" + newAPN + "|errorcode=";
                responseBCMP = ipClient.sendAndReceiveIPMessage(ipAdress, port, messageBCMP);

                if (responseBCMP.contains("status=PASS")) {
                    this.communicationResultOK = true;
                }

            } else {
                this.communicationResultOK = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final StringBuilder result = new StringBuilder(messageBREQ + ";");
        result.append(responseBREQ + ";").append(messageBCMP + ";").append(responseBCMP);
        return result.toString();
    }
}
