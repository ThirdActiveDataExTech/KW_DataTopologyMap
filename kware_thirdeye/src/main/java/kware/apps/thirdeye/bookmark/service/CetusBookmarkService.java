
package kware.apps.thirdeye.bookmark.service;

import cetus.user.UserUtil;
import kware.apps.thirdeye.bookmark.domain.CetusBookMark;
import kware.apps.thirdeye.bookmark.domain.CetusBookMarkDao;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMark;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.request.UserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.response.UserBookMarkList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetView;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusBookMarkService {

    private final CetusBookMarkDao dao;
    private final CetusApprovedDatasetService approvedDatasetService;

    @Transactional(readOnly = true)
    public List<UserBookMarkList> findUserBookMarkList(SearchUserBookMark search ) {
        List<UserBookMarkList> userBookMarkList = dao.getUserBookMarkList(search);
        for ( UserBookMarkList userBookMark : userBookMarkList ) {
            Long approvedUid = userBookMark.getApprovedUid();
            ApprovedDatasetView datasetView = approvedDatasetService.findApprovedDatasetView(approvedUid, false, false, false);
            userBookMark.setDatasetView(datasetView);
        }
        return userBookMarkList;
    }

    @Transactional
    public Boolean toggleLike( UserBookMarkToggle request ) {
        Long userUid = UserUtil.getUser().getUid();
        SearchUserBookMarkToggle search = new SearchUserBookMarkToggle( userUid, request.getApprovedUid() );

        Boolean wishExists = dao.isBookMarkExists(search);
        CetusBookMark bean = new CetusBookMark(userUid, request.getApprovedUid());
        if( wishExists ) {
            dao.deleteBookMark(bean);
             return false;
        } else {
             dao.insert(bean);
             return true;
        }
    }

    @Transactional
    public void deleteBookMark( Long approvedUid ) {
        CetusBookMark bean = new CetusBookMark( UserUtil.getUser().getUid(), approvedUid );
        dao.deleteBookMark(bean);
    }

}
