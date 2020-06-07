package jsj.mjc.hobbybook;

public class Ranking {
    private String rankingNum;
    private String rankingImageUrl;
    private String rankingTitle;
    private String rankingWriter;

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

    public Ranking(String rankingNum, String rankingImageUrl, String rankingTitle, String rankingWriter) {
        this.rankingNum = rankingNum;
        this.rankingImageUrl = rankingImageUrl;
        this.rankingTitle = rankingTitle;
        this.rankingWriter = rankingWriter;
    }
}
