package br.edu.ulbra.election.candidate.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.ulbra.election.candidate.output.v1.VoteOutput;


@Service
public class VoteClientService {
	private final VoteClient voteClient;

    @Autowired
    public VoteClientService(VoteClient voteClient) {
        this.voteClient = voteClient;
    }

    public List<VoteOutput> getByElectionId(Long electionId) {
        return this.voteClient.getByElectionId(electionId);
    }

    @FeignClient(value="vote-service", url="${url.vote-service}")
    private interface VoteClient {

    	@GetMapping("/v1/vote/election/{electionId}")
    	List<VoteOutput> getByElectionId(@PathVariable(name = "electionId") Long electionId);
    }
}
