//package jhkim593.springboard.articleread.adapter.event.handler;
//import jhkim593.springboard.articleread.adapter.event.EventHandler;
//import jhkim593.springboard.articleread.application.provided.ArticleReadFinder;
//import jhkim593.springboard.common.event.payload.ArticleUpdatedEventPayload;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ArticleUpdatedEventHandler implements EventHandler<ArticleUpdatedEventPayload> {
//    private final ArticleReadFinder articleQueryModelRepository;
//
//    @Override
//    public void handle(Event<ArticleUpdatedEventPayload> event) {
//        articleQueryModelRepository.read(event.getPayload().getArticleId())
//                .ifPresent(articleQueryModel -> {
//                    articleQueryModel.updateBy(event.getPayload());
//                    articleQueryModelRepository.update(articleQueryModel);
//                });
//    }
//
//    @Override
//    public boolean supports(Event<ArticleUpdatedEventPayload> event) {
//        return EventType.ARTICLE_UPDATED == event.getType();
//    }
//}
//
