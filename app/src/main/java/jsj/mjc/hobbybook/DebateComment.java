package jsj.mjc.hobbybook;

public class DebateComment {
    int dcNum;
    int rcNum;
    String dcText;
    String dcWriter;
    String dcDate;

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

    DebateComment(int dcNum, int rcNum, String dcText, String dcWriter, String dcDate) {
        this.dcNum = dcNum;
        this.rcNum = rcNum;
        this.dcText = dcText;
        this.dcWriter = dcWriter;
        this.dcDate = dcDate;
    }
}
