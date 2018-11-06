package br.edu.ulbra.election.candidate.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.ulbra.election.candidate.model.Candidate;
import br.edu.ulbra.election.candidate.output.v1.CandidateOutput;
import br.edu.ulbra.election.candidate.output.v1.ElectionOutput;
import br.edu.ulbra.election.candidate.output.v1.PartyOutput;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
    	ModelMapper mapper = new ModelMapper();
    	
    	mapper.getConfiguration().setAmbiguityIgnored(true);

    	//ajustes
    	mapper.addMappings(new PropertyMap<Candidate, CandidateOutput>() {
    		@Override
            protected void configure() {
                map().setNumberElection(source.getNumber());
                
            	map().setElectionOutput(new ElectionOutput());
            	map().getElectionOutput().setId(source.getElectionId());
            	
            	map().setPartyOutput(new PartyOutput());
            	map().getPartyOutput().setId(source.getPartyId());
            }
        });
    	
    	mapper.addMappings(new PropertyMap<CandidateOutput, Candidate>() {
    		@Override
            protected void configure() {
                map().setNumber(source.getNumberElection());
                
                map().setElectionId(source.getElectionOutput().getId());
                map().setPartyId(source.getPartyOutput().getId());
            }
        });
    	
        return mapper;
    }

}
