package com.ZipSlack.repository;

import com.ZipSlack.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository <Message, Long> {

}
