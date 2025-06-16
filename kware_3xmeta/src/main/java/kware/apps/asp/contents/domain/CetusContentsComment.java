package kware.apps.asp.contents.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 컨텐츠 댓글 도메인 클래스
 */
@Getter
@Setter
@ToString
public class CetusContentsComment {
    
    /** 댓글 고유번호 */
    private Long uid;
    
    /** 컨텐츠 고유번호 */
    private Long contentsUid;
    
    /** 댓글 타입 */
    private String type;
    
    /** 평점 */
    private Integer ratings;
    
    /** 댓글 내용 */
    private String comment;
    
    /** 등록자 고유번호 */
    private Long regUid;
    
    /** 등록일시 */
    private String regDt;
    
    /** 수정자 고유번호 */
    private Long updtUid;
    
    /** 수정일시 */
    private String updtDt;
} 