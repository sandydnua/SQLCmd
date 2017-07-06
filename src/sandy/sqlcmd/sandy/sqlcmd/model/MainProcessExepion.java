package sandy.sqlcmd.sandy.sqlcmd.model;

import java.sql.SQLException;

public class MainProcessExepion extends Throwable {
    public MainProcessExepion(String message) {
        super(message);
    }
/*
    public MainProcessExepion(SQLException e) {
        super(e);
    }*/
/*
    public MainProcessExepion(RuntimeException e) {
        super(e);
    }*/
}
