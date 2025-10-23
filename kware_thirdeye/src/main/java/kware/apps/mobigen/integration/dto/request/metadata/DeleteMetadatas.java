package kware.apps.mobigen.integration.dto.request.metadata;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteMetadatas {
    private List<Long> uids;
}
