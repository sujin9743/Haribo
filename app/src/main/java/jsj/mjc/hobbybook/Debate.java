package jsj.mjc.hobbybook;

public class Debate {
    String debateDocId;
    int debateNum;
    String debateTitle;
    String debateText;
    String debateDate;
    String debateWriter;

    public String getDebateDocId() {
        return debateDocId;
    }

    public void setDebateDocId(String debateNum) {
        this.debateDocId = debateDocId;
    }

    public int getDebateNum() {
        return debateNum;
    }

    public void setDebateNum(int debateNum) {
        this.debateNum = debateNum;
    }

    public String getDebateTitle() {
        return debateTitle;
    }

    public void setDebateTitle(String debateTitle) {
        this.debateTitle = debateTitle;
    }

    public String getDebateText() {
        return debateText;
    }

    public void setDebateText(String debateText) {
        this.debateText = debateText;
    }

    public String getDebateDate() {
        return debateDate;
    }

    public void setDebateDate(String debateDate) {
        this.debateDate = debateDate;
    }

    public String getDebateWriter() {
        return debateWriter;
    }

    public void setDebateWriter(String debateWriter) {
        this.debateWriter = debateWriter;
    }

    Debate(String debateDocId, int debateNum, String debateTitle, String debateText, String debateDate, String debateWriter) {
        this.debateDocId = debateDocId;
        this.debateNum = debateNum;
        this.debateTitle = debateTitle;
        this.debateText = debateText;
        this.debateDate = debateDate;
        this.debateWriter = debateWriter;
    }
}
