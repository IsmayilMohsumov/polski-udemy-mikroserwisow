package com.wedevloop.menus.service;

import com.wedevloop.menus.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu addMenu(Menu menu);
    List<Menu> getAllMenus();
    Menu getMenuById(Long id);
    Menu updateMenu(Long id, Menu menu);
    void deleteMenu(Long id);
}