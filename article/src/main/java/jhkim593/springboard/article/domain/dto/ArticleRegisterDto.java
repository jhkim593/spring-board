package jhkim593.springboard.article.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ArticleRegisterDto {
    private String title;
    private String content;
    private Long writerId;
    private Long boardId;
}
