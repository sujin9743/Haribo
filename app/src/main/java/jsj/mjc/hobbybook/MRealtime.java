package jsj.mjc.hobbybook;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MRealtime {
    private Uri profileImg;    //circleimg
    private String profileText, brTitle, bookName, bookCreator,likeCnt, commentCnt;
    private String bookImgPage;       //viewpager임
    private Drawable heart;             //imageview

    public void setProfileImg(Uri pImg){
        profileImg = pImg;
    }
    public void setProfileText(String pText){
        profileText = pText;
    }
    public void setBookName(String bName){
        bookName = bName;
    }
    public void setBookCreator(String bCreator){
        bookCreator = bCreator;
    }
    public void setBrTitle(String brTitle) {this.brTitle = brTitle;}
    public void setLikeCnt(String lCnt){
        likeCnt = lCnt;
    }
    public void setCommentCnt(String cCnt){
        commentCnt = cCnt;
    }
    public void setBookImgPage(String bImgPage){
        bookImgPage = bImgPage;
    }
    public void setHeart(Drawable h){
        heart = h;
    }

    public Uri getProfileImg(){ return profileImg; }
    public String getProfileText(){
        return profileText;
    }
    public String getBookName(){
        return bookName;
    }
    public String getBookCreator(){
        return bookCreator;
    }
    public String getBrTitle() {return brTitle;}
    public String getLikeCnt(){
        return likeCnt;
    }
    public String getCommentCnt(){
        return commentCnt;
    }
    public String getBookImgPage(){
        return bookImgPage;
    }
    public Drawable getHeart(){
        return heart;
    }

    /*MRealtime(String profileText, String img, String bookName, String bookCreator, String likeCnt, String commentCnt){
        this.profileText = profileText;
        this.bookImgPage = img;
        this.bookName = bookName;
        this.bookCreator = bookCreator;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
    }*/

}
