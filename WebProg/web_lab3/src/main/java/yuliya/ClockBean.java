package yuliya;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Date;

@ManagedBean(name = "clockBean")
@ViewScoped
public class ClockBean implements Serializable {
    private Date dateTime = new Date();

}

