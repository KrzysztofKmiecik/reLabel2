package pl.kmiecik.Utils;

public class Fis {

    private String ipAdress;
    private int port;

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
    public Fis(String ipAdress, int port) {
        this.ipAdress = ipAdress;
        this.port = port;
    }

    public String sendMessageToFis(String old2DCode, String new2DCode, String newAPN) {

        String messageBREQ="NOmessageBREQ";
        String responseBREQ ="NOresponseBREQ";
        String messageBCMP = "NOmessageBCMP";
        String responseBCMP = "NOresponseBCMP";
        try (IpClient ipClient = new IpClient()) {
            messageBREQ = "BREQ|id=" + old2DCode + "|process=REFLASH|station=BMW_RM_REFLASH_1|newid=" + new2DCode;
            responseBREQ = ipClient.sendAndReceiveIPMessage(ipAdress, port, messageBREQ);


            if (responseBREQ.contains("status=PASS")) {
                messageBCMP = "BCMP|id=" + old2DCode + "|newid=" + new2DCode + "|station=BMW_RM_REFLASH_1|process=REFLASH|status=PASS|endmodel=" + newAPN + "|errorcode=";
                responseBCMP = ipClient.sendAndReceiveIPMessage(ipAdress, port, messageBREQ);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder result=new StringBuilder(messageBREQ+";");
        result.append(responseBREQ+";").append(messageBCMP+";").append(responseBCMP);
        return result.toString();
    }
}
