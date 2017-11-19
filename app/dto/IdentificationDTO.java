package dto;

public class IdentificationDTO
{
    private String id;
    private String name;
    private Long time;
    private Long waitingTime;
    private String companyId;

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

    public Long getTime()
    {
        return time;
    }

    public void setTime(Long time)
    {
        this.time = time;
    }

    public Long getWaitingTime()
    {
        return waitingTime;
    }

    public void setWaitingTime(Long waitingTime)
    {
        this.waitingTime = waitingTime;
    }

    public String getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }
}
