package hw4.hw4.Repository;

import hw4.hw4.DTO.Pilot.PilotDTO_CarStatistic;
import hw4.hw4.Entity.Car;
import hw4.hw4.Entity.Pilot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

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
                .orderBy(cb.asc(cb.count(pilotCarJoin.get("brand"))));

        return this.entityManager.createQuery(query).getResultList();
    }
}
