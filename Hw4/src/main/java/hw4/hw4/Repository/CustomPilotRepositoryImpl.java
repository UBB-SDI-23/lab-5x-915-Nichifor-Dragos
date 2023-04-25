package hw4.hw4.Repository;

import hw4.hw4.DTO.Pilot.PilotDTO_CarStatistic;
import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic;
import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import java.util.List;

public class CustomPilotRepositoryImpl implements CustomPilotRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PilotDTO_CarStatistic> getPilotsWithNumberOfCarsAsc() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<PilotDTO_CarStatistic> query = cb.createQuery(PilotDTO_CarStatistic.class);
        Root<Pilot> pilotRoot = query.from(Pilot.class);
        Join<Pilot, Car> pilotCarJoin = pilotRoot.join("cars", JoinType.INNER);

        query.select(cb.construct(
                        PilotDTO_CarStatistic.class,
                        pilotRoot.get("id"),
                        pilotRoot.get("firstName"),
                        pilotRoot.get("lastName"),
                        cb.count(pilotCarJoin.get("brand"))
                ))
                .groupBy(pilotRoot.get("id"), pilotRoot.get("firstName"), pilotRoot.get("lastName"))
                .orderBy(cb.desc(cb.count(pilotCarJoin.get("brand"))));

        TypedQuery<PilotDTO_CarStatistic> typedQuery = this.entityManager.createQuery(query);
        typedQuery.setMaxResults(50);

        return typedQuery.getResultList();
    }
}
