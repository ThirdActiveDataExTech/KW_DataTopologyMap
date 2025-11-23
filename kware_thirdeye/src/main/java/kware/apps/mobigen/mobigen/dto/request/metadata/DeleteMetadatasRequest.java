package kware.apps.mobigen.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
* @fileName     DeleteMetadatasRequest
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      [METADATA_02] 한개 이상의 메타데이터 삭제 요청 DTO
**/
@Getter @Setter @ToString
public class DeleteMetadatasRequest {

    private String action;                  // "delete"
    private List<String> metadata_ids;      // ["1", "2", ... ]

    public DeleteMetadatasRequest(List<String> metadata_ids) {
        this.action = "delete";
        this.metadata_ids = metadata_ids;
    }

}
