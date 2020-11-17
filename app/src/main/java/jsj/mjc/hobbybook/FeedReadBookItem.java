package jsj.mjc.hobbybook;

public class FeedReadBookItem {
    private String bookCoverImgUrl;
    private String bookReId;

    public FeedReadBookItem(String bookReId, String bookCoverImgUrl) {
        this.bookReId = bookReId;
        this.bookCoverImgUrl = bookCoverImgUrl;
    }

    public String getBookCoverImgUrl() {
        return bookCoverImgUrl;
    }

    public String getbookReNum() {
        return bookReId;
    }
}
