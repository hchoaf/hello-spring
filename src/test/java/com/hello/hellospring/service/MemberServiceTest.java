package com.hello.hellospring.service;

import com.hello.hellospring.domain.Member;
import com.hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository repository;

    @BeforeEach
    void beforeEach() {
        repository = new MemoryMemberRepository();
        memberService = new MemberService(repository);
    }
    @AfterEach
    void afterEach() {
        repository.clearStorage();
    }

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

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}