package com.User.Management.domain.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table
@Entity
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @Column(name ="BOARD_TITLE")
    private String title;

    @Column(name ="BOARD_DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Builder
    public Board(User user, String title, String description){
        this.user =user;
        this.title = title;
        this.description = description;
    }

    public void update(String title, String description){
        this.title = title;
        this.description = description;
    }
}
