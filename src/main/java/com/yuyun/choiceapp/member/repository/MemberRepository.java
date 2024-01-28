package com.yuyun.choiceapp.member.repository;

import com.yuyun.choiceapp.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}
