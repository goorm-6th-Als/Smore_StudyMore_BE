package com.als.SMore.entity;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "advertising_table")
public class AdvertisingTable {
    @Id @Tsid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advertising_table_pk", nullable = false)
    private Long advertisingTablePk;

    @ManyToOne
    @JoinColumn(name = "study_pk", nullable = false)
    private Study study;

    @Column(name = "ad_title", nullable = false)
    private String adTitle;

    @Column(name = "ad_content")
    private String ad_content;

    @Column(name = "ad_summary")
    private String adSummary;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "modify_date")
    private Date modifyDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "close_date")
    private Date closeDate;

    @ManyToOne
    @JoinColumn(name = "member_pk", nullable = false)
    private Member member;

    // Getters and Setters
}

