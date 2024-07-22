package com.example.test.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;


@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public UserCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public String loginCheck(String userId, String pwd) {
        QUser qUser = QUser.user;
        return jpaQueryFactory.select(qUser.userId)
                .from(qUser)
                .where(qUser.userId.eq(userId),qUser.pwd.eq(pwd))
                .fetchOne();
    }
}
