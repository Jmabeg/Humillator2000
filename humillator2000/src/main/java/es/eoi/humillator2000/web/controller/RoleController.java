package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.RoleService;
import es.eoi.humillator2000.web.dto.RoleDTO;
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
public class RoleController extends AbstractController<RoleDTO> {

    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("/roles")
    public String getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {
        final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Page<RoleDTO> all = this.service.findAll( PageRequest.of(page.orElse(1) - 1, size.orElse(10)));
        model
                .addAttribute("username", user.getLogin())
                .addAttribute("roles", all)
                .addAttribute(pageNumbersAttributeKey, getPageNumbers(all));
        return "roles/list";
    }

    @GetMapping("/roles/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[role].userId == authentication.principal.id")
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("role", this.service.findById(id));
        return "roles/detail";
    }

    @GetMapping("/roles/{id}/edit")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[role].userId == authentication.principal.id")
    public String edit(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("role", this.service.findById(id));
        return "roles/edit";
    }

    @GetMapping("/roles/create")
    public String create(ModelMap model) {
        final RoleDTO dto = new RoleDTO();
        dto.setId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("role", dto);
        return "roles/create";
    }

    @Transactional
    @PostMapping(value = { "/roles/{id}/edit", "/roles/create" })
    public String save(RoleDTO dto) {
        return String.format("redirect:/roles/%s", this.service.save(dto));
    }
    @PostMapping({ "/roles/{id}/delete" })
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteUser(@PathVariable(value = "id") Integer id, SessionStatus status) {
        try {
            this.service.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", id)
                    .addObject("entityName", "role")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/roles");
        }
        status.setComplete();
        return "redirect:/roles";
    }


}