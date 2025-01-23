package com.ohgiraffers.menu.model.dao;


import com.ohgiraffers.menu.model.dto.MenuDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {


    List<MenuDTO> selectAllMenu();

    MenuDTO selectMenuByCode(int code);

    int registMenu(MenuDTO menu);

    int modifyMenu(MenuDTO menu);

    int deleteMenu(int code);

    List<MenuDTO> searchMenuByName(@Param("keyword") String keyword);
    List<MenuDTO> searchMenuByCategory(@Param("keyword") String keyword);
}
