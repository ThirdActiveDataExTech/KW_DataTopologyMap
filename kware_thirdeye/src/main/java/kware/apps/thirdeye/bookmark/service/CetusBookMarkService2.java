package kware.apps.thirdeye.bookmark.service;

import java.util.List;

import kware.apps.mobigen.mobigen.dto.request.common.PaginationRequest;
import kware.apps.thirdeye.bookmark.domain.CetusBookMarkDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusBookMarkService2 {

    private final CetusBookMarkDao dao;

    @Transactional
    public void deleteBookMarkByMetadataIds(List<String> metadataIds) {
        dao.deleteBookMarkByMetadataIds(metadataIds);
    }

    @Transactional
    public void deleteBookMarkByApprovedUid( Long approvedUid ) {
        dao.deleteBookMarkByApprovedUid(approvedUid);
    }
}
