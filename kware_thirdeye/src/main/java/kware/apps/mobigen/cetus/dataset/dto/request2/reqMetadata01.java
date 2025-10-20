package kware.apps.mobigen.cetus.dataset.dto.request2;

import lombok.*;

import java.util.List;

@Getter @Setter @ToString
public class reqMetadata01 {

    private final String action = "search";
    private reqMetadata01_filters filters;
    private reqMetadata01_pagination pagination;
    private reqMetadata01_sort sort;

    @Getter @Setter @ToString
    public class reqMetadata01_filters {
        private String publisher;
        private List<String> theme;
        private reqMetadata01_range date_range;

        @Getter @Setter @ToString
        public class reqMetadata01_range {
            private String start;
            private String end;
        }
    }

    @Getter @Setter @ToString
    public class reqMetadata01_pagination {
        private int page;
        private int limit;
    }

    @Getter @Setter
    public class reqMetadata01_sort {
        private String order;
    }
}
