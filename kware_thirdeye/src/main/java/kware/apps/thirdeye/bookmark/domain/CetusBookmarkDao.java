package kware.apps.thirdeye.bookmark.domain;

import cetus.dao.SuperDao;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMark;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.response.UserBookMarkList;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CetusBookMarkDao extends SuperDao<CetusBookMark> {

    public CetusBookMarkDao(){
        super("cetusBookMark");
    }

    public List<UserBookMarkList> getUserBookMarkList(SearchUserBookMark search ){
        return selectList("getUserBookMarkList", search);
    }

    public Boolean isBookMarkExists( SearchUserBookMarkToggle search ){
        return selectOne("isBookMarkExists", search);
    }

    public void deleteBookMark( CetusBookMark bean ) {
        delete("deleteBookMark", bean);
    }
}
