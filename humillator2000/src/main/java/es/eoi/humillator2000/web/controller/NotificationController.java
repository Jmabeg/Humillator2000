package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.NotificationService;
import es.eoi.humillator2000.web.dto.NotificationDTO;
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
public class NotificationController extends AbstractController<NotificationDTO> {

    private final NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/notifications")
    public String getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                         Model model) {
        final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Page<NotificationDTO> all = this.service.findAll( PageRequest.of(page.orElse(1) - 1, size.orElse(10)));
        model
                .addAttribute("username", user.getLogin())
                .addAttribute("notifications", all)
                .addAttribute(pageNumbersAttributeKey, getPageNumbers(all));
        return "notifications/list";
    }

    @GetMapping("/notifications/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[notification].userId == authentication.principal.id")
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("notification", this.service.findById(id));
        return "notifications/detail";
    }

    @GetMapping("/notifications/{id}/edit")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[notification].userId == authentication.principal.id")
    public String edit(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("notification", this.service.findById(id));
        return "notifications/edit";
    }

    @GetMapping("/notifications/create")
    public String create(ModelMap model) {
        final NotificationDTO dto = new NotificationDTO();
        dto.setId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("notification", dto);
        return "notifications/create";
    }

    @Transactional
    @PostMapping(value = { "/notifications/{id}/edit", "/notifications/create" })
    public String save(NotificationDTO dto) {
        return String.format("redirect:/notifications/%s", this.service.save(dto));
    }
    @PostMapping({ "/notifications/{id}/delete" })
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteUser(@PathVariable(value = "id") Integer id, SessionStatus status) {
        try {
            this.service.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", id)
                    .addObject("entityName", "notification")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/notifications");
        }
        status.setComplete();
        return "redirect:/notifications";
    }


}
