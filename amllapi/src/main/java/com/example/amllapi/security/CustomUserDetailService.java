package com.example.amllapi.security;

import com.example.amllapi.domain.Member;
import com.example.amllapi.dto.MemberDto;
import com.example.amllapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * get user info
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername ----");

        Member member = memberRepository.findWithRoles(username);

        if (Objects.isNull(member)) {
            throw new UsernameNotFoundException("Not found");
        }

        MemberDto memberDto = new MemberDto(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList()
                        .stream().map(x -> x.name()).collect(Collectors.toList())
        );

        log.info("memberDto: " + memberDto);
        return memberDto;
    }
}
