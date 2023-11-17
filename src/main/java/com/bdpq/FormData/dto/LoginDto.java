package com.bdpq.FormData.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String name;
    private String password;
}
