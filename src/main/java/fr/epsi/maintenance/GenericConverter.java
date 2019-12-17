package fr.epsi.maintenance;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Component
public class GenericConverter<DTO, E> {

	@Autowired
	private ModelMapper modelMapper;

	public DTO entityToDto(final E e, final Class<DTO> c) {
		return this.modelMapper.map(e, c);
	}

	public Collection<DTO> entitiesToDtos(final Collection<E> e, final Class<DTO> c) {
		if (e == null || e.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		return e.stream().map(n -> this.entityToDto(n, c)).collect(Collectors.toCollection(ArrayList::new));
	}

	public E dtoToEntity(final DTO dto, final Class<E> c) {
		return this.modelMapper.map(dto, c);
	}

	public Collection<E> dtosToEntities(final Collection<DTO> dto, final Class<E> c) {
		if (dto == null || dto.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		return dto.stream().map(n -> this.dtoToEntity(n, c)).collect(Collectors.toCollection(ArrayList::new));
	}

	public void entityToDto(final E e, final DTO dto) {
		this.modelMapper.map(e, dto);
	}

	public void dtoToEntity(final DTO dto, final E entity) {
		this.modelMapper.map(dto, entity);
	}

}
