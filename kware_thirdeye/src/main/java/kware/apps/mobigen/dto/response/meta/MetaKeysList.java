package kware.apps.mobigen.dto.response.meta;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MetaKeysList {

    public List<Items> items;

    @Getter @Setter
    public static class Items {
        private String name;
        private String type;
        private String description;
        private String status;
    }
}
