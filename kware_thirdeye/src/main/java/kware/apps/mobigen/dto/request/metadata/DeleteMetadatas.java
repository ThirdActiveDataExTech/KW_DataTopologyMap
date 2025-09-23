package kware.apps.mobigen.dto.request.metadata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DeleteMetadatas {

    private List<String> metadata_ids;

}
