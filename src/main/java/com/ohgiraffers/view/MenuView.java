package com.ohgiraffers.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuView {
    private final Scanner sc = new Scanner(System.in);

    // 메뉴 화면 출력
    public void displayMenu() {
        System.out.println("=========== 메뉴 관리 프로그램 ===========");
        System.out.println("1. 메뉴 전체 조회");
        System.out.println("2. 메뉴 코드로 메뉴 조회");
        System.out.println("3. 신규 메뉴 추가");
        System.out.println("4. 메뉴 수정");
        System.out.println("5. 메뉴 삭제");
        System.out.print("[INFO] 메뉴 관리 번호를 입력하세요 : ");
    }

    // 메뉴 번호 입력받기 (입력값 검증 추가)
    public int getMenuChoice() {
        while (true) {
            try {
                if (!sc.hasNextInt()) {
                    System.out.println("[ERROR] 숫자를 입력해주세요.");
                    sc.next(); // 잘못된 입력 제거
                    continue;
                }
                return sc.nextInt();
            } catch (Exception e) {
                System.out.println("[ERROR] 메뉴 번호 입력 중 오류가 발생했습니다. 다시 시도해주세요.");
                sc.nextLine(); // 입력 버퍼 초기화
            }
        }
    }

    public Map<String, String> inputSearch() {
        Map<String, String> searchCriteria = new HashMap<>();

        while (true) {
            System.out.print("검색 유형을 선택하세요 (menuName/categoryName): ");
            String type = sc.nextLine().trim().toLowerCase(); // 입력값을 소문자로 변환

            // 검색 유형 유효성 검사
            if (!type.equals("menuname") && !type.equals("categoryname")) {
                if (!type.isEmpty()) { // 입력값이 비어있지 않을 때만 오류 메시지 출력
                    System.out.println("[ERROR] 정확한 값을 입력해주세요.");
                }
                continue; // 유효하지 않으면 다시 입력받기
            }

            System.out.print("검색할 키워드를 입력하세요: ");
            String keyword = sc.nextLine().trim();

            if (keyword.isEmpty()) {
                System.out.println("[ERROR] 검색 키워드가 비어 있습니다. 다시 입력해주세요.");
                continue; // 유효하지 않은 키워드도 다시 입력받음
            }

            // 검색 조건을 맵에 저장
            searchCriteria.put("type", type);
            searchCriteria.put("keyword", keyword.toLowerCase()); // 키워드도 소문자로 저장

            break; // 모든 입력이 유효하면 반복 종료
        }

        return searchCriteria;
    }


    // 메뉴 코드 입력받기
    public Map<String, String> inputMenuCode() {
        sc.nextLine(); // 버퍼 비우기
        System.out.print("메뉴 코드를 입력하세요 : ");
        String code = sc.nextLine();

        Map<String, String> parameter = new HashMap<>();
        parameter.put("code", code);

        return parameter;
    }

    // 신규 메뉴 정보 입력받기
    public Map<String, String> inputMenu() {
        sc.nextLine(); // 버퍼 비우기
        System.out.print("메뉴 이름을 입력하세요 : ");
        String name = sc.nextLine();

        System.out.print("메뉴 가격을 입력하세요 : ");
        String price = sc.nextLine();

        System.out.print("카테고리 코드를 입력하세요 : ");
        String categoryCode = sc.nextLine();

        Map<String, String> parameter = new HashMap<>();
        parameter.put("name", name);
        parameter.put("price", price);
        parameter.put("categoryCode", categoryCode);

        return parameter;
    }

    // 수정할 메뉴 정보 입력받기
    public Map<String, String> inputModifyMenu() {
        sc.nextLine(); // 버퍼 비우기
        System.out.print("수정할 메뉴 코드를 입력하세요 : ");
        String code = sc.nextLine();

        System.out.print("수정할 메뉴 이름을 입력하세요 : ");
        String name = sc.nextLine();

        System.out.print("수정할 메뉴 가격을 입력하세요 : ");
        String price = sc.nextLine();

        System.out.print("수정할 카테고리 코드를 입력하세요 : ");
        String categoryCode = sc.nextLine();

        Map<String, String> parameter = new HashMap<>();
        parameter.put("code", code);
        parameter.put("name", name);
        parameter.put("price", price);
        parameter.put("categoryCode", categoryCode);

        return parameter;
    }

    // 경고 메시지 출력
    public void displayWarning() {
        System.out.println("[WARN] 잘못된 메뉴를 선택하셨습니다.");
    }
}
