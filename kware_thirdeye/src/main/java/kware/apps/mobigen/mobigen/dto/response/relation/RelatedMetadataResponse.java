package kware.apps.mobigen.mobigen.dto.response.relation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RelatedMetadataResponse {

    private Long uid;       // 추후 삭제
    private String regDt;   // 추후 삭제

    private String metadata_id;
    private String title;
    private float similarity_score;
    private List<String> common_fields;
}
