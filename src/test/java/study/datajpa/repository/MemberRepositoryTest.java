package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @Test
    public void basicCRUD() {
        Member member1 = new Member("Member1");
        Member member2 = new Member("Member2");

        Member savedMember1 = memberRepository.save(member1);
        Member savedMember2 = memberRepository.save(member2);

        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(savedMember1);
        assertThat(findMember2).isEqualTo(savedMember2);

        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        memberRepository.delete(member1);
        memberRepository.delete(member2);

        long deletedCount = memberRepository.count();
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() {
        Member member1 = new Member("AAA",10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> list = memberRepository.findByUsernameAndAgeGreaterThan("BBB", 15);
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void findUserTest() {
        Member member1 = new Member("AAA",10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> list = memberRepository.findUser("AAA", 10);
        assertThat(list.size()).isEqualTo(1);
    }

    @Test
    public void findUserNameTest() {
        Member member1 = new Member("AAA",10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<String> usernameList = memberRepository.findUsernameList();
        assertThat(usernameList.size()).isEqualTo(2);
    }

    @Test
    public void findDto() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        Member member1 = new Member("AAA",10,teamA);
        Member member2 = new Member("BBB", 20,teamB);
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto.id() = " + dto.id());
            System.out.println("dto.id() = " + dto.username());
            System.out.println("dto.id() = " + dto.teamName());
        }
    }

    @Test
    public void findByNamesTest() {
        Member member1 = new Member("AAA",10);
        Member member2 = new Member("BBB", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> byNames = memberRepository.findByNames(List.of("AAA", "BBB"));
        for (Member byName : byNames) {
            System.out.println("byName = " + byName);
        }

        assertThat(byNames.size()).isEqualTo(2);
    }
}