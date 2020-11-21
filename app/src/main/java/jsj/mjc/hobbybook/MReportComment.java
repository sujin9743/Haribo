package jsj.mjc.hobbybook;

import android.widget.ImageButton;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MReportComment{

    String profileText,date,comment;
    String docId;
    int brcNum, brNum;

    /*public MReportComment(String id, String mem_id, String date, String brc_content, int brc_num, int br_num) {
        this.docId = id;
        this.profileText = mem_id;
        this.date = date;
        this.comment = brc_content;
        this.brcNum = brc_num;
        this.brNum = br_num;

    }*/

    public void setProfileText(String profileText) {
        this.profileText = profileText;
    }

    public String getProfileText() {
        return profileText;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setBrcNum(int brcNum) {
        this.brcNum = brcNum;
    }

    public int getBrcNum() {
        return brcNum;
    }

    public void setBrNum(int brNum) {
        this.brNum = brNum;
    }

    public int getBrNum() {
        return brNum;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }
}