package org.babyshark.chagok.domain.member.repository;

import java.util.Optional;
import org.babyshark.chagok.domain.member.domain.Member;
import org.babyshark.chagok.global.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  boolean existsByEmail(String email);

  Optional<Member> findByRefreshToken(String refreshToken);

  Optional<Member> findByProviderAndProviderId(Provider provider, String providerId);
}
