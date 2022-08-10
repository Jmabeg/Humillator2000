package es.eoi.humillator2000.web.controller;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractController<DTO> {
    protected final String pageNumbersAttributeKey = "pageNumbers";


    protected List<Integer> getPageNumbers(Page<DTO> pages) {
        return pages.getTotalPages() > 0 ?
                IntStream.rangeClosed(1, pages.getTotalPages()).boxed().collect(Collectors.toList()) :
                new ArrayList<>();
    }
}
