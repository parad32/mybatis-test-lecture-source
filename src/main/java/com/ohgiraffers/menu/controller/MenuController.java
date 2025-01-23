package com.ohgiraffers.menu.controller;
import com.ohgiraffers.common.util.PrintResult;
import com.ohgiraffers.menu.model.dto.MenuDTO;
import com.ohgiraffers.menu.model.service.MenuService;

import java.util.List;
import java.util.Map;

public class MenuController {

    // MenuService 및 결과 출력 클래스 선언
    private final MenuService menuService;
    private final PrintResult printResult;

    // 생성자에서 MenuService와 PrintResult 객체 초기화
    public MenuController() {
        menuService = new MenuService();
        printResult = new PrintResult();
    }

    /**
     * 전체 메뉴 조회 요청 처리 메서드
     * - 서비스 레이어에서 모든 메뉴 데이터를 받아온 후
     *   결과를 출력.
     */
    public void selectAllMenu() {
        List<MenuDTO> menuList = menuService.selectAllMenu(); // 전체 메뉴 조회

        if (menuList != null) {
            printResult.printMenuList(menuList); // 메뉴 리스트 출력
        } else {
            printResult.printErrorMessage("selectList"); // 조회 실패 메시지 출력
        }
    }

    /**
     * 신규 메뉴 등록 요청 처리 메서드
     * - 사용자 입력 데이터를 받아 `MenuDTO` 객체로 생성 후
     *   서비스 레이어를 통해 DB에 저장 요청.
     */
    public void registMenu(Map<String, String> parameter) {
        // 입력값에서 신규 메뉴 정보 추출
        String name = parameter.get("name"); // 메뉴 이름
        int price = Integer.parseInt(parameter.get("price")); // 메뉴 가격
        int categoryCode = Integer.parseInt(parameter.get("categoryCode")); // 카테고리 코드

        // 입력값으로 MenuDTO 객체 생성
        MenuDTO newMenu = new MenuDTO();
        newMenu.setName(name);
        newMenu.setPrice(price);
        newMenu.setCategoryCode(categoryCode);

        // 메뉴 등록 요청 수행 및 결과 출력
        if (menuService.registMenu(newMenu)) {
            printResult.printSuccessMessage("insert"); // 성공 메시지 출력
        } else {
            printResult.printErrorMessage("insert"); // 실패 메시지 출력
        }
    }

    /**
     * 메뉴 수정 요청 처리 메서드
     * - 사용자로부터 입력받은 변경 데이터를 기반으로
     *   기존 메뉴 정보를 수정.
     */
    public void modifyMenu(Map<String, String> parameter) {
        // 입력값에서 수정할 메뉴 정보 추출
        int code = Integer.parseInt(parameter.get("code")); // 메뉴 코드
        String name = parameter.get("name"); // 수정할 메뉴 이름
        int price = Integer.parseInt(parameter.get("price")); // 수정할 메뉴 가격
        int categoryCode = Integer.parseInt(parameter.get("categoryCode")); // 수정할 카테고리 코드

        // 입력값으로 MenuDTO 객체 생성
        MenuDTO menuToUpdate = new MenuDTO();
        menuToUpdate.setCode(code);
        menuToUpdate.setName(name);
        menuToUpdate.setPrice(price);
        menuToUpdate.setCategoryCode(categoryCode);

        // 메뉴 수정 요청 수행 및 결과 출력
        if (menuService.modifyMenu(menuToUpdate)) {
            printResult.printSuccessMessage("update"); // 성공 메시지 출력
        } else {
            printResult.printErrorMessage("update"); // 실패 메시지 출력
        }
    }

    /**
     * 메뉴 삭제 요청 처리 메서드
     * - 사용자로부터 메뉴 코드를 입력받아 해당 메뉴 삭제 요청 수행.
     */
    public void deleteMenu(Map<String, String> parameter) {
        int code = Integer.parseInt(parameter.get("code")); // 삭제할 메뉴 코드 추출

        // 메뉴 삭제 요청 수행 및 결과 출력
        if (menuService.deleteMenu(code)) {
            printResult.printSuccessMessage("delete"); // 성공 메시지 출력
        } else {
            printResult.printErrorMessage("delete"); // 실패 메시지 출력
        }
    }

    /**
     * 동적 검색 요청 처리 메서드
     * - 사용자로부터 검색 조건(유형 및 키워드)을 입력받아
     *   해당 조건에 맞는 메뉴 리스트를 조회.
     */
    public void searchMenu(Map<String, String> searchCriteria) {
        // 검색 조건이 설정되지 않은 경우 처리
        if (searchCriteria == null) {
            System.out.println("검색 조건이 설정되지 않았습니다. 올바른 값을 입력해주세요.");
            return;
        }

        // 검색 유형과 키워드 추출
        String type = searchCriteria.get("type"); // 검색 유형 (menuName 또는 categoryName)
        String keyword = searchCriteria.get("keyword"); // 검색 키워드

        // 키워드가 비어있는 경우 처리
        if (keyword == null || keyword.isEmpty()) {
            System.out.println("검색 키워드를 입력하지 않았습니다. 다시 시도해주세요.");
            return;
        }

        try {
            // 검색 결과를 서비스 레이어에서 받아옴
            List<MenuDTO> searchResults = menuService.searchMenu(type, keyword);

            // 검색 결과 출력
            if (searchResults.isEmpty()) {
                System.out.println("검색 결과가 없습니다.");
            } else {
                searchResults.forEach(System.out::println); // 검색 결과 출력
            }
        } catch (Exception e) {
            System.out.println("검색 중 오류가 발생했습니다: " + e.getMessage()); // 오류 메시지 출력
        }
    }
}
