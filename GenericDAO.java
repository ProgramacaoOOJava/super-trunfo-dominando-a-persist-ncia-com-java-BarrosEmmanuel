import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public abstract class GenericDAO<E, K> {
    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("SuperTrunfoPU");
    private final Class<E> classeEntidade;

    public GenericDAO(Class<E> classeEntidade) {
        this.classeEntidade = classeEntidade;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public boolean inserir(E entidade) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entidade);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("❌ Erro ao salvar: " + e.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    public boolean remover(K chave) {
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

    public List<E> listarTodos() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT e FROM " + classeEntidade.getSimpleName() + " e";
            return em.createQuery(jpql, classeEntidade).getResultList();
        } finally {
            em.close();
        }
    }

    public E obtener(K chave) {
        EntityManager em = getEntityManager();
        try {
            return em.find(classeEntidade, chave);
        } finally {
            em.close();
        }
    }
    
    public static void fecharFabrica() {
        if (emf != null && emf.isOpen()) emf.close();
    }
}
