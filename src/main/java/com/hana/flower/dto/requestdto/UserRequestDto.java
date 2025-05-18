package com.hana.flower.dto.requestdto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto 
{
    private String name;
    private String email;
    private String phoneNumber;
}
