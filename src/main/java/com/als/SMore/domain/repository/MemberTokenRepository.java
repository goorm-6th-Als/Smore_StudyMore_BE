package com.als.SMore.domain.repository;

import com.als.SMore.domain.entity.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {
    Optional<MemberToken> findMemberTokenByMember_MemberPk(Long memberPk);
}
