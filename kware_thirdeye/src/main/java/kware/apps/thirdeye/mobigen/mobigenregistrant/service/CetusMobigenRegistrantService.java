package kware.apps.thirdeye.mobigen.mobigenregistrant.service;


import cetus.user.UserUtil;
import kware.apps.thirdeye.mobigen.mobigenregistrant.domain.CetusMobigenRegistrant;
import kware.apps.thirdeye.mobigen.mobigenregistrant.domain.CetusMobigenRegistrantDao;
import kware.apps.thirdeye.mobigen.mobigenregistrant.dto.response.MobigenRegistrantView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CetusMobigenRegistrantService {

    private final CetusMobigenRegistrantDao dao;

    /**
     * @method      saveMobigenRegistrant
     * @author      dahyeon
     * @date        2025-10-13
     * @deacription 모비젠 측에 데이터셋 등록 시점에 등록자 정보는 우리쪽에서 관리하기 위해 등록자 정보 저장
     *              => 해당 데이터셋들은 승인 이전 시점이어도 정보를 저장한다.
    **/
    @Transactional
    public void saveMobigenRegistrant(String metadataId) {
        CetusMobigenRegistrant bean = new CetusMobigenRegistrant(metadataId, UserUtil.getUser().getUid());
        dao.insert(bean);
    }

    /**
     * @method      findMobigenRegistrant
     * @author      dahyeon
     * @date        2025-10-15
     * @deacription 모비젠 데이터셋 저장소에 데이터셋을 등록한 사용자 정보를 조회
    **/
    @Transactional(readOnly = true)
    public MobigenRegistrantView findMobigenRegistrant(String metadataId) {
        return dao.getMobigenRegistrant(metadataId);
    }
}
