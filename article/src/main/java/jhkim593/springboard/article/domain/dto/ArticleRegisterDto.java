package jhkim593.springboard.article.domain.dto;

import lombok.Getter;

@Getter
public class ArticleRegisterDto {
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;
}
