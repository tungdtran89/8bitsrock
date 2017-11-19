package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name ="identification")
public class Identification implements Serializable
{
    @Id
    private String id;
    @Column(name="name")
    private String name;
    @Column(name="time")
    private Long time;
    @Column(name="waiting_time")
    private Long waitingTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

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

    public Company getCompany()
    {
        return company;
    }

    public void setCompany(Company company)
    {
        this.company = company;
    }
}
