package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Company implements Serializable
{
    @Id
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "sla_time")
    private Long slaTime;
    @Column(name = "sla_percentage")
    private Double slaPercentage;
    @Column(name = "current_sla_percentage")
    private Double currentSLAPercentage;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Long getSlaTime()
    {
        return slaTime;
    }

    public void setSlaTime(Long slaTime)
    {
        this.slaTime = slaTime;
    }

    public Double getSlaPercentage()
    {
        return slaPercentage;
    }

    public void setSlaPercentage(Double slaPercentage)
    {
        this.slaPercentage = slaPercentage;
    }

    public Double getCurrentSLAPercentage()
    {
        return currentSLAPercentage;
    }

    public void setCurrentSLAPercentage(Double currentSLAPercentage)
    {
        this.currentSLAPercentage = currentSLAPercentage;
    }
}
