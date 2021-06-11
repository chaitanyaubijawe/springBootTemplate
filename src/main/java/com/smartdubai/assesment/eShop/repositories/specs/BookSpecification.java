package com.smartdubai.assesment.eShop.repositories.specs;

import com.smartdubai.assesment.eShop.entities.Book;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookSpecification implements Specification<Book> {

  private String name;
  private String author;
  private String type;
  private BigDecimal price;

  public BookSpecification(
      final String name, final String author, final String type, final BigDecimal price) {

    this.name = name;
    this.author = author;
    this.type = type;
    this.price = price;
  }

  /**
   * Creates a WHERE clause for a query of the referenced entity in form of a {@link Predicate} for
   * the given {@link Root} and {@link CriteriaQuery}.
   *
   * @param root must not be {@literal null}.
   * @param query must not be {@literal null}.
   * @param criteriaBuilder must not be {@literal null}.
   * @return a {@link Predicate}, may be {@literal null}.
   */
  @Override
  public Predicate toPredicate(
      final Root<Book> root, final CriteriaQuery<?> query, final CriteriaBuilder criteriaBuilder) {

    List<Predicate> predicates = new ArrayList<>();

    if (!StringUtils.isEmpty(this.name)) {

      predicates.add(criteriaBuilder.equal(root.get("name"), this.name));
    }

    if (!StringUtils.isEmpty(this.author)) {

      predicates.add(criteriaBuilder.equal(root.get("author"), this.author));
    }

    if (Objects.nonNull(this.price)) {

      predicates.add(criteriaBuilder.equal(root.get("price"), this.price));
    }
    if (!StringUtils.isEmpty(this.type)) {

      predicates.add(criteriaBuilder.equal(root.get("type"), this.type));
    }

    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
  }
}
