package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.SeasonService;
import es.eoi.humillator2000.web.dto.SeasonDTO;
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
public class SeasonController extends AbstractController<SeasonDTO> {

    private final SeasonService service;

    @Autowired
    public SeasonController(SeasonService service) {
        this.service = service;
    }

    @GetMapping("/seasons")
    public String getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                         Model model) {
        final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Page<SeasonDTO> all = this.service.findAll( PageRequest.of(page.orElse(1) - 1, size.orElse(10)));
        model
                .addAttribute("username", user.getLogin())
                .addAttribute("seasons", all)
                .addAttribute(pageNumbersAttributeKey, getPageNumbers(all));
        return "seasons/list";
    }

    @GetMapping("/seasons/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[season].userId == authentication.principal.id")
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("season", this.service.findById(id));
        return "seasons/detail";
    }

    @GetMapping("/seasons/{id}/edit")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[season].userId == authentication.principal.id")
    public String edit(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("season", this.service.findById(id));
        return "seasons/edit";
    }

    @GetMapping("/seasons/create")
    public String create(ModelMap model) {
        final SeasonDTO dto = new SeasonDTO();
        dto.setId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("season", dto);
        return "seasons/create";
    }

    @Transactional
    @PostMapping(value = { "/seasons/{id}/edit", "/seasons/create" })
    public String save(SeasonDTO dto) {
        return String.format("redirect:/seasons/%s", this.service.save(dto));
    }
    @PostMapping({ "/seasons/{id}/delete" })
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteUser(@PathVariable(value = "id") Integer id, SessionStatus status) {
        try {
            this.service.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", id)
                    .addObject("entityName", "season")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/seasons");
        }
        status.setComplete();
        return "redirect:/seasons";
    }


}