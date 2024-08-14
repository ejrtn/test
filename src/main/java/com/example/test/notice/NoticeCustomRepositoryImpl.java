package com.example.test.notice;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public NoticeCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Notice> noticeList(int currentPageNo, int recordCountPerPage, String searchText) {
        QNotice qNotice = QNotice.notice;
        return jpaQueryFactory.selectFrom(qNotice)
                .where(qNotice.title.like("%"+searchText+"%"))
                .orderBy(qNotice.noticeId.asc())
                .offset((long) (currentPageNo - 1) * recordCountPerPage)
                .limit(recordCountPerPage)
                .fetch();
    }

    @Override
    public List<Notice> noticeListAll(String searchText) {
        QNotice qNotice = QNotice.notice;
        return jpaQueryFactory.selectFrom(qNotice)
                .where(qNotice.title.like("%"+searchText+"%"))
                .orderBy(qNotice.noticeId.asc())
                .fetch();
    }

    @Override
    public Long noticeTotal(String searchText) {
        QNotice qNotice = QNotice.notice;
        return jpaQueryFactory.select(qNotice.count())
                .from(qNotice)
                .where(qNotice.title.like("%" + searchText + "%"))
                .fetchOne();
    }
}
