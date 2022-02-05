module com.goranrsbg.gi {
    requires java.naming;
    requires java.persistence;
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.goranrsbg.apppreloader;
    requires org.apache.logging.log4j;
    requires org.hibernate.orm.core;
    requires org.hibernate.orm.c3p0;
    requires mysql.connector.java;

    opens com.goranrsbg.gi to javafx.fxml;
    opens com.goranrsbg.gi.controller to javafx.fxml;
    opens com.goranrsbg.gi.fxml to javafx.fxml;
    opens com.goranrsbg.gi.database.entity to org.hibernate.orm.core;
    opens com.goranrsbg.gi.database.entity.embedable to org.hibernate.orm.core;

    exports com.goranrsbg.gi;
}
