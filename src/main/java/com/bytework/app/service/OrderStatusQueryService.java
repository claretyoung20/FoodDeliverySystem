package com.bytework.app.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bytework.app.domain.OrderStatus;
import com.bytework.app.domain.*; // for static metamodels
import com.bytework.app.repository.OrderStatusRepository;
import com.bytework.app.service.dto.OrderStatusCriteria;
import com.bytework.app.service.dto.OrderStatusDTO;
import com.bytework.app.service.mapper.OrderStatusMapper;

/**
 * Service for executing complex queries for {@link OrderStatus} entities in the database.
 * The main input is a {@link OrderStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrderStatusDTO} or a {@link Page} of {@link OrderStatusDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrderStatusQueryService extends QueryService<OrderStatus> {

    private final Logger log = LoggerFactory.getLogger(OrderStatusQueryService.class);

    private final OrderStatusRepository orderStatusRepository;

    private final OrderStatusMapper orderStatusMapper;

    public OrderStatusQueryService(OrderStatusRepository orderStatusRepository, OrderStatusMapper orderStatusMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusMapper = orderStatusMapper;
    }

    /**
     * Return a {@link List} of {@link OrderStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrderStatusDTO> findByCriteria(OrderStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrderStatus> specification = createSpecification(criteria);
        return orderStatusMapper.toDto(orderStatusRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrderStatusDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderStatusDTO> findByCriteria(OrderStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrderStatus> specification = createSpecification(criteria);
        return orderStatusRepository.findAll(specification, page)
            .map(orderStatusMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrderStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrderStatus> specification = createSpecification(criteria);
        return orderStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link OrderStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrderStatus> createSpecification(OrderStatusCriteria criteria) {
        Specification<OrderStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrderStatus_.id));
            }
            if (criteria.getDateCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateCreated(), OrderStatus_.dateCreated));
            }
            if (criteria.getDateUpdated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateUpdated(), OrderStatus_.dateUpdated));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), OrderStatus_.status));
            }
        }
        return specification;
    }
}
