package kware.apps.thirdeye.approveddataset.dto.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteApprovedDatasets {

    private List<Long> uids;
}
