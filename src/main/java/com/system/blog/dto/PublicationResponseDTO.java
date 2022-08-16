package com.system.blog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationResponseDTO {

	private List<PublicationDTO> content;
	private int pageNumber;
	private int pageSize;
	private long elementTotal;
	private int pageTotal;
	private boolean last;
}
