module org.example {
    requires javafx.controls;
    requires java.sql;
    requires org.apache.commons.dbcp2;
    requires com.google.gson;
    exports org.example;
    opens org.example.Models to com.google.gson;
}
