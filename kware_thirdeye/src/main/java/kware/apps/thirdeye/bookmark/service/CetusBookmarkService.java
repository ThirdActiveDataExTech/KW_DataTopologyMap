
package kware.apps.thirdeye.bookmark.service;

import cetus.user.UserUtil;
import kware.apps.mobigen.cetus.dataset.dto.response.MobigenDatasetView;
import kware.apps.mobigen.cetus.dataset.service.CetusMobigenDatasetService;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService;
import kware.apps.thirdeye.bookmark.domain.CetusBookMark;
import kware.apps.thirdeye.bookmark.domain.CetusBookMarkDao;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMark;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.request.UserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.response.UserBookMarkList;
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
    private final CetusApprovedDatasetService datasetService;
    private final CetusMobigenDatasetService mobigenDatasetService;

    @Transactional(readOnly = true)
    public List<UserBookMarkList> findUserBookMarkList(SearchUserBookMark search ) {
        List<UserBookMarkList> userBookMarkList = dao.getUserBookMarkList(search);
        for ( UserBookMarkList userBookMark : userBookMarkList ) {
            MobigenDatasetView mobigenDatasetView = mobigenDatasetService.findMobigenDatasetByDatasetId(userBookMark.getDatasetId());
            userBookMark.setMobigenDatasetView(mobigenDatasetView);
        }
        return userBookMarkList;
    }

    @Transactional
    public Boolean toggleLike( UserBookMarkToggle request ) {

        Long userUid = UserUtil.getUser().getUid();
        SearchUserBookMarkToggle search = new SearchUserBookMarkToggle( userUid, request.getApprovedUid() );

        // 기존 좋아요 체크
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

    @Transactional(readOnly = true)
    public Boolean isBookmarkExists( SearchUserBookMarkToggle search ) {
        return dao.isBookMarkExists(search);
    }

}
