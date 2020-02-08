package ru.kpfu.itis.Utils;

public interface DTOMapper<E, T> {
    E toEntity(T dto);

    T toDto(E entity);
}
