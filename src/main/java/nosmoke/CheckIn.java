package nosmoke;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="CheckIn_table")
public class CheckIn {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long smokingAreaId;
    private Long point;
    private String status;

    @PostPersist
    public void onPostPersist(){
        CheckIned checkIned = new CheckIned();
        BeanUtils.copyProperties(this, checkIned);
        checkIned.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){
        if(this.getStatus()==null){
            CheckOuted checkOuted = new CheckOuted();
            BeanUtils.copyProperties(this, checkOuted);
            checkOuted.publishAfterCommit();
        }


    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getSmokingAreaId() {
        return smokingAreaId;
    }

    public void setSmokingAreaId(Long smokingAreaId) {
        this.smokingAreaId = smokingAreaId;
    }
    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
