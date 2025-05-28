package kware.apps.asp.contents.domain;


import org.springframework.stereotype.Component;

import cetus.dao.SuperDao;

@Component
public class CetusContentsDao extends SuperDao<CetusContents> {

    public CetusContentsDao() {
        super("cetusContents");
    }

}
