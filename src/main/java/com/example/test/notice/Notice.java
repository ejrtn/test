package com.example.test.notice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    private String title;

    private String contentData;


    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContentData() {
        return contentData;
    }
}
