package jsj.mjc.hobbybook;

public class DebateComment {
    String dcNum;
    String rcNum;
    String dcText;
    String dcWriter;
    String dcDate;

    public String getDcNum() {
        return dcNum;
    }

    public void setDcNum(String dcNum) {
        this.dcNum = dcNum;
    }

    public String getRcNum() {
        return rcNum;
    }

    public void setRcNum(String rcNum) {
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

    DebateComment(String dcNum, String rcNum, String dcText, String dcWriter, String dcDate) {
        this.dcNum = dcNum;
        this.rcNum = rcNum;
        this.dcText = dcText;
        this.dcWriter = dcWriter;
        this.dcDate = dcDate;
    }
}
