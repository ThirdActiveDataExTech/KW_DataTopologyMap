
package kware.apps.thirdeye.bookmark.service;

import cetus.user.UserUtil;
import kware.apps.thirdeye.bookmark.domain.CetusBookMark;
import kware.apps.thirdeye.bookmark.domain.CetusBookMarkDao;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMark;
import kware.apps.thirdeye.bookmark.dto.request.SearchUserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.request.UserBookMarkToggle;
import kware.apps.thirdeye.bookmark.dto.response.UserBookMarkList;
import kware.apps.thirdeye.mobigen.approveddataset.dto.response.ApprovedDatasetItem;
import kware.apps.thirdeye.mobigen.approveddataset.service.CetusApprovedDatasetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusBookMarkService {

    private final CetusBookMarkDao dao;
    private final CetusApprovedDatasetService approvedDatasetService;

    @Transactional(readOnly = true)
    public List<UserBookMarkList> findUserBookMarkList(SearchUserBookMark search ) {
        List<UserBookMarkList> userBookMarkList = dao.getUserBookMarkList(search);
        if (userBookMarkList.isEmpty()) {
            return userBookMarkList;
        }
        // 북마크 건수만큼 상세·모비젠을 부르지 않고 진열 정보를 한 번에 가져와 붙인다
        List<Long> approvedUids = userBookMarkList.stream()
                .map(UserBookMarkList::getApprovedUid)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, ApprovedDatasetItem> datasetViewByUid = approvedDatasetService.findApprovedDatasetViewList(approvedUids).stream()
                .collect(Collectors.toMap(ApprovedDatasetItem::getApprovedUid, Function.identity(), (first, dup) -> first));
        for ( UserBookMarkList userBookMark : userBookMarkList ) {
            userBookMark.setDatasetView(datasetViewByUid.get(userBookMark.getApprovedUid()));
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
            dao.deleteBookMarkByApprovedUidAndUser(bean);
             return false;
        } else {
             dao.insert(bean);
             return true;
        }
    }

    @Transactional
    public void deleteBookMarkByApprovedUidAndUser( Long approvedUid ) {
        CetusBookMark bean = new CetusBookMark( UserUtil.getUser().getUid(), approvedUid );
        dao.deleteBookMarkByApprovedUidAndUser(bean);
    }

}
