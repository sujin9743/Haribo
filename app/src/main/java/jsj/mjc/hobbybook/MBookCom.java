package jsj.mjc.hobbybook;

public class MBookCom {

    String profileImg, profileText, date, reviewText, delete;

    public MBookCom(String mem_id, String date, String rv_content) {
        this.profileText = mem_id;
        this.date = date;
        this.reviewText = rv_content;
    }


    public void setProfileImg(String pImg){profileImg = pImg;}
    public String getProfileImg() {
        return profileImg;
    }
    public void setProfileText(String pText){profileText = pText;}
    public String getProfileText(){
        return profileText;
    }
    public void setDate(String d){date = d;}
    public String getDate() {
        return date;
    }
    public void setReviewText(String rText){reviewText = rText;}
    public String getReviewText() {
        return reviewText;
    }
    public void setDelete(String del){delete = del;}
    public String getDelete() {
        return delete;
    }
}
