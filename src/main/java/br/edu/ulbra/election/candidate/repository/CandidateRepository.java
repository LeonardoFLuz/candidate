package br.edu.ulbra.election.candidate.repository;

import br.edu.ulbra.election.candidate.model.Candidate;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Long> {
<<<<<<< HEAD
	Candidate findByName(String name);
	Candidate findByNumber(String number);
=======
>>>>>>> 1ce85f053387a9175b39bb0ca775d8b616c1f1f5
}
