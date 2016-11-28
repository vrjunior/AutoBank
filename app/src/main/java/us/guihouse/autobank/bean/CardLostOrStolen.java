package us.guihouse.autobank.bean;

/**
 * Created by aluno on 28/11/16.
 */

public class CardLostOrStolen {
    private Long cardId;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
