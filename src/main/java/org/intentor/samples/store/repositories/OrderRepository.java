package org.intentor.samples.store.repositories;

import org.intentor.samples.store.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository for processing {@link Order} data.
 */
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
}
