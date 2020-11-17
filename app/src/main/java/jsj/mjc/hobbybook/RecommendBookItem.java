package jsj.mjc.hobbybook;

import android.util.Log;

public class RecommendBookItem {

    private String bookImgUrl;
    private String bookTitle, bookWriter, bookPublisher, bookRateTxt, bookIsbn;
    float bookRate;

    /*public RecommendBookItem(String bookTitle, String bookWriter, String bookPublisher, float bookRate, String bookRateTxt) {
        this.bookTitle = bookTitle;
        this.bookWriter = bookWriter;
        this.bookPublisher = bookPublisher;
        this.bookRate = bookRate;
        this.bookRateTxt = bookRateTxt;
    }*/

    public void setBookImgUrl(String bookImgUrl) {
        this.bookImgUrl = bookImgUrl;
    }

    public String getBookImgUrl() {
        return bookImgUrl;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookWriter(String bookWriter) {
        this.bookWriter = bookWriter;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookRate(float bookRate) {
        this.bookRate = bookRate;
    }

    public Float getBookRate() {
        return bookRate;
    }

    public void setBookRateTxt(String bookRateTxt) {
        this.bookRateTxt = bookRateTxt;
    }

    public String getBookRateTxt() { return bookRateTxt; }

    public String getBookIsbn() {
        return bookIsbn;
    }
}
