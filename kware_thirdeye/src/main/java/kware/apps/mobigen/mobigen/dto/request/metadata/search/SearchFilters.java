package kware.apps.mobigen.mobigen.dto.request.metadata.search;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
public class SearchFilters {

    private String publisher;
    private List<String> keyword;
    private List<String> theme;
    private SearchDataRange date_range;

    public SearchFilters(String publisher, List<String> keyword, List<String> theme, SearchDataRange date_range) {
        this.publisher = publisher;
        this.keyword = keyword;
        // TODO 추후에 'theme' 넘겨주기로 변경
        this.theme = Arrays.asList("theme003");
        this.date_range = date_range;
    }
}
