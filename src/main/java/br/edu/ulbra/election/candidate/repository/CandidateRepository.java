package br.edu.ulbra.election.candidate.repository;

import br.edu.ulbra.election.candidate.model.Candidate;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    Candidate findFirstByNumberElectionAndAndElectionId(Long numberElection, Long electionId);
    List<Candidate> findByPartyId(Long partyId);
    List<Candidate> findByElectionId(Long electionId);
}
