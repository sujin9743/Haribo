package jsj.mjc.hobbybook;

import android.widget.ImageButton;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MReportComment{

    private String profileText,date,comment;
    private String docId;
    private int brcNum, brNum;

    public MReportComment() {
    }

    public void setProfileText(String profileText) {
        this.profileText = profileText;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBrcNum(int brcNum) {
        this.brcNum = brcNum;
    }

    public void setBrNum(int brNum) {
        this.brNum = brNum;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getProfileText() {
        return profileText;
    }

    public String getDate() {
        return date;
    }

    public int getBrcNum() {
        return brcNum;
    }

    public int getBrNum() {
        return brNum;
    }

    public String getComment() {
        return comment;
    }

    public String getDocId() {
        return docId;
    }
}