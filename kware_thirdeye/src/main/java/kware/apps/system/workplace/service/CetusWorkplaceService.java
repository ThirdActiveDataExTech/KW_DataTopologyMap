package kware.apps.system.workplace.service;


import cetus.bean.Page;
import cetus.bean.Pageable;
import kware.apps.system.menu.dto.request.WorkplaceMenuSave;
import kware.apps.system.menu.service.CetusMenuInfoService;
import kware.apps.system.program.dto.request.ProgramSave;
import kware.apps.system.program.service.CetusProgrmInfoService;
import kware.apps.system.user.dto.request.WorkplaceUserSave;
import kware.apps.system.user.service.CetusUserService;
import kware.apps.system.workplace.domain.CetusWorkplace;
import kware.apps.system.workplace.domain.CetusWorkplaceDao;
import kware.apps.system.workplace.dto.request.SearchWorkplace;
import kware.apps.system.workplace.dto.request.WorkplaceSave;
import kware.apps.system.workplace.dto.response.WorkplaceList;
import kware.apps.thirdeye.enumstatus.EnumCodeDto;
import kware.apps.thirdeye.enumstatus.MenuRootCd;
import kware.apps.thirdeye.enumstatus.UserAuthorCd;
import kware.apps.thirdeye.enumstatus.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CetusWorkplaceService {

    private final CetusWorkplaceDao dao;

    private final CetusProgrmInfoService progrmInfoService;
    private final CetusMenuInfoService menuInfoService;
    private final CetusUserService userService;

    public Page<WorkplaceList> findWorkplacePage(SearchWorkplace search, Pageable pageable) {
        return dao.page("getWorkplacePage", "getWorkplacePageCount", search, pageable);
    }

    public CetusWorkplace findWorkplaceByUid(Long uid) {
        return dao.view(uid);
    }

    @Transactional
    public void saveWorkplace(WorkplaceSave request) {
        CetusWorkplace bean = new CetusWorkplace(request);
        dao.insert(bean);
        Long workplaceUid = bean.getUid();


        ProgramSave programRootBean = new ProgramSave("root", "/", "Y");
        Long programRootUid = progrmInfoService.saveProgram(programRootBean);

        ProgramSave programFooterRootBean = new ProgramSave("footer_root", "/", "Y");
        Long programFooterRootUid = progrmInfoService.saveProgram(programFooterRootBean);

        ProgramSave programHomeBean = new ProgramSave("컨텐츠 메인 (홈)", "/portal/home", "Y");
        Long programHomeUid = progrmInfoService.saveProgram(programHomeBean);

        // 2. cetus_menu_info
        List<EnumCodeDto> authorList = UserAuthorCd.toList();
        for (EnumCodeDto author: authorList) {
            WorkplaceMenuSave rootMenu = WorkplaceMenuSave.builder()
                    .programUid(programRootUid)
                    .menuNm("Main")
                    .authorCd(author.getCode())
                    .rootMenuCd(MenuRootCd.TOP_ROOT.name())
                    .workplaceUid(workplaceUid)
                    .upperMenuNo(null)
                    .sortNo(1)
                    .build();
            Long rootMenuUid = menuInfoService.saveWorkplaceMenu(rootMenu);

            WorkplaceMenuSave footerRootMenu = WorkplaceMenuSave.builder()
                    .programUid(programFooterRootUid)
                    .menuNm("FOOTER_ROOT")
                    .authorCd(author.getCode())
                    .rootMenuCd(MenuRootCd.FOOTER_ROOT.name())
                    .workplaceUid(workplaceUid)
                    .upperMenuNo(null)
                    .sortNo(2)
                    .build();
            Long footerRootMenuUid = menuInfoService.saveWorkplaceMenu(footerRootMenu);

            WorkplaceMenuSave homeMenu = WorkplaceMenuSave.builder()
                    .programUid(programHomeUid)
                    .menuNm("데이터 검색")
                    .authorCd(author.getCode())
                    .rootMenuCd(null)
                    .workplaceUid(workplaceUid)
                    .upperMenuNo(rootMenuUid)
                    .sortNo(2)
                    .build();
            Long homeMenuUid = menuInfoService.saveWorkplaceMenu(homeMenu);
        }

        String userId = "system"+workplaceUid;
        String userEmail = userId + "@kware.co.kr";
        WorkplaceUserSave userSaveRequest = WorkplaceUserSave.builder()
                .userId(userId)
                .password("1234")
                .userNm(userId)
                .userEmail(userEmail)
                .authorCd(UserAuthorCd.ROLE_SYSTEM.name())
                .status(UserStatus.APPROVED.name())
                .build();
        userService.saveWorkplaceUser(userSaveRequest, workplaceUid);
    }

    public void changeWorkplace(Long uid, WorkplaceSave request) {
        CetusWorkplace bean = new CetusWorkplace(uid, request);
        dao.update(bean);
    }
}
