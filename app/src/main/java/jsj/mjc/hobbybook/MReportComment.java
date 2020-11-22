package jsj.mjc.hobbybook;

import android.widget.ImageButton;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MReportComment{
    String docId;
    int cNum;
    int rcNum;
    int cBundle;
    String cText;
    String cWriter;
    String cDate;

    
    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public int getCNum() {
        return cNum;
    }

    public void setCNum(int cNum) {
        this.cNum = cNum;
    }

    public int getRcNum() {
        return rcNum;
    }

    public void setRcNum(int rcNum) {
        this.rcNum = rcNum;
    }

    public int getCBundle() {
        return cBundle;
    }

    public void setCBundle(int cBundle) {
        this.cBundle = cBundle;
    }

    public String getCText() {
        return cText;
    }

    public void setCText(String cText) {
        this.cText = cText;
    }

    public String getCWriter() {
        return cWriter;
    }

    public void setCWriter(String cWriter) {
        this.cWriter = cWriter;
    }

    public String getCDate() {
        return cDate;
    }

    public void setCDate(String cDate) {
        this.cDate = cDate;
    }

    MReportComment(String docId, int cNum, int rcNum, int cBundle, String cText, String cWriter, String cDate) {
        this.docId = docId;
        this.cNum = cNum;
        this.rcNum = rcNum;
        this.cBundle = cBundle;
        this.cText = cText;
        this.cWriter = cWriter;
        this.cDate = cDate;
    }
}