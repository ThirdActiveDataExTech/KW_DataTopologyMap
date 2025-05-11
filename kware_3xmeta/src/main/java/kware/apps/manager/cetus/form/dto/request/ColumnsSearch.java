package kware.apps.manager.cetus.form.dto.request;

import cetus.user.UserUtil;
import lombok.Getter;
 @Getter
public class ColumnsSearch {
    private String type;
    private Long workplaceUid;

     public ColumnsSearch(String type) {
         this.type = type;
         this.workplaceUid = UserUtil.getUser().getWorkplaceUid();
     }
 }
