package us.guihouse.autobank.bean;

/**
 * Created by aluno on 28/11/16.
 */

public class CardLostOrStolen {
    public CardLostOrStolen(Long cardId, String comment) {
        this.cardId = cardId;
        this.clientComment = comment;
    }

    private Long cardId;
    private String clientComment;

    public String getClientComment() {
        return clientComment;
    }

    public void setClientComment(String clientComment) {
        this.clientComment = clientComment;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
