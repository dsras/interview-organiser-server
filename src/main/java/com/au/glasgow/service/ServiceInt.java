package com.au.glasgow.service;

/*
Service interface containing headers for basic business logic code
that calls repository to access database objects
to be extended by all services
 */
public interface ServiceInt<E> {

    //get entity by ID
    E getById(Integer id);

    //get entities by ID
    Iterable<E> getById(Iterable<Integer> ids);

    //save entity
    <S extends E> E save(E entity);

    //save multiple entities
    <S extends E> Iterable<S> saveAll(Iterable<S> entities);

}
