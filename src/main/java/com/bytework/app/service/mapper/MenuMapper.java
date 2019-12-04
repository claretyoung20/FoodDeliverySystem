package com.bytework.app.service.mapper;

import com.bytework.app.domain.*;
import com.bytework.app.service.dto.MenuDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Menu} and its DTO {@link MenuDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    MenuDTO toDto(Menu menu);

    @Mapping(source = "userId", target = "user")
    Menu toEntity(MenuDTO menuDTO);

    default Menu fromId(Long id) {
        if (id == null) {
            return null;
        }
        Menu menu = new Menu();
        menu.setId(id);
        return menu;
    }
}
