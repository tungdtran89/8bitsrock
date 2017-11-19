package service;

import dto.IdentificationDTO;
import entity.Identification;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import repository.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains business logic of the app
 */
@Singleton
public class IDNowService
{
    /**
     * Mapper for DTO and entity
     */
    private static final Mapper POJO_MAPPER = new DozerBeanMapper();

    @Inject
    Repository repository;

    public void addIdentification(IdentificationDTO identDTO)
    {
        Identification identEntity = POJO_MAPPER.map(identDTO, Identification.class);

        repository.addIdentification(identEntity);
    }


    public List<IdentificationDTO> getIdentifications()
    {
        List<Identification> identEntityList = repository.getAllIdentifications(100);
        List<IdentificationDTO> identDTOList = identEntityList.parallelStream()
                .map(entity -> POJO_MAPPER.map(entity, IdentificationDTO.class)).collect(Collectors.toList());


        return identDTOList;
    }
}
