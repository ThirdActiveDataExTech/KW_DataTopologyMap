package kware.apps.thirdeye.comment.dto.request;

import cetus.annotation.DisplayName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** 평점·의견 등록 요청. 유형 코드가 실제 코드(CETUS_SYS_CODE DATASET_COMMENT)인지는 서비스가 확인한다 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DatasetCommentSave {
    /** 의견 내용 최대 길이. 화면 입력은 한 줄 텍스트이고 저장 컬럼은 text 라 서버에서 상한을 둔다 */
    public static final int MAX_COMMENT_LENGTH = 1000;
    public static final int MAX_RATINGS = 5;

    @NotNull @DisplayName("데이터셋")
    private Long approvedUid;
    @NotBlank @Size(max = 32) @DisplayName("의견 유형")
    private String typeCd;
    @Min(0) @Max(MAX_RATINGS) @DisplayName("평점")
    private Integer ratings;                // 별 0~5개(0 = 평점 없이 의견만)
    @NotBlank @Size(max = MAX_COMMENT_LENGTH) @DisplayName("의견 내용")
    private String comment;
}
