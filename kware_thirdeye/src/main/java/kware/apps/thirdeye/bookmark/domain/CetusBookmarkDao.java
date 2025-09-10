package kware.apps.thirdeye.bookmark.domain;

import cetus.dao.CetusDao;
import cetus.dao.SuperDao;
import kware.apps.thirdeye.bookmark.dto.request.CetusSearchBookmark;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusBookmarkDao extends SuperDao<CetusBookmark> {

    public CetusBookmarkDao(){
        super("cetusBookmark");
    }

    public List<CetusBookmark> findListByUserUid(CetusSearchBookmark bean){
        return selectList("findListByUserUid", bean);
    }

    public Boolean isBookmarkExists(CetusBookmark bean){
        return selectOne("isBookmarkExists", bean);
    }

    public void deleteBookMark(CetusBookmark bean) {
        delete("deleteBookMark", bean);
    }
}
