package repository;

import entity.Identification;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import javax.inject.Singleton;

@Singleton
@Transactional
public class Repository
{
    public Identification addIdentification(Identification identification)
    {
        JPA.em().persist(identification);
        return identification;
    }
}
