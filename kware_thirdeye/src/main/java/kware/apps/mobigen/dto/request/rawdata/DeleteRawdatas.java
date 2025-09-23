package kware.apps.mobigen.dto.request.rawdata;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DeleteRawdatas {

    private List<String> rawdata_ids;
}
