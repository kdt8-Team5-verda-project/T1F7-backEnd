package org.example.entity;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "FundPic")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundPicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private String fileUrl;

    @OneToOne
    @JoinColumn(name = "fmId")
    private FundEntity fundEntity;
}