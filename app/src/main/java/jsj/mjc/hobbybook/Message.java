package jsj.mjc.hobbybook;

public class Message {
    String mNum;
    String mSender;
    String mReciever;
    String mDate;
    String mText;

    public String getmNum() {
        return mNum;
    }

    public void setmNum(String mNum) {
        this.mNum = mNum;
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

    Message(String mNum, String mSender, String mReciever, String mDate, String mText) {
        this.mNum = mNum;
        this.mSender = mSender;
        this.mReciever = mReciever;
        this.mDate = mDate;
        this.mText = mText;
    }
}
