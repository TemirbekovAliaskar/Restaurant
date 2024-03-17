package java12.service.impl;

import jakarta.transaction.Transactional;
import java12.dto.request.MenuItemCheckRequest;
import java12.dto.response.ChequeResponse;
import java12.dto.response.DefaultResponse;
import java12.dto.response.GetCheckResponse;
import java12.dto.response.MenuResponse;
import java12.entity.Cheque;
import java12.entity.MenuItem;
import java12.entity.User;
import java12.entity.enums.Role;
import java12.exception.ForbiddenException;
import java12.exception.NotFoundException;
import java12.repository.ChequeRepository;
import java12.repository.MenuItemRepository;
import java12.repository.UserRepository;
import java12.service.ChequeService;
import java12.service.MenuService;
import java12.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Check;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final MenuService menuService;


    @Override
    @Transactional
    public DefaultResponse save(MenuItemCheckRequest menuItemCheckRequest) {

        User currentUser = getCurrentUser();
        List<Long> menu = new ArrayList<>();

        menu.addAll(menuItemCheckRequest.menuItemIds());
        List<MenuItem> allMenuId = menuItemRepository.getAllMenuId(menu);
        int total = 0;
        for (MenuItem menuItem : allMenuId) {
            total += menuItem.getPrice();
        }
        Cheque cheque = new Cheque();

        cheque.setPriceTotal(total);
        cheque.setService(total * cheque.procient);
        cheque.setUser(currentUser);
        chequeRepository.save(cheque);
        cheque.getMenuItems().addAll(allMenuId);
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Успешно сохранен чек !")
                .build();
    }

    @Override
    @Transactional
    public DefaultResponse update(Long cheId, MenuItemCheckRequest checkRequest) {
        Cheque cheque = chequeRepository.getByIds(cheId);

        List<Long> menu = new ArrayList<>();
        menu.addAll(checkRequest.menuItemIds());
        List<MenuItem> updateMenuId = menuItemRepository.getAllMenuId(menu);

        if (checkRequest.menuItemIds() == null) {
            throw new NotFoundException("Error !");
        } else {
            for (MenuItem menuItem : updateMenuId) {
                if (cheque.getMenuItems().contains(menuItem)) {
                    cheque.getMenuItems().remove(menuItem);
                } else cheque.getMenuItems().add(menuItem);
            }
            int total = 0;
            for (MenuItem menuItem : updateMenuId) {
                total += menuItem.getPrice();
            }
            double service = total * (cheque.procient);
            cheque.setPriceTotal(total);
            cheque.setService(service);
            return DefaultResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(" Успешно изменен чек !")
                    .build();
        }
    }

    @Override
    public DefaultResponse delete(Long cheId) {
        chequeRepository.delete(chequeRepository.getByIds(cheId));
        return DefaultResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(" Успешно удален чек !")
                .build();
    }

    @Override
    public List<GetCheckResponse> getAll() {
        List<GetCheckResponse> getAll = new ArrayList<>();
        List<Cheque> getCheckResponses = chequeRepository.findAll();
        for (Cheque getCheckRespons : getCheckResponses) {
           getAll.add(findCheckById(getCheckRespons.getId()));
        }
        return getAll;
    }

    @Override
    public GetCheckResponse findCheckById(Long checkId) {
        ChequeResponse find = chequeRepository.findBY(checkId);
        List<MenuResponse> menuItemResponse = menuItemRepository.checkById(checkId);
        double grandTotal = find.priceTotal() + find.service();

        return GetCheckResponse.builder()
                .chequeResponse(find)
                .menuItems(menuItemResponse)
                .percent(find.percent()+"%")
                .grandTotal(grandTotal)
                .build();
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User current = userRepository.getByEmail(email);
        if (current.getRole().equals(Role.ADMIN) || current.getRole().equals(Role.WAITER))
            return current;
        else throw new ForbiddenException("Forbidden 403");
    }
}
