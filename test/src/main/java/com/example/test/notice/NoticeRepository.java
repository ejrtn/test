package com.example.test.notice;

import com.example.test.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long>, NoticeCustomRepository {
}
