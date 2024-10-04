package com.github.epay.repository;

import com.github.epay.repository.entity.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


/**
 * @author Raman Haurylau
 */
public interface PaymentRepository extends CrudRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(Long id);
}
