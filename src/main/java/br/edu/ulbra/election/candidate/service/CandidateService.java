package br.edu.ulbra.election.candidate.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ulbra.election.candidate.repository.CandidateRepository;
import br.edu.ulbra.election.candidate.output.v1.GenericOutput;
import br.edu.ulbra.election.candidate.output.v1.PartyOutput;
import br.edu.ulbra.election.candidate.exception.GenericOutputException;
import br.edu.ulbra.election.candidate.input.v1.CandidateInput;
import br.edu.ulbra.election.candidate.model.Candidate;
import br.edu.ulbra.election.candidate.output.v1.CandidateOutput;
import br.edu.ulbra.election.candidate.output.v1.ElectionOutput;

@Service
public class CandidateService {


	private final CandidateRepository candidateRepository;

	private final ModelMapper modelMapper;

	private static final String MESSAGE_INVALID_ID = "Invalid id";
	private static final String MESSAGE_CANDIDATE_NOT_FOUND = "Candidate not found";

	@Autowired
	public CandidateService(CandidateRepository candidateRepository, ModelMapper modelMapper){
		this.candidateRepository = candidateRepository;
		this.modelMapper = modelMapper;
	}

	public List<CandidateOutput> getAll(){
		Iterable<Candidate> candidates = candidateRepository.findAll();
		List<CandidateOutput> listOutput = new ArrayList<CandidateOutput>(); 
		candidates.forEach(candidate -> listOutput.add(getOutput(candidate)));
		return listOutput;
	}

	public CandidateOutput create(CandidateInput candidateInput) {
		validateInput(candidateInput, false);
		Candidate candidate = modelMapper.map(candidateInput, Candidate.class);
		candidate = candidateRepository.save(candidate);
		return getOutput(candidate);
	}

	public CandidateOutput getById(Long candidateId){
		if (candidateId == null){
			throw new GenericOutputException(MESSAGE_INVALID_ID);
		}

		Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
		if (candidate == null){
			throw new GenericOutputException(MESSAGE_CANDIDATE_NOT_FOUND);
		}

		return getOutput(candidate);
	}

	public CandidateOutput update(Long candidateId, CandidateInput candidateInput) {
		if (candidateId == null){
			throw new GenericOutputException(MESSAGE_INVALID_ID);
		}
		validateInput(candidateInput, true);

		Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
		if (candidate == null){
			throw new GenericOutputException(MESSAGE_CANDIDATE_NOT_FOUND);
		}

		candidate.setElectionId(candidateInput.getElectionId());
		candidate.setPartyId(candidateInput.getPartyId());
		candidate.setName(candidateInput.getName());
		candidate.setNumber(candidateInput.getNumberElection());

		candidate = candidateRepository.save(candidate);

		return getOutput(candidate);
	}

	public GenericOutput delete(Long candidateId) {
		if (candidateId == null){
			throw new GenericOutputException(MESSAGE_INVALID_ID);
		}

		Candidate candidate = candidateRepository.findById(candidateId).orElse(null);
		if (candidate == null){
			throw new GenericOutputException(MESSAGE_CANDIDATE_NOT_FOUND);
		}

		candidateRepository.delete(candidate);

		return new GenericOutput("Candidate deleted");
	}

	private CandidateOutput getOutput(Candidate candidate) {
		CandidateOutput candidateOutput = modelMapper.map(candidate, CandidateOutput.class);

		ElectionOutput electionOutput = new ElectionOutput();
		electionOutput.setId(candidate.getElectionId());
		candidateOutput.setElectionOutput(electionOutput);

		PartyOutput partyOutput = new PartyOutput();
		partyOutput.setId(candidate.getPartyId());
		candidateOutput.setPartyOutput(partyOutput);

		return candidateOutput;
	}

	public Candidate findByName(String name){
		return candidateRepository.findByName(name);
	}
	public Candidate findByNumber(String number){
		return candidateRepository.findByNumber(number);
	}
	private void validateInput(CandidateInput candidateInput, boolean isUpdate){
		if (StringUtils.isBlank(candidateInput.getName())){
			throw new GenericOutputException("Invalid name");
		}
		
		if (candidateInput.getNumberElection() == null || candidateInput.getNumberElection() == 0){
			throw new GenericOutputException("Invalid Number");
		}

		if(candidateInput.getName().length() < 5) {
			throw new GenericOutputException("Name must be at least 5 characters");
		}
		
		String[] names = candidateInput.getName().trim().split(" ");
		if(names.length == 1) {
			throw new GenericOutputException("Last name is required");
		}
	}
	   
}
