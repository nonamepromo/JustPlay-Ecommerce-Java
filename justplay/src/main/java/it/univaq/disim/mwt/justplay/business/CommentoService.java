public interface CommentoService{

    Commento findById(Long id) throws BusinessException;

    void addCommento(Videogioco videogioco, Utente utente, String contenuto) throws BusinessException;

    void updateCommento(Commento commento) throws BusinessException;

    Comment findBtId(Long id) throws BusinessException;

}