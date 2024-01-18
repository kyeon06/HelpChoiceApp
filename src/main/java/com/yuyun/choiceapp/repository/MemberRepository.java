package com.yuyun.choiceapp.repository;

import com.yuyun.choiceapp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByUsername(String username);

    boolean existByEmail(String email);
    boolean existByUsername(String username);
    boolean existByNickname(String nickname);
}
