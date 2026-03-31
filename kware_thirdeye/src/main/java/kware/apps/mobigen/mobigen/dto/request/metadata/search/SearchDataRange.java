package kware.apps.mobigen.mobigen.dto.request.metadata.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter @ToString
public class SearchDataRange {

    private String start;
    private String end;

    public SearchDataRange(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
