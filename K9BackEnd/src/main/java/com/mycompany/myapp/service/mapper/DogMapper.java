package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.DogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dog and its DTO DogDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface DogMapper extends EntityMapper<DogDTO, Dog> {

    @Mapping(source = "owns.id", target = "ownsId")
    @Mapping(source = "owns.login", target = "ownsLogin")
    DogDTO toDto(Dog dog); 

    @Mapping(source = "ownsId", target = "owns")
    Dog toEntity(DogDTO dogDTO);

    default Dog fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dog dog = new Dog();
        dog.setId(id);
        return dog;
    }
}
