package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.ClubRoleService;
import es.eoi.humillator2000.web.dto.ClubRoleDTO;
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
public class ClubRoleController extends AbstractController<ClubRoleDTO> {

    private final ClubRoleService service;

    @Autowired
    public ClubRoleController(ClubRoleService service) {
        this.service = service;
    }

    @GetMapping("/clubroles")
    public String getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                         Model model) {
        final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Page<ClubRoleDTO> all = this.service.findAll( PageRequest.of(page.orElse(1) - 1, size.orElse(10)));
        model
                .addAttribute("username", user.getLogin())
                .addAttribute("clubroles", all)
                .addAttribute(pageNumbersAttributeKey, getPageNumbers(all));
        return "clubroles/list";
    }

    @GetMapping("/clubroles/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[clubrole].userId == authentication.principal.id")
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("clubrole", this.service.findById(id));
        return "clubroles/detail";
    }

    @GetMapping("/clubroles/{id}/edit")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[clubrole].userId == authentication.principal.id")
    public String edit(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("clubrole", this.service.findById(id));
        return "clubroles/edit";
    }

    @GetMapping("/clubroles/create")
    public String create(ModelMap model) {
        final ClubRoleDTO dto = new ClubRoleDTO();
        dto.setId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("clubrole", dto);
        return "clubroles/create";
    }

    @Transactional
    @PostMapping(value = { "/clubroles/{id}/edit", "/clubroles/create" })
    public String save(ClubRoleDTO dto) {
        return String.format("redirect:/clubroles/%s", this.service.save(dto));
    }
    @PostMapping({ "/clubroles/{id}/delete" })
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteUser(@PathVariable(value = "id") Integer id, SessionStatus status) {
        try {
            this.service.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", id)
                    .addObject("entityName", "clubrole")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/clubroles");
        }
        status.setComplete();
        return "redirect:/clubroles";
    }


}
