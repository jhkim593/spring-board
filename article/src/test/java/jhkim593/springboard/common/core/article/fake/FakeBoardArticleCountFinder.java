//package jhkim593.springboard.article.fake;
//
//import jhkim593.springboard.article.application.provided.BoardArticleCountFinder;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class FakeBoardArticleCountFinder implements BoardArticleCountFinder {
//
//    private final Map<Long, Long> boardCounts = new HashMap<>();
//
//    public void setBoardCount(Long boardId, Long count) {
//        boardCounts.put(boardId, count);
//    }
//
//    public void clear() {
//        boardCounts.clear();
//    }
//
//    @Override
//    public Long count(Long boardId) {
//        return boardCounts.getOrDefault(boardId, 0L);
//    }
//}