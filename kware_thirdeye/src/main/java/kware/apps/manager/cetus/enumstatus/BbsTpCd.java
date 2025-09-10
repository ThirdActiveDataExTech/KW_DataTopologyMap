package kware.apps.manager.cetus.enumstatus;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum BbsTpCd {

    NOTICE("notice", "공지사항"),
    BOARD("board", "자유 게시판"),
    REPORT("report", "오류 신고"),
    MANUAL("manual", "이용안내"),
    FAQ("faq", "faq"),
    QNA("qna", "1:1문의");

    private String subCode;
    private String subName;

    BbsTpCd(String subCode, String subName) {
        this.subCode = subCode;
        this.subName = subName;
    }

    // sub code → enum
    public static BbsTpCd fromSubcode(String subCode) {
        for (BbsTpCd type : BbsTpCd.values()) {
            if (type.getSubCode().equalsIgnoreCase(subCode)) {
                return type;
            }
        }
        return null; // 못 찾았을 경우
    }

    // code string -> sub name
    public static String getSubNameByCode(String code) {
        try {
            return BbsTpCd.valueOf(code).getSubName();
        } catch (IllegalArgumentException e) {
            return "";
        }
    }


    // code string -> sub code
    public static String getSubCodeByCode(String code) {
        try {
            return BbsTpCd.valueOf(code).getSubCode();
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    public static List<EnumCodeDto> toList() {
        return Arrays.stream(values())
                .map(e -> new EnumCodeDto(e.name(), e.getSubName()))
                .collect(Collectors.toList());
    }

    public static boolean isValidCode(String code) {
        return Arrays.stream(BbsTpCd.values())
                .anyMatch(status -> status.name().equals(code));
    }
}
