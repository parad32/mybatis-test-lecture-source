package com.ohgiraffers.Main;

import com.ohgiraffers.menu.controller.MenuController;
import com.ohgiraffers.view.MenuView;

public class Application {
    public static void main(String[] args) {
        MenuController menuController = new MenuController();
        MenuView view = new MenuView();

        while (true) {
            view.displayMenu(); // 메뉴 화면 출력
            int choice = view.getMenuChoice(); // 사용자 입력

            switch (choice) {
                case 1 -> menuController.selectAllMenu(); // 메뉴 전체 조회
                case 2 -> menuController.searchMenu(view.inputSearch()); // 특정 메뉴 조회
                case 3 -> menuController.registMenu(view.inputMenu()); // 신규 메뉴 추가
                case 4 -> menuController.modifyMenu(view.inputModifyMenu()); // 메뉴 수정
                case 5 -> menuController.deleteMenu(view.inputMenuCode()); // 메뉴 삭제
                default -> view.displayWarning(); // 잘못된 입력
            }
        }
    }
}
