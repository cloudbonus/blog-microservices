package com.github.blog.controller;

import com.github.blog.controller.dto.common.OrderDto;
import com.github.blog.controller.dto.request.OrderRequest;
import com.github.blog.controller.dto.request.PageableRequest;
import com.github.blog.controller.dto.request.PaymentRequest;
import com.github.blog.controller.dto.request.filter.OrderFilterRequest;
import com.github.blog.controller.dto.response.PageResponse;
import com.github.blog.service.OrderService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Raman Haurylau
 */
@Validated
@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("cancel/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('COMPANY') and @orderAccess.verifyOwnership(#id))")
    public PaymentRequest cancel(@PathVariable("id") @P("id") @Positive(message = "ID must be greater than 0") Long id) {
        return orderService.cancel(id);
    }

    @GetMapping("process/{id}")
    @SneakyThrows
    @PreAuthorize("hasAnyRole('ADMIN', 'COMPANY') and @orderAccess.verifyOwnership(#id)")
    public PaymentRequest process(@PathVariable("id") @P("id") @Positive(message = "ID must be greater than 0") Long id) {
        return orderService.process(id);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('COMPANY') and @orderAccess.verifyOwnership(#id))")
    public OrderDto findById(@PathVariable("id") @P("id") @Positive(message = "ID must be greater than 0") Long id) {
        return orderService.findById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or (hasRole('COMPANY') and @orderAccess.canFilter(#filter.userId))")
    public PageResponse<OrderDto> findAll(@P("filter") @Validated OrderFilterRequest filter, @Validated PageableRequest pageableRequest) {
        return orderService.findAll(filter, pageableRequest);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto update(@PathVariable("id") @Positive(message = "ID must be greater than 0") Long id, @RequestBody @Validated OrderRequest orderDto) {
        return orderService.update(id, orderDto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto delete(@PathVariable("id") @Positive(message = "ID must be greater than 0") Long id) {
        return orderService.findById(id);
    }
}

