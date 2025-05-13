package kware.apps.manager.cetus.user.domain;

import cetus.bean.Page;
import cetus.bean.Pageable;
import cetus.dao.SuperDao;
import kware.apps.manager.cetus.user.dto.request.UserExcelSearch;
import kware.apps.manager.cetus.user.dto.request.UserListSearch;
import kware.apps.manager.cetus.user.dto.response.UserExcelPage;
import kware.apps.manager.cetus.user.dto.response.UserFullInfo;
import kware.apps.manager.cetus.user.dto.response.UserList;
import kware.apps.manager.cetus.user.dto.response.UserView;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CetusUserDao extends SuperDao<CetusUser> {

    public CetusUserDao() {
        super("cetusUser");
    }

    public UserFullInfo getUserByUserId(String userId) {
        return selectOne("getUserByUserId", userId);
    }

    public int updateFailCnt(CetusUser bean) {
        return update("updateFailCnt", bean);
    }

    public Page<UserExcelPage> excelPage(UserExcelSearch bean, Pageable pageable) {
        return page("excelPageList", "excelPageCount", bean , pageable);
    }

    public int updateUserFailCnt(CetusUser bean) {
        return update("updateUserFailCnt", bean);
    }

    public int updateUserUseAt(CetusUser bean) {
        return update("updateUserUseAt", bean);
    }

    public UserView getUserInfoByUid(Long userUid) {
        return selectOne("getUserInfoByUid", userUid);
    }

    public Page<UserList> userPage(UserListSearch search, Pageable pageable) {
        return page("userList", "userListCount", search, pageable);
    }

    public Page<UserExcelPage> userExcelPage(UserExcelSearch search, Pageable pageable) {
        return page("userExcelList", "userExcelCount", search, pageable);
    }

    public List<UserList> userExcelList(UserExcelSearch search) {
        return selectList("userExcelList", search);
    }

    public int userExcelCount(UserExcelSearch search) {
        return selectOne("userExcelCount", search);
    }

    public int updateUserStatus(CetusUser bean) {
        return update("updateUserStatus", bean);
    }

    public int updateUserAuthorCd(CetusUser bean) {
        return update("updateUserAuthorCd", bean);
    }
}
