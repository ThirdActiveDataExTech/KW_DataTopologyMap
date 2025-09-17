package kware.apps.system.position.service;


import cetus.user.UserUtil;
import kware.apps.system.position.domain.CetusPosition;
import kware.apps.system.position.domain.CetusPositionDao;
import kware.apps.system.position.dto.request.PositionMerge;
import kware.apps.system.position.dto.request.PositionMergeList;
import kware.apps.system.position.dto.response.PositionList;
import kware.apps.system.position.dto.response.PositionListWithUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusPositionService {

    private final CetusPositionDao dao;

    @Transactional(readOnly = true)
    public List<PositionList> findPositionList() {
        return dao.getPositionList(UserUtil.getUser().getWorkplaceUid());
    }

    @Transactional(readOnly = true)
    public List<PositionListWithUser> findPositionListByWorkplaceUid(Long workplceUid) {
        return dao.getPositionListWithUser(workplceUid);
    }

    public void mergePosition(PositionMergeList request) {
        for (PositionMerge position: request.getPositions()) {
            CetusPosition bean = new CetusPosition(request.getWorkplaceUid(), position);
            if(position.getUid() == null) dao.insert(bean);
            else dao.update(bean);
        }
        if(request.getUids().length != 0) {
            for(Long uid : request.getUids()) {
                CetusPosition bean = new CetusPosition(uid);
                dao.updateUseAtToN(bean);
            }
        }
    }
}
