package spring.spring_basic.member;

public interface MemberService {
    void join(Member member);
    Member findByMember(Long memberId);
}
