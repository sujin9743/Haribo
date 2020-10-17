package jsj.mjc.hobbybook;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import de.hdodenhof.circleimageview.CircleImageView;

public class MRealtime {
    CircleImageView profileImg;
    TextView profileText, bookName, bookCreator, likeCnt, commentCnt;
    ViewPager bookImgPage;
    ImageView heart;

    public MRealtime(CircleImageView profileImg, TextView profileText, TextView bookName, TextView bookCreator, TextView likeCnt, TextView commentCnt, ViewPager bookImgPage, ImageView heart) {
        this.profileImg = profileImg;
        this.profileText = profileText;
        this.bookName = bookName;
        this.bookCreator = bookCreator;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.bookImgPage = bookImgPage;
        this.heart = heart;
    }

    public CircleImageView getProfileImg() {
        return profileImg;
    }
    public void setProfileImg(CircleImageView profileImg){
        this.profileImg = profileImg;
    }

    public TextView getProfileText() {
        return profileText;
    }
    public void setProfileText(TextView profileText){
        this.profileText = profileText;
    }

    public ImageView getHeart() {
        return heart;
    }

    public TextView getBookCreator() {
        return bookCreator;
    }

    public TextView getBookName() {
        return bookName;
    }

    public TextView getCommentCnt() {
        return commentCnt;
    }

    public TextView getLikeCnt() {
        return likeCnt;
    }

    public ViewPager getBookImgPage() {
        return bookImgPage;
    }

    public void setBookCreator(TextView bookCreator) {
        this.bookCreator = bookCreator;
    }

    public void setBookImgPage(ViewPager bookImgPage) {
        this.bookImgPage = bookImgPage;
    }

    public void setBookName(TextView bookName) {
        this.bookName = bookName;
    }

    public void setCommentCnt(TextView commentCnt) {
        this.commentCnt = commentCnt;
    }

    public void setHeart(ImageView heart) {
        this.heart = heart;
    }

    public void setLikeCnt(TextView likeCnt) {
        this.likeCnt = likeCnt;
    }
}
