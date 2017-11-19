package repository;

import entity.Company;
import entity.Identification;
import play.db.jpa.JPA;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Transactional
public class Repository
{
    private static final String PERSISTENCE_UNIT_NAME = "default";
    @Inject
    JPAApi jpa;

    public Identification addIdentification(Identification identification)
    {
        jpa.em(PERSISTENCE_UNIT_NAME).persist(identification);
        return identification;
    }

    public List<Identification> getAllIdentifications(int limit)
    {
        String query = "SELECT ident FROM Identification ident " +
                "order by ident.company.slaPercentage desc, ident.waitingTime desc, ident.company.currentSLAPercentage asc";
        List<Identification> results = JPA.em(PERSISTENCE_UNIT_NAME).createQuery(query)
                .setMaxResults(limit).getResultList();
        return results;
    }

    public Company addCompany(Company company)
    {
        JPA.em(PERSISTENCE_UNIT_NAME).persist(company);
        return company;
    }
}
