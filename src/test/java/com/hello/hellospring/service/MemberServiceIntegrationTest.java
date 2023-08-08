package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemberRepository;
import com.hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository repository;


    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("hello");
        // when
        Long savedId = memberService.join(member);

        // then
        Member foundMember =  memberService.findOne(savedId).get();
        Assertions.assertThat(member.getName()).isEqualTo(foundMember.getName());
    }

    @Test
    public void duplicateMembersException() {
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);

        IllegalArgumentException e  = assertThrows(IllegalArgumentException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("User already exists");
    }


}
