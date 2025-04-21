package com.wedevloop.menus.service;

import com.wedevloop.menus.constants.MenuConstants;
import com.wedevloop.menus.entity.Menu;
import com.wedevloop.menus.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public Menu addMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MenuConstants.MENU_NOT_FOUND));
    }

    @Override
    public Menu updateMenu(Long id, Menu menu) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MenuConstants.MENU_NOT_FOUND));

        existingMenu.setName(menu.getName());
        existingMenu.setPrice(menu.getPrice());
        existingMenu.setDescription(menu.getDescription());

        return menuRepository.save(existingMenu);
    }

    @Override
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(MenuConstants.MENU_NOT_FOUND));

        menuRepository.delete(menu);
    }
}