package es.eoi.humillator2000.web.controller;

public abstract class AbstractController {
    protected final String pageNumbersAttributeKey = "pageNumbers";

    protected List<Integer> getPageNumbers(Page<DTO> pages) {
        return pages.getTotalPages() > 0 ?
                IntStream.rangeClosed(1, pages.getTotalPages()).boxed().collect(Collectors.toList()) :
                new ArrayList<>();
    }

}
