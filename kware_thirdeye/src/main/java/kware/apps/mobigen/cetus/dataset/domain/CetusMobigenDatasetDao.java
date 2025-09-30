package kware.apps.mobigen.cetus.dataset.domain;


import cetus.dao.SuperDao;
import org.springframework.stereotype.Component;

@Component
public class CetusMobigenDatasetDao extends SuperDao<CetusMobigenDataset> {

    public CetusMobigenDatasetDao() {
        super("cetusMobigenDataset");
    }
}
