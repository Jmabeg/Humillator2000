package es.eoi.humillator2000.web.controller;

import es.eoi.humillator2000.data.entity.User;
import es.eoi.humillator2000.service.ClubService;
import es.eoi.humillator2000.web.dto.ClubDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class ClubController extends AbstractController<ClubDTO> {

    private final ClubService service;

    @Autowired
    public ClubController(ClubService service) {
        this.service = service;
    }

    @GetMapping("/clubs")
    public String getAll(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {
        final User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final Page<ClubDTO> all = this.service.findAll( PageRequest.of(page.orElse(1) - 1, size.orElse(10)));
        model
                .addAttribute("username", user.getLogin())
                .addAttribute("clubs", all)
                .addAttribute(pageNumbersAttributeKey, getPageNumbers(all));
        return "clubs/list";
    }

    @GetMapping("/clubs/{id}")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[club].userId == authentication.principal.id")
    public String detail(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("club", this.service.findById(id));
        return "clubs/detail";
    }

    @GetMapping("/clubs/{id}/edit")
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[club].userId == authentication.principal.id")
    public String edit(@PathVariable("id") Integer id, ModelMap model) {
        model.addAttribute("club", this.service.findById(id));
        return "clubs/edit";
    }

    @GetMapping("/clubs/create")
    public String create(ModelMap model) {
        final ClubDTO dto = new ClubDTO();
        dto.setId(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        model.addAttribute("club", dto);
        return "clubs/create";
    }

    @Transactional
    @PostMapping(value = { "/clubs/{id}/edit", "/clubs/create" })
    public String save(ClubDTO dto) {
        return String.format("redirect:/clubs/%s", this.service.save(dto));
    }
    @PostMapping({ "/clubs/{id}/delete" })
    @PostAuthorize("hasRole('ROLE_ADMIN')")
    public Object deleteUser(@PathVariable(value = "id") Integer id, SessionStatus status) {
        try {
            this.service.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            status.setComplete();
            return new ModelAndView("error/errorHapus")
                    .addObject("entityId", id)
                    .addObject("entityName", "club")
                    .addObject("errorCause", exception.getRootCause().getMessage())
                    .addObject("backLink", "/clubs");
        }
        status.setComplete();
        return "redirect:/clubs";
    }
    @PostMapping({"/clubs/{id}/add"})
    @PostAuthorize("hasRole('ROLE_ADMIN') or #model[club].userId == authentication.principal.id")
    public String addUser(@PathVariable(value="id")Integer id, Integer userId, Integer roleId){
        this.service.addUser(id, userId, roleId);
        return "clubs/{id}/users";
    }

}
