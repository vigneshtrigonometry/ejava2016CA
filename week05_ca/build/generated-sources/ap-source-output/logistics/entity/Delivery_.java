package logistics.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import logistics.entity.Pod;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-07T17:48:10")
@StaticMetamodel(Delivery.class)
public class Delivery_ { 

    public static volatile SingularAttribute<Delivery, String> address;
    public static volatile SingularAttribute<Delivery, Pod> pod;
    public static volatile SingularAttribute<Delivery, String> phone;
    public static volatile SingularAttribute<Delivery, String> name;
    public static volatile SingularAttribute<Delivery, Integer> pkgId;
    public static volatile SingularAttribute<Delivery, Date> createDate;

}