package com.system.blog.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.blog.dto.PublicationDTO;
import com.system.blog.dto.PublicationResponseDTO;
import com.system.blog.service.IPublicationDTOService;
import com.system.blog.utils.AppConstanst;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

	@Autowired
	private IPublicationDTOService publicationDTOService;

	@GetMapping
	public ResponseEntity<PublicationResponseDTO> findAll(
			@RequestParam(value = "pageNumber", defaultValue = AppConstanst.PAGE_NUMBER_BY_DEFAULT, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstanst.PAGE_SIZE_BY_DEFAULT, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstanst.SORT_BY_DEFAULT, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstanst.SORT_DIRECTION_BY_DEFAULT, required = false) String sortDir) {
		return new ResponseEntity<PublicationResponseDTO>(
				publicationDTOService.findAllPublications(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PublicationDTO> findPublicationById(@PathVariable("id") Long id) {
		return new ResponseEntity<PublicationDTO>(publicationDTOService.findPublicationById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PublicationDTO> savePublication(@Valid @RequestBody PublicationDTO publicationDTO) {
		return new ResponseEntity<>(publicationDTOService.createPublication(publicationDTO), HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PublicationDTO> updatePublication(@Valid @RequestBody PublicationDTO publicationDTO,
			@PathVariable("id") Long id) {

		return ResponseEntity.ok(publicationDTOService.updatePublication(publicationDTO, id));

	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePublication(@PathVariable("id") Long id) {
		publicationDTOService.deletePublication(id);
		return new ResponseEntity<>("successfully erased", HttpStatus.OK);
	}

}
