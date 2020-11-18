package jsj.mjc.hobbybook;

public class MBookCom {

    String profileImg, profileText, date, reviewText, delete;

    public MBookCom(String inputtime, String mem_id, String rv_content) {
        this.profileText = mem_id;
        this.date = inputtime;
        this.reviewText = rv_content;
    }

    public void setProfileImg(String pImg){profileImg = pImg;}
    public String getProfileImg() {
        return this.profileImg;
    }
    public void setProfileText(String pText){profileText = pText;}
    public String getProfileText(){
        return this.profileText;
    }
    public void setDate(String d){date = d;}
    public String getDate() {
        return this.date;
    }
    public void setReviewText(String rText){reviewText = rText;}
    public String getReviewText() {
        return this.reviewText;
    }
    public void setDelete(String del){delete = del;}
    public String getDelete() {
        return this.delete;
    }
}
