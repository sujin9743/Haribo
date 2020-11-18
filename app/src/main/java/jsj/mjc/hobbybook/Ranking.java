package jsj.mjc.hobbybook;

public class Ranking {
    private String rankingNum;
    private String rankingImageUrl;
    private String rankingTitle;
    private String rankingWriter;
    private String rankingAuthor;
    private String bookDesc;

    public String getRankingNum() {
        return rankingNum;
    }

    public void setRankingNum(String rankingNum) {
        this.rankingNum = rankingNum;
    }

    public String getRankingImageUrl() {
        return rankingImageUrl;
    }

    public void setRankingImageUrl(String rankingImageUrl) {
        this.rankingImageUrl = rankingImageUrl;
    }

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
}
