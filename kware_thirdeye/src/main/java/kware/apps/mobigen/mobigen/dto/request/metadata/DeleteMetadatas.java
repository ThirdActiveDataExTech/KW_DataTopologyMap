package kware.apps.mobigen.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* @fileName     DeleteMetadatas
* @author       dahyeon
* @version      1.0.0
* @date         2025-09-23
* @summary      한개 이상의 메타데이터 삭제 진행
**/
@Getter @Setter
public class DeleteMetadatas {

    private List<String> metadata_ids;

}
