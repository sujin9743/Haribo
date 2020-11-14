package jsj.mjc.hobbybook;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MRealtime {
    private Drawable profileImg;    //circleimg
    private String profileText, bookName, bookCreator,likeCnt, commentCnt;
    private Drawable bookImgPage;       //viewpager임
    private Drawable heart;             //imageview

    public void setProfileImg(Drawable pImg){
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
    public void setLikeCnt(String lCnt){
        likeCnt = lCnt;
    }
    public void setCommentCnt(String cCnt){
        commentCnt = cCnt;
    }
    public void setBookImgPage(Drawable bImgPage){
        bookImgPage = bImgPage;
    }
    public void setHeart(Drawable h){
        heart = h;
    }

    public Drawable getProfileImg(){
        return this.profileImg;
    }
    public String getProfileText(){
        return this.profileText;
    }
    public String getBookName(){
        return this.bookName;
    }
    public String getBookCreator(){
        return this.bookCreator;
    }
    public String getLikeCnt(){
        return this.likeCnt;
    }
    public String getCommentCnt(){
        return this.commentCnt;
    }
    public Drawable getBookImgPage(){
        return this.bookImgPage;
    }
    public Drawable getHeart(){
        return this.heart;
    }

    MRealtime(String profileText, String bookName, String bookCreator, String likeCnt, String commentCnt){
        this.profileText = profileText;
        this.bookName = bookName;
        this.bookCreator = bookCreator;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
    }

}
