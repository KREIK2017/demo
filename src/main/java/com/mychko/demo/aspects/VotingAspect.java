package com.mychko.demo.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.mychko.demo.models.Vote;
import java.time.LocalDateTime;

@Aspect
@Component
public class VotingAspect {

    @Before("execution(* com.mychko.demo.repository.VoteRepository.save(..)) && args(voting)")
    public void setDefaultVotingPeriod(Vote voting) {
        if (voting.getEndDate() == null) {
            voting.setEndDate(LocalDateTime.now().plusDays(7));
        }
    }
}
