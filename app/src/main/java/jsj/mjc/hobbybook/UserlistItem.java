package jsj.mjc.hobbybook;

import android.graphics.drawable.Drawable;

public class UserlistItem {
    //private String profile_Img_Url;
    private String id;
    private String btnTxt;

    public UserlistItem(String id, String btnTxt) {
        //this.profile_Img_Url = profile_Img_Url;
        this.id = id;
        this.btnTxt = btnTxt;
    }

    //회원 프로필 사진 받아올 때 구현
    //public String getPicture() {
    //    return profile_Img_Url;
    //}

    //회원 닉네임
    public String getId() {
        return id;
    }

    public String getBtnTxt() { return btnTxt; }
}
