package board.entity;

import board.entity.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-04T22:22:04")
@StaticMetamodel(Notice.class)
public class Notice_ { 

    public static volatile SingularAttribute<Notice, Date> postedDateTime;
    public static volatile SingularAttribute<Notice, String> title;
    public static volatile SingularAttribute<Notice, String> category;
    public static volatile SingularAttribute<Notice, User> poster;
    public static volatile SingularAttribute<Notice, Integer> noticeId;
    public static volatile SingularAttribute<Notice, String> content;

}