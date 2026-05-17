import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Classe abstrata GenericDAO conforme os requisitos do roteiro.
 * Utiliza generics (E = Entidade, K = Chave Primária) e JPA.
 */
public abstract class GenericDAO<E, K> {
    
    // Fábrica de EntityManager conectada à unidade de persistência do seu persistence.xml
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SuperTrunfoPU");
    private final Class<E> classeEntidade;

    // Construtor que recebe a classe da entidade para buscas automáticas
    public GenericDAO(Class<E> classeEntidade) {
        this.classeEntidade = classeEntidade;
    }

    // Cria e retorna um EntityManager limpo para cada operação
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Requisito: incluir(E entidade)
    public boolean incluir(E entidade) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    // Requisito: excluir(K chave)
    public boolean excluir(K chave) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            E entidade = em.find(classeEntidade, chave);
            if (entidade != null) {
                em.remove(entidade);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    // Requisito: obter(K chave)
    public E obter(K chave) {
        EntityManager em = getEntityManager();
        try {
            return em.find(classeEntidade, chave);
        } finally {
            em.close();
        }
    }

    // Requisito: obterTodos()
    public List<E> obterTodos() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM " + classeEntidade.getSimpleName() + " e";
            return em.createQuery(jpql, classeEntidade).getResultList();
        } finally {
            em.close();
        }
    }

    // Requisito: alterar(E entidade)
    public boolean alterar(E entidade) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(entidade);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
    
    // Método para fechar a fábrica de recursos corretamente ao finalizar o jogo
    public static void fecharFabrica() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
