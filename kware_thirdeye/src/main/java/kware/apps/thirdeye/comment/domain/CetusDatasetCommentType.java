package kware.apps.thirdeye.comment.domain;

import lombok.Getter;

@Getter
public enum CetusDatasetCommentType {

    OPINION("의견", "opinion"),
    QUESTION("문의", "question"),
    REPORT("오류신고", "report");

    private String description;
    private String code;

    CetusDatasetCommentType(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public static String getDescriptionByCode(String code) {
        for (CetusDatasetCommentType type : values()) {
            if (type.getCode().equals(code)) {
                return type.getDescription();
            }
        }
        return null; // 못 찾으면 null 반환 (Optional 써도 됨)
    }
}
