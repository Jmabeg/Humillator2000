package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.MatchService;
import es.eoi.humillator2000.web.dto.MatchDTO;
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
public class MatchController extends AbstractController<MatchDTO> {

    private final MatchService service;

    @Autowired
    public MatchController(MatchService service) {
        this.service = service;
    }

    @GetMapping("/matches")
    public String getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                         Model model) {
        final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Page<MatchDTO> all = this.service.findAll( PageRequest.of(page.orElse(1) - 1, size.orElse(10)));
        model
                .addAttribute("username", user.getLogin())
                .addAttribute("matches", all)
                .addAttribute(pageNumbersAttributeKey, getPageNumbers(all));
        return "matches/list";
    }

    @GetMapping("/matches/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[match].userId == authentication.principal.id")
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("match", this.service.findById(id));
        return "matches/detail";
    }

    @GetMapping("/matches/{id}/edit")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[match].userId == authentication.principal.id")
    public String edit(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("match", this.service.findById(id));
        return "matches/edit";
    }

    @GetMapping("/matches/create")
    public String create(ModelMap model) {
        final MatchDTO dto = new MatchDTO();
        dto.setId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("match", dto);
        return "matches/create";
    }

    @Transactional
    @PostMapping(value = { "/matches/{id}/edit", "/matches/create" })
    public String save(MatchDTO dto) {
        return String.format("redirect:/matches/%s", this.service.save(dto));
    }
    @PostMapping({ "/matches/{id}/delete" })
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteUser(@PathVariable(value = "id") Integer id, SessionStatus status) {
        try {
            this.service.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", id)
                    .addObject("entityName", "match")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/matches");
        }
        status.setComplete();
        return "redirect:/matches";
    }


}