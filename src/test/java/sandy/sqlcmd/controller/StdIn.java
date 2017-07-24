package sandy.sqlcmd.controller;

import java.io.IOException;
import java.io.InputStream;

class StdIn extends InputStream {

    private String string;
    private boolean eof = false;

    @Override
    public int read() throws IOException {

        if ( string.length() == 0 ) {
            return -1;
        }

        if ( eof ) {

            eof = false;
            return -1;
        }

        char ch = string.charAt(0);
        string = string.substring(1);

        if ( ch == '\n' ) {
            eof = true;
        }

        return (int)ch;
    }

    public void add(String line) {

        if ( this.string == null ) {
            this.string = line;
        } else {
            this.string += "\n" + line;
        }
    }
}
