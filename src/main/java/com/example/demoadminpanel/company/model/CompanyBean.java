package com.example.demoadminpanel.company.model;

import com.example.demoadminpanel.user.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBean {
    private Long id;
    private String name;

    public static CompanyBean transformFromUser(User user) {
        return CompanyBean.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
