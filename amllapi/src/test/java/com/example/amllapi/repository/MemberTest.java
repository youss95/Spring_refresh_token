package com.example.amllapi.repository;

import com.example.amllapi.domain.Member;
import com.example.amllapi.domain.MemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testInsertMember() {

        for (int i=0; i<10; i++) {
            Member member = Member.builder()
                    .email("user"+i+"@test.com")
                    .pw(passwordEncoder.encode("1234"))
                    .nickname("nickname"+i)
                    .build();

            member.addRole(MemberRole.USER);

            if( i >= 5) {
                member.addRole(MemberRole.MANAGER);
            }

            if (i >= 8) {
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        }

    }

    @Test
    public void testRead() {
        String email = "user8@test.com";

        Member member = memberRepository.findWithRoles(email);

        log.info(member);
    }
}
