package kware.apps.manager.cetus.menu.domain;

import cetus.annotation.Key;
import cetus.annotation.TableName;
import cetus.bean.AuditBean;
import cetus.bean.CetusBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CetusMenuInfo extends AuditBean {
	@Key
	private Long menuNo;
	private Long programUid;
	private Long upperMenuNo;
	private String menuNm;
	private String menuIcon;
	private Integer sortNo;
	private String menuDc;
	private String useAt;
	private String deleteAt;
}
