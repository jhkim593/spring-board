package jhkim593.springboard.article.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardArticleCount is a Querydsl query type for BoardArticleCount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardArticleCount extends EntityPathBase<BoardArticleCount> {

    private static final long serialVersionUID = 74561938L;

    public static final QBoardArticleCount boardArticleCount = new QBoardArticleCount("boardArticleCount");

    public final NumberPath<Long> articleCount = createNumber("articleCount", Long.class);

    public final NumberPath<Long> boardId = createNumber("boardId", Long.class);

    public QBoardArticleCount(String variable) {
        super(BoardArticleCount.class, forVariable(variable));
    }

    public QBoardArticleCount(Path<? extends BoardArticleCount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardArticleCount(PathMetadata metadata) {
        super(BoardArticleCount.class, metadata);
    }

}

