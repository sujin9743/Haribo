package jsj.mjc.hobbybook;

public class FeedReadBookItem {
    private String bookCoverImgUrl;
    private int bookReNum;

    public FeedReadBookItem(int bookReNum, String bookCoverImgUrl) {
        this.bookReNum = bookReNum;
        this.bookCoverImgUrl = bookCoverImgUrl;
    }

    public String getBookCoverImgUrl() {
        return bookCoverImgUrl;
    }

    public int getbookReNum() {
        return bookReNum;
    }
}
