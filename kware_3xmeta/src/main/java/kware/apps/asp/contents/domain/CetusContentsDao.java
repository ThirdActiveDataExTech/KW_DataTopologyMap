package kware.apps.asp.contents.domain;


import java.util.List;

import org.springframework.stereotype.Component;

import cetus.dao.SuperDao;
import kware.apps.asp.contents.dto.response.ContentsView;

@Component
public class CetusContentsDao extends SuperDao<CetusContents> {

    public CetusContentsDao() {
        super("cetusContents");
    }

    public ContentsView contentsView(Long uid) {
        return selectOne("view", uid);
    }

    public List<CetusTags> findTagsByContentsUid(Long uid) {
        return selectList("findTagsByContentsUid", uid);
    }

}
