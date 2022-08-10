package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.UserService;
import es.eoi.humillator2000.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.Optional;

@Controller
public class UserController extends AbstractController<UserDTO> {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                         Model model) {
        final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Page<UserDTO> all = this.service.findAll( PageRequest.of(page.orElse(1) - 1, size.orElse(10)));
        model
                .addAttribute("username", user.getLogin())
                .addAttribute("users", all)
                .addAttribute(pageNumbersAttributeKey, getPageNumbers(all));
        return "users/list";
    }

    @GetMapping("/users/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[user].userId == authentication.principal.id")
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("user", this.service.findById(id));
        return "users/detail";
    }

    @GetMapping("/users/{id}/edit")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[user].userId == authentication.principal.id")
    public String edit(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("user", this.service.findById(id));
        return "users/edit";
    }

    @GetMapping("/users/create")
    public String create(ModelMap model) {
        final UserDTO dto = new UserDTO();
        dto.setId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("user", dto);
        return "users/create";
    }

    @Transactional
    @PostMapping(value = { "/users/{id}/edit", "/users/create" })
    public String save(UserDTO dto) {
        return String.format("redirect:/users/%s", this.service.save(dto));
    }
    @PostMapping({ "/users/{id}/delete" })
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteUser(@PathVariable(value = "id") Integer id, SessionStatus status) {
        try {
            this.service.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", id)
                    .addObject("entityName", "user")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/users");
        }
        status.setComplete();
        return "redirect:/users";
    }


}
