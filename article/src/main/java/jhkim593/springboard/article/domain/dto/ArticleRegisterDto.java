package jhkim593.springboard.article.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleRegisterDto {
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;
}
