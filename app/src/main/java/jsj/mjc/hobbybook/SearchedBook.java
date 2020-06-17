package jsj.mjc.hobbybook;

public class SearchedBook {
    private String bookImageUrl;
    private String bookTitle;
    private String bookWriter;

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookWriter() {
        return bookWriter;
    }

    public void setBookWriter(String bookWriter) {
        this.bookWriter = bookWriter;
    }

    public SearchedBook(String bookImageUrl, String bookTitle, String bookWriter) {
        this.bookImageUrl = bookImageUrl;
        this.bookTitle = bookTitle;
        this.bookWriter = bookWriter;
    }
}
