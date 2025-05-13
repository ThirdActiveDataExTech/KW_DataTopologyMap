package kware.apps.manager.cetus.form.domain;

import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CetusColumnOptionsDao extends SuperDao<CetusColumnOptions> {

    public CetusColumnOptionsDao() {
        super("cetusColumnOptions");
    }

    public List<CetusColumnOptions> list(Long columnsUid) {
        return selectList("list", columnsUid);
    }

    public Optional<CetusColumnOptions> findByUid(Long uid) {
        return Optional.of(selectOne("findByUid", uid));
    }

    public Integer findNextSortNum(Long columnsUid) {
        return selectOne("findNextSortNum", columnsUid);
    }

}
