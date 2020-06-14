package jsj.mjc.hobbybook;

public class RecommendBookItem {

    //private String bookImgUrl;
    private String bookTitle, bookWriter, bookPublisher, bookRateTxt;
    float bookRate;

    public RecommendBookItem(String bookTitle, String bookWriter, String bookPublisher, float bookRate, String bookRateTxt) {
        this.bookTitle = bookTitle;
        this.bookWriter = bookWriter;
        this.bookPublisher = bookPublisher;
        this.bookRate = bookRate;
        this.bookRateTxt = bookRateTxt;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public Float getbookRate() {
        return bookRate;
    }

    public String getBookRateTxt() { return bookRateTxt; }
}
