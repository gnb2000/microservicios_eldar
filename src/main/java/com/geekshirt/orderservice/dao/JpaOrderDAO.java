package com.geekshirt.orderservice.dao;

import com.geekshirt.orderservice.entities.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaOrderDAO implements OrderDAO{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Order> findAll() {
        return em.createQuery("SELECT o FROM Order o").getResultList();
    }

    @Override
    public Optional<Order> findByOrderId(String orderId) {
        TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o WHERE o.orderId = :orderId",Order.class);
        query.setParameter("orderId",orderId);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(em.find(Order.class,id));
    }

    @Override
    public Order save(Order order) {
        em.persist(order);
        return order;
    }
}
