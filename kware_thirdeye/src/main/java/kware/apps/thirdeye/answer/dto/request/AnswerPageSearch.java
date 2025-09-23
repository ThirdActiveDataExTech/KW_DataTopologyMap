package kware.apps.thirdeye.answer.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerPageSearch {

    private Long bbscttUid;

    public AnswerPageSearch(Long bbscttUid) {
        this.bbscttUid = bbscttUid;
    }
}
