package com.example.amllapi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class MemberDto extends User {

    private String email;

    private String password;

    private String nickname;

    private boolean social;

    private List<String> roleNames = new ArrayList<>();

    public MemberDto(String email, String password, String nickname, boolean social, List<String> roleNames) {
        super(email,password,roleNames.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList()));

        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.social = social;
        this.roleNames = roleNames;
    }

    public Map<String, Object> getClaims() {
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("email",email);
        dataMap.put("pw",password);
        dataMap.put("nickname", nickname);
        dataMap.put("social", social);
        dataMap.put("roleNames", roleNames);

        return dataMap;
    }
}
