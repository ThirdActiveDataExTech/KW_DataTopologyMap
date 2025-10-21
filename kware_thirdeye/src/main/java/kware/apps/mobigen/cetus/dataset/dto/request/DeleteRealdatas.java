package kware.apps.mobigen.cetus.dataset.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DeleteRealdatas {
    private List<String> fileIds;
}
