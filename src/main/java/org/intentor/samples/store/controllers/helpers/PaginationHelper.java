package org.intentor.samples.store.controllers.helpers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

/**
 * Provides methods to help pagination on controllers.
 */
public final class PaginationHelper {
    /**
     * Utility class. Should not be instantiated
     */
    private PaginationHelper() {
    }

    /**
     * Performs a pagination.
     *
     * @param pageable  Pagination details.
     * @param sort      Field used for sorting.
     * @param order     Field order (asc, desc).
     * @param pageUri   Pagination main URI. Usually the findAll action URI.
     * @param paginator Pagination method.
     * @return Pagination response.
     */
    public static ResponseEntity<?> paginate(Pageable pageable, String sort, String order, String pageUri,
                                             Function<Pageable, Page<?>> paginator) {
        var pagination = PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by("asc".equals(order) ? Sort.Direction.ASC : Sort.Direction.DESC, sort)
        );

        var data = paginator.apply(pagination);

        if (data.getContent().isEmpty()) {
            return noContent().build();
        } else {
            var total = data.getTotalElements();
            var current = data.getNumberOfElements();

            var headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(total));

            if (current < total) {
                headers.add("first", buildPageUri(pageUri, PageRequest.of(0, data.getSize())));
                headers.add("last", buildPageUri(pageUri, PageRequest.of(data.getTotalPages() - 1, data.getSize())));

                if (data.hasNext()) {
                    headers.add("next", buildPageUri(pageUri, data.nextPageable()));
                }

                if (data.hasPrevious()) {
                    headers.add("prev", buildPageUri(pageUri, data.previousPageable()));
                }

                return new ResponseEntity<>(data.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(data.getContent(), headers, HttpStatus.OK);
            }
        }
    }

    /**
     * Builds a page URI.
     *
     * @param pageUri Pagination main URI. Usually the findAll action URI.
     * @param page    Page details.
     * @return Created URI.
     */
    private static String buildPageUri(String pageUri, Pageable page) {
        return fromUriString(pageUri)
                .query("page={page}&size={size}")
                .buildAndExpand(page.getPageNumber(), page.getPageSize())
                .toUriString();
    }
}
