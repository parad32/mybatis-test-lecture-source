package com.ohgiraffers.menu.model.service;

import com.ohgiraffers.common.config.Template;
import com.ohgiraffers.menu.model.dao.MenuMapper;
import com.ohgiraffers.menu.model.dto.MenuDTO;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.ohgiraffers.common.config.Template.getSqlSession;

public class MenuService {

    private MenuMapper menuMapper; // MenuMapper 생성 (MyBatis 매퍼 인터페이스)

    /**
     * 전체 메뉴 조회 메서드
     * - MyBatis SqlSession과 매퍼 인터페이스를 통해 전체 메뉴 데이터 조회
     * @return List<MenuDTO> 전체 메뉴 리스트
     */
    public List<MenuDTO> selectAllMenu() {
        // 1. SqlSession 객체 생성
        SqlSession sqlSession = getSqlSession();

        // 2. MenuMapper 인터페이스에서 쿼리 실행 후 결과 반환
        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
        List<MenuDTO> menuList = menuMapper.selectAllMenu();

        // 3. SqlSession 객체 반납
        sqlSession.close();

        // 4. 조회 결과 반환
        return menuList;
    }

    /**
     * 특정 메뉴 조회 메서드
     * - 메뉴 코드에 해당하는 데이터를 데이터베이스에서 조회
     * @param code 조회하려는 메뉴의 코드
     * @return MenuDTO 검색된 메뉴 데이터
     */
    public MenuDTO selectMenuByCode(int code) {
        // 1. SqlSession 객체 생성
        SqlSession sqlSession = getSqlSession();

        // 2. MenuMapper 인터페이스에서 쿼리 실행
        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
        MenuDTO foundMenu = menuMapper.selectMenuByCode(code);

        // 3. SqlSession 객체 반납
        sqlSession.close();

        // 4. 조회 결과 반환
        return foundMenu;
    }

    /**
     * 신규 메뉴 등록 메서드
     * - 사용자가 입력한 메뉴 데이터를 데이터베이스에 등록
     * - 트랜잭션 처리(커밋/롤백) 포함
     * @param newMenu 등록하려는 신규 메뉴 데이터
     * @return boolean 등록 성공 여부
     */
    public boolean registMenu(MenuDTO newMenu) {
        // 1. SqlSession 객체 생성
        SqlSession sqlSession = getSqlSession();

        // 2. MenuMapper 인터페이스에서 삽입 쿼리 실행
        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
        int result = menuMapper.registMenu(newMenu);

        // 3. 결과에 따라 트랜잭션 처리
        if (result > 0) {
            sqlSession.commit(); // 삽입 성공 시 커밋
        } else {
            sqlSession.rollback(); // 삽입 실패 시 롤백
        }

        // 4. SqlSession 객체 반납
        sqlSession.close();

        // 5. 삽입 성공 여부 반환
        return result > 0;
    }

    /**
     * 기존 메뉴 수정 메서드
     * - 수정할 메뉴 데이터를 입력받아 데이터베이스 정보 업데이트
     * - 트랜잭션 처리(커밋/롤백) 포함
     * @param menuToUpdate 수정하려는 메뉴 데이터
     * @return boolean 수정 성공 여부
     */
    public boolean modifyMenu(MenuDTO menuToUpdate) {
        // 1. SqlSession 객체 생성
        SqlSession sqlSession = getSqlSession();

        // 2. MenuMapper 인터페이스에서 업데이트 쿼리 실행
        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
        int result = menuMapper.modifyMenu(menuToUpdate);

        // 3. 결과에 따라 트랜잭션 처리
        if (result > 0) {
            sqlSession.commit(); // 수정 성공 시 커밋
        } else {
            sqlSession.rollback(); // 수정 실패 시 롤백
        }

        // 4. SqlSession 객체 반납
        sqlSession.close();

        // 5. 수정 성공 여부 반환
        return result > 0;
    }

    /**
     * 기존 메뉴 삭제 메서드
     * - 삭제할 메뉴 코드를 입력받아 데이터베이스에서 해당 데이터 삭제
     * - 트랜잭션 처리(커밋/롤백) 포함
     * @param code 삭제하려는 메뉴의 코드
     * @return boolean 삭제 성공 여부
     */
    public boolean deleteMenu(int code) {
        // 1. SqlSession 객체 생성
        SqlSession sqlSession = getSqlSession();

        // 2. MenuMapper 인터페이스에서 삭제 쿼리 실행
        MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
        int result = menuMapper.deleteMenu(code);

        // 3. 결과에 따라 트랜잭션 처리
        if (result > 0) {
            sqlSession.commit(); // 삭제 성공 시 커밋
        } else {
            sqlSession.rollback(); // 삭제 실패 시 롤백
        }

        // 4. SqlSession 객체 반납
        sqlSession.close();

        // 5. 삭제 성공 여부 반환
        return result > 0;
    }

    /**
     * 동적 검색 요청 메서드
     * - 검색 유형과 키워드를 입력받아 특정 조건에 맞는 메뉴 조회
     * @param type 검색 유형 ("menuName" 또는 "categoryName")
     * @param keyword 검색 키워드
     * @return List<MenuDTO> 검색 결과 리스트
     */
    public List<MenuDTO> searchMenu(String type, String keyword) {
        // try-with-resources 문 사용: SqlSession 자동 반납 처리
        try (SqlSession session = Template.getSqlSession()) {
            // MenuMapper 인터페이스 취득
            MenuMapper menuMapper = session.getMapper(MenuMapper.class);

            // 검색 유형에 따라 적절한 쿼리 호출
            if ("menuName".equalsIgnoreCase(type)) {
                return menuMapper.searchMenuByName(keyword); // 메뉴 이름으로 검색
            } else if ("categoryName".equalsIgnoreCase(type)) {
                return menuMapper.searchMenuByCategory(keyword); // 카테고리 이름으로 검색
            } else {
                throw new IllegalArgumentException("유효하지 않은 검색 유형입니다."); // 잘못된 검색 유형 처리
            }
        }
    }
}
