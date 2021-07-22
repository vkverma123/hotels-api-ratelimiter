package com.hotels.web.api.mapper;

/**
 * Mapper interface.
 * @param <S> source.
 * @param <D> destination.
 */
public interface IMapper<S, D> {

    /**
     * Maps from S (source) to D (destination).
     * @param source source to map from.
     * @return destination to map to.
     */
    D map(final S source);
}
