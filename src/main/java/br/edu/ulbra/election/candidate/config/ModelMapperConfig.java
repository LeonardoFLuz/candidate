package br.edu.ulbra.election.candidate.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.edu.ulbra.election.candidate.model.Candidate;
import br.edu.ulbra.election.candidate.output.v1.CandidateOutput;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
    	ModelMapper mapper = new ModelMapper();
    	
    	mapper.getConfiguration().setAmbiguityIgnored(true);

    	//ajusta a diferença entre os nomes (number e numberElection).
    	mapper.addMappings(new PropertyMap<Candidate, CandidateOutput>() {
            protected void configure() {
                map().setNumberElection(source.getNumber());
            }
        });
    	
    	mapper.addMappings(new PropertyMap<CandidateOutput, Candidate>() {
            protected void configure() {
                map().setNumber(source.getNumberElection());
            }
        });
    	
        return mapper;
    }

}
