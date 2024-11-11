package com.book.book_network.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token,Integer> {

    Optional<Token> findByToken(String Token); //optional used to avoid null pointers it is a wrapper
}
