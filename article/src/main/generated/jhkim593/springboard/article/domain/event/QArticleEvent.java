package jhkim593.springboard.article.domain.event;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleEvent is a Querydsl query type for ArticleEvent
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QArticleEvent extends EntityPathBase<ArticleEvent> {

    private static final long serialVersionUID = -2140663131L;

    public static final QArticleEvent articleEvent = new QArticleEvent("articleEvent");

    public final NumberPath<Long> articleId = createNumber("articleId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final EnumPath<jhkim593.springboard.common.event.EventType> eventType = createEnum("eventType", jhkim593.springboard.common.event.EventType.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath payload = createString("payload");

    public final BooleanPath published = createBoolean("published");

    public QArticleEvent(String variable) {
        super(ArticleEvent.class, forVariable(variable));
    }

    public QArticleEvent(Path<? extends ArticleEvent> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleEvent(PathMetadata metadata) {
        super(ArticleEvent.class, metadata);
    }

}

