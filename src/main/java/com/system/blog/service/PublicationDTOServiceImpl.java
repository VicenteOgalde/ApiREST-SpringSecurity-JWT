package com.system.blog.service;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.blog.dto.PublicationDTO;
import com.system.blog.dto.PublicationResponseDTO;
import com.system.blog.entities.Publication;
import com.system.blog.exceptions.ResourceNotFoundException;
import com.system.blog.repositories.IPublicationRepository;

@Service
public class PublicationDTOServiceImpl implements  IPublicationDTOService {

	@Autowired
	private IPublicationRepository publicationRepository;

	
	@Override
	public PublicationDTO createPublication(PublicationDTO publicationDTO) {
			
		return mapDTO(publicationRepository.save(mapPublicationEntity(publicationDTO)));
	}

	@Override
	public PublicationResponseDTO findAllPublications(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		//interface for pagination 
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Publication> listOfPublication = publicationRepository.findAll(pageable);
		
		List<Publication> publications =listOfPublication.getContent();
		
		List<PublicationDTO> publicationsDTO = publications.stream().map(publication -> mapDTO(publication)).collect(Collectors.toList());;
		
		return new PublicationResponseDTO(
				publicationsDTO, listOfPublication.getNumber(), listOfPublication.getSize()
				, listOfPublication.getTotalElements(), listOfPublication.getTotalPages()
				, listOfPublication.isLast());
	}
	//transform entity to DTO
	private PublicationDTO mapDTO(Publication publication) {
				
		PublicationDTO responsePublicationDTO  = new PublicationDTO();
		responsePublicationDTO.setId(publication.getId());
		responsePublicationDTO.setTitle(publication.getTitle());
		responsePublicationDTO.setDescription(publication.getDescription());
		responsePublicationDTO.setContent(publication.getContent());
		
		//responsePublicationDTO.setComments(publication.getComments());
		
		return responsePublicationDTO;
	}
	//transform DTO to Entity
	private Publication mapPublicationEntity(PublicationDTO publicationDTO) {
		
		
		Publication publication = new Publication();
		
		publication.setTitle(publicationDTO.getTitle());
		publication.setDescription(publicationDTO.getDescription());
		publication.setContent(publicationDTO.getContent());
		//publication.setComments(publicationDTO.getComments());
		
		return publication;
	}

	@Override
	public PublicationDTO findPublicationById(Long id) {
		
		return mapDTO(publicationRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Publication", "id", id)));
	}

	@Override
	public PublicationDTO updatePublication(PublicationDTO publicationDTO, Long id) {
		Publication publication = publicationRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Publication", "id", id));
		publication.setTitle(publicationDTO.getTitle());
		publication.setDescription(publicationDTO.getDescription());
		publication.setContent(publicationDTO.getContent());
		return mapDTO(publicationRepository.save(publication));
	}

	@Override
	public void deletePublication(Long id) {
		publicationRepository.delete(publicationRepository.findById(id)
				.orElseThrow(()->new ResourceNotFoundException("Publication", "id", id)));
		
	}
	
}
