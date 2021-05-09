package br.com.letscode.rebels.core.enums;

import lombok.Getter;

public enum MessageEnum {
    PERSON_NOT_FOUND("Rebelde não encontrado",404),
    ITEM_NOT_FOUND("Item não encontrado",404),
    INVALID_UPDATE_LOCALIZATION("Não é possível atualizar a localização de um traidor",400),
    INVALID_EXCHANGE_PERSON_NOT_REBEL("Não é possível realizar a troca. Um ou mais pessoas envolvidas são traidores",400),
    INVALID_EXCHANGE("Item informado para troca não pertence a um dos rebeldes",400),
    SAME_ID_TO_EXCHANGE("O ID do rebelde de origem não pode ser igual ao de destino",400),
    INVALID_EXCHANGE_QUANTITY("Quantidade do item informado para troca é superior ao existente",400),
    TOTAL_DIVERGENCE("Valores incompatíveis para troca",400),
    ;

    @Getter
    private final String message;
    @Getter
    private final int statusCode;

    MessageEnum(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
