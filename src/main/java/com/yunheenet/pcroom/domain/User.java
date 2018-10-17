package com.yunheenet.pcroom.domain;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
public class User {

    private String id;
    private String password;
    private String name;
    private boolean status;
    private int time;

    @Builder
    public User(String id, String name, int time) {
        this.id = id;
        this.name = name;
        this.time = time;
    }
}
