package kware.apps.system.workplace.service;


import kware.apps.system.workplace.domain.CetusWorkplaceDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CetusWorkplaceService {

    private final CetusWorkplaceDao dao;
}
