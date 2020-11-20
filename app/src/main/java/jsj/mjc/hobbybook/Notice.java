package jsj.mjc.hobbybook;

public class Notice {
    private String docId;
    private String sendId;
    private String dateText;
    private int type;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    Notice(String docId, String sendId, String dateText, int type) {
        this.docId = docId;
        this.sendId = sendId;
        this.dateText = dateText;
        this.type = type;
    }
}
