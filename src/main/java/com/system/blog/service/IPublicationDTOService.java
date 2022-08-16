package com.system.blog.service;



import com.system.blog.dto.PublicationDTO;
import com.system.blog.dto.PublicationResponseDTO;

public interface IPublicationDTOService {

	public PublicationDTO createPublication(PublicationDTO publicationDTO);
	public PublicationResponseDTO findAllPublications(int pageNumber, 
			int pageSize, String sortBy, String sortDir);
	public PublicationDTO findPublicationById(Long id);
	public PublicationDTO updatePublication(PublicationDTO publicationDTO,Long id);
	public void deletePublication(Long id);
}
