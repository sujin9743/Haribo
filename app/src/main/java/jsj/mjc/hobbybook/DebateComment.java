package jsj.mjc.hobbybook;

public class DebateComment {
    String docId;
    int dcNum;
    int rcNum;
    int dcBundle;
    String dcText;
    String dcWriter;
    String dcDate;

    public DebateComment(int dc_num, int receive_com, String dc_content, String mem_id, String dateStr) {
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public int getDcNum() {
        return dcNum;
    }

    public void setDcNum(int dcNum) {
        this.dcNum = dcNum;
    }

    public int getRcNum() {
        return rcNum;
    }

    public void setRcNum(int rcNum) {
        this.rcNum = rcNum;
    }

    public int getDcBundle() {
        return dcBundle;
    }

    public void setDcBundle(int dcBundle) {
        this.dcBundle = dcBundle;
    }

    public String getDcText() {
        return dcText;
    }

    public void setDcText(String dcText) {
        this.dcText = dcText;
    }

    public String getDcWriter() {
        return dcWriter;
    }

    public void setDcWriter(String dcWriter) {
        this.dcWriter = dcWriter;
    }

    public String getDcDate() {
        return dcDate;
    }

    public void setDcDate(String dcDate) {
        this.dcDate = dcDate;
    }

    DebateComment(String docId, int dcNum, int rcNum, int dcBundle, String dcText, String dcWriter, String dcDate) {
        this.docId = docId;
        this.dcNum = dcNum;
        this.rcNum = rcNum;
        this.dcBundle = dcBundle;
        this.dcText = dcText;
        this.dcWriter = dcWriter;
        this.dcDate = dcDate;
    }
}
