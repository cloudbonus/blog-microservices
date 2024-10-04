package com.github.blog.repository;

import com.github.blog.repository.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Raman Haurylau
 */
public interface OrderRepository extends CrudRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Override
    @NonNull
    Page<Order> findAll(@Nullable Specification<Order> spec, @NonNull Pageable pageable);

    Optional<Order> findByPostId(Long postId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Order o WHERE o.state = 'CANCELED'")
    void deleteCanceledOrders();

}
