//package com.verda.BE.review.entity;
//
//import com.verda.BE.board.entity.UserPostEntity;
//import com.verda.BE.login.member.domain.UserEntity;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.PrePersist;
//import java.time.LocalDateTime;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.cglib.core.Local;
//
//@Entity
//@NoArgsConstructor
//@Getter
//public class ReviewEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="reviewId")
//    private long id;
//
//    private String title;
//
//    private String content;
//
//    private String targetFmName;
//
//    private LocalDateTime createdAt;
//
//    @OneToOne
//    @JoinColumn(name = "postId", referencedColumnName = "postId")
//    private UserPostEntity userPostEntity;
//
//    @PrePersist
//    void prePersist() {
//        this.createdAt = LocalDateTime.now();
//    }
//
//}
