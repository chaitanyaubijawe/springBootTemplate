package com.smartdubai.assesment.eShop.repositories;

import com.smartdubai.assesment.eShop.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface BooksRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
}
