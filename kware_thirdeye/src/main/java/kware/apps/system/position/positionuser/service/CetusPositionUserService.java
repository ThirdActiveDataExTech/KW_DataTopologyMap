package kware.apps.system.position.positionuser.service;


import kware.apps.system.position.positionuser.domain.CetusPositionUser;
import kware.apps.system.position.positionuser.domain.CetusPositionUserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CetusPositionUserService {

    private final CetusPositionUserDao dao;

    @Transactional
    public void savePositionUser(Long positionUid, Long userUid) {
        dao.insertPositionUser(new CetusPositionUser(positionUid, userUid));
    }

    @Transactional
    public void resetPositionUser(Long userUid) {
        dao.deletePositionUser(userUid);
    }
}
