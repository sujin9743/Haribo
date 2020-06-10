package jsj.mjc.hobbybook;

public class Debate {
    String debateNum;
    String debateTitle;
    String debateText;
    String debateDate;
    String debateWriter;
    int debateComment;
    String debateImageUrl;

    public String getDebateNum() {
        return debateNum;
    }

    public void setDebateNum(String debateNum) {
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

    public int getDebateComment() {
        return debateComment;
    }

    public void setDebateComment(int debateComment) {
        this.debateComment = debateComment;
    }

    public String getDebateImageUrl() {
        return debateImageUrl;
    }

    public void setDebateImageUrl(String debateImageUrl) {
        this.debateImageUrl = debateImageUrl;
    }

    Debate(String debateNum, String debateTitle, String debateText, String debateDate, String debateWriter, int debateComment, String debateImageUrl) {
        this.debateNum = debateNum;
        this.debateTitle = debateTitle;
        this.debateText = debateText;
        this.debateDate = debateDate;
        this.debateWriter = debateWriter;
        this.debateComment = debateComment;
        this.debateImageUrl = debateImageUrl;
    }
}
