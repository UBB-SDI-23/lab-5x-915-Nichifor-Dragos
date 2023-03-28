package hw4.hw4.Repository;

import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic;
import hw4.hw4.DTO.Race.RaceDTO_PilotStatistic_CountryUSA;
import hw4.hw4.Entity.Pilot;
import hw4.hw4.Entity.Race;
import hw4.hw4.Entity.RacePilot.RacesPilots;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.List;

public class CustomRaceRepositoryImpl implements CustomRaceRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<RaceDTO_PilotStatistic> getRacesWithNumberOfPilotsDesc() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<RaceDTO_PilotStatistic> query = cb.createQuery(RaceDTO_PilotStatistic.class);
        Root<Race> raceRoot = query.from(Race.class);
        Join<Race, RacesPilots> raceRacesPilotsJoin = raceRoot.join("racesPilots", JoinType.INNER);
        Join<RacesPilots, Pilot> racesPilotsPilotJoin = raceRacesPilotsJoin.join("pilot", JoinType.INNER);

        query.select(cb.construct(
                        RaceDTO_PilotStatistic.class,
                        raceRoot.get("id"),
                        raceRoot.get("name"),
                        cb.count(racesPilotsPilotJoin.get("firstName"))
                ))
                .groupBy(raceRoot.get("name"), raceRoot.get("id"))
                .orderBy(cb.desc(cb.count(racesPilotsPilotJoin.get("firstName"))));

        return this.entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<RaceDTO_PilotStatistic_CountryUSA> getRacesFromUSAWithNumberOfPilotsDesc() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<RaceDTO_PilotStatistic_CountryUSA> query = cb.createQuery(RaceDTO_PilotStatistic_CountryUSA.class);
        Root<Race> raceRoot = query.from(Race.class);
        Join<Race, RacesPilots> raceRacesPilotsJoin = raceRoot.join("racesPilots", JoinType.INNER);
        Join<RacesPilots, Pilot> racesPilotsPilotJoin = raceRacesPilotsJoin.join("pilot", JoinType.INNER);

        query.select(cb.construct(
                        RaceDTO_PilotStatistic_CountryUSA.class,
                        raceRoot.get("id"),
                        raceRoot.get("name"),
                        raceRoot.get("country"),
                        cb.count(racesPilotsPilotJoin.get("firstName"))
                ))
                .where(cb.equal(raceRoot.get("country"), "USA"))
                .groupBy(raceRoot.get("name"), raceRoot.get("id"), raceRoot.get("country"))
                .orderBy(cb.desc(cb.count(racesPilotsPilotJoin.get("firstName"))));

        return this.entityManager.createQuery(query).getResultList();
    }

}
