package br.com.letscode.rebels.person.services;


import br.com.letscode.rebels.core.exceptions.GenericException;
import br.com.letscode.rebels.person.domain.dto.*;

import java.util.List;

public interface PersonService {

    /**
     * Adiciona um rebelde ao sistema
     * @param dto Objeto com os dados para inclusão de um rebelde
     * @return um {@link PersonDisplayDTO} com os dados do rebelde cadastrodo
     */
    PersonDisplayDTO add(AddPersonDTO dto);

    /**
     * Retorna um rebelde caso o ID exista, se não existir levanta um execeção {@link GenericException}
     * @param id Identificador único do rebelde
     * @return um {@link PersonDisplayDTO} com os dados do rebelde cadastrodo
     */
    PersonDisplayDTO get(String id);

    /**
     * Atualiza a localização de um rebelde
     * @param dto Objeto com os dados de localização
     */
    void alter(LocalizationWithIDDTO dto);

    /**
     * Retorna todos os rebeldes cadastrados na galáxia
     * @return uma lista de {@link PersonDisplayDTO} com os rebeldes
     */
    List<PersonDisplayDTO>  findAll();

    /**
     * Reporta um rebelde como traidor
     * @param id Identificador do traidor
     */
    void betray(String id);

    /**
     * Realiza a troca do rebelde com {id} para os itens que estão no objeto {dto}
     * @param dto Objeto com o identificador do outro rebelde e a lista de itens
     */
    void exchange(ExchangeDTO dto);

    /**
     * Conta quantos rebeldes existem no catálogo
     * @return Retorna o total de rebeldes
     */
    int countAllByRebelIsTrue();

    /**
     * Conta quantos traidores existem no catálogo
     * @return Retorna o total de traidores
     */
    int countAllByRebelIsFalse();

    /**
     * Conta o total de pessoas no catálogo
     * @return Retorna o total de rebeldes e traidores
     */
    int countAllBy();

    /**
     * Lista todo os traidores do catálogo
     * @return Uma lista com todos os rebeldes que foram marcados como traidor
     */
    List<PersonDisplayDTO> findAllTraitors();
}
