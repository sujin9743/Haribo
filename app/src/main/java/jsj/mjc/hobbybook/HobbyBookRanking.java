package jsj.mjc.hobbybook;

public class HobbyBookRanking {
    private String rankingNum;
    private String rankingImageUrl;
    private String rankingTitle;
    private String rankingWriter;
    private String rankingAuthor;
    private String bookDesc;
    private String bookIsbn;

    public String getRankingNum() { return rankingNum; }

    public void setRankingNum(String rankingNum) {
        this.rankingNum = rankingNum;
    }

    public String getRankingImageUrl() {
        return rankingImageUrl;
    }

    public void setRankingImageUrl(String rankingImageUrl) { this.rankingImageUrl = rankingImageUrl; }

    public String getRankingTitle() {
        return rankingTitle;
    }

    public void setRankingTitle(String rankingTitle) {
        this.rankingTitle = rankingTitle;
    }

    public String getRankingWriter() {
        return rankingWriter;
    }

    public void setRankingWriter(String rankingWriter) {
        this.rankingWriter = rankingWriter;
    }

    public void setBookDesc (String bookDesc) { this.bookDesc = bookDesc; }

    public String getBookDesc() { return bookDesc; }

    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }

    public String getBookIsbn() { return bookIsbn; }

    public HobbyBookRanking(String num) {
        this.rankingNum = num;
    }
}
