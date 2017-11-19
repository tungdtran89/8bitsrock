package entity;

import java.io.Serializable;

public class Company implements Serializable
{
    private String id;
    private String name;
    private Long slaTime;
    private Double slaPercentage;
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
