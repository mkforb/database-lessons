package org.jjd.lessons.jpa.dao;

import org.jjd.lessons.dao.Dao;
import org.jjd.lessons.jpa.enity.Group;
import org.jjd.lessons.jpa.enity.Group_;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by User on 07.06.2021.
 */
public class GroupDao implements Dao<Group, Integer> {

    private EntityManager manager;

    public GroupDao(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void add(Group entity) {
        manager.persist(entity);
    }

    @Override
    public Group getByPK(Integer integer) {
        return manager.find(Group.class, integer);
    }

    @Override
    public List<Group> getAll() { // SELECT * FROM tb_group
        // Есть 3 варианта:
        // 1. Именованный запрос (named query)
        // Создается типизированный запрос на основе именованного запроса
        TypedQuery<Group> query1 = manager.createNamedQuery("group.get_all", Group.class);
        List<Group> groups1 = query1.getResultList();

        // 2. JPQL
        // TypedQuery<Group> query2 = manager.createQuery("SELECT g FROM Group g", Group.class);
        Query query2 = manager.createQuery("SELECT g FROM Group g");
        List<Group> groups2 = (List<Group>) query2.getResultList();

        Query query2a = manager.createQuery("SELECT g.title FROM Group g");
        List<String> groups2a = (List<String>) query2a.getResultList();

        // 3. Criteria API
        // Для фанатов ООП :)
        // Формирование запроса
        // CriteriaBuilder - для построения запросов
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        // CriteriaQuery - инкапсулирует запрос. Здесь Group - это тип данных, которые в итоге хотим получить
        CriteriaQuery<Group> criteriaQuery = builder.createQuery(Group.class);
        // Сам запрос не пишем, а составляем "по кусочкам"
        // Root<> - откуда получаем данные
        Root<Group> root = criteriaQuery.from(Group.class); // FROM tb_group
        criteriaQuery.select(root); // SELECT * FROM tb_group
        // Выполнение запроса
        TypedQuery<Group> query3 = manager.createQuery(criteriaQuery);
        List<Group> groups3 = query3.getResultList();

        return groups1;
    }

    @Override
    public void update(Group entity) {
        manager.merge(entity);
    }

    @Override
    public void deleteByPK(Integer integer) {
        // В JPA нет удаления по первичному ключу
        // Только по объекту целиком
        manager.remove(getByPK(integer));
    }

    // SELECT * FROM tb_groups WHERE title = ?
    public Group getByTitle(String title) {
        // 1. Named query
        TypedQuery<Group> query1 = manager.createNamedQuery("group.get_by_title", Group.class);
        query1.setParameter("group_title", title);
        Group group1 = query1.getSingleResult();

        // 3. Criteria API
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = builder.createQuery(Group.class);
        Root<Group> root = criteriaQuery.from(Group.class); // FROM tb_groups
        // Условие выборки
        // g.title = :group_title
        // Условие выборки хранится в предикате (Predicate)
        // Group_ - это сгенерированный класс в папке target
        // Если нужно указать, что классы лежат не только в папке src, но в target,
        // надо ПКМ на target/generated-sources, выбрать Mark Directory as -> Generated Sources Root
        Predicate condition = builder.equal(root.get(Group_.title), title);
        criteriaQuery.select(root).where(condition);
        TypedQuery<Group> query3 = manager.createQuery(criteriaQuery);
        Group group3 = query3.getSingleResult();

        return group1;
    }

    public List<Group> byDurationAndPrice(double duration, int price) {
        // SELECT * FROM group WHERE duration > ? AND price > ?;
        // SELECT g FROM Group g WHERE g.duration > ? AND g.price > ?;

        // 2. JPQL
        TypedQuery<Group> query1 = manager.createQuery("SELECT g FROM Group g WHERE g.duration > :duration AND g.price > :price", Group.class)
                .setParameter("duration", duration)
                .setParameter("price", price);
        List<Group> groups1 = query1.getResultList();

        // 3. Criteria API
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = builder.createQuery(Group.class);
        Root<Group> root = criteriaQuery.from(Group.class);
        Predicate condition = builder.and(builder.greaterThan(root.get(Group_.duration), duration),
                builder.greaterThan(root.get(Group_.price), price));
        criteriaQuery.select(root).where(condition);
        TypedQuery<Group> query3 = manager.createQuery(criteriaQuery);
        List<Group> groups3 = query3.getResultList();

        return groups1;
    }
}
