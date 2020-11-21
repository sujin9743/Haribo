package jsj.mjc.hobbybook;

public class Message {
    String docId;
    String mSender;
    String mReciever;
    String mDate;
    String mText;
    Boolean seen;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String mNum) {
        this.docId = mNum;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public String getmReciever() {
        return mReciever;
    }

    public void setmReciever(String mReciever) {
        this.mReciever = mReciever;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mtext) {
        this.mText = mtext;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean mtext) {
        this.seen = seen;
    }

    Message(String docId, String mSender, String mReciever, String mDate, String mText, Boolean seen) {
        this.docId = docId;
        this.mSender = mSender;
        this.mReciever = mReciever;
        this.mDate = mDate;
        this.mText = mText;
        this.seen = seen;
    }
}
