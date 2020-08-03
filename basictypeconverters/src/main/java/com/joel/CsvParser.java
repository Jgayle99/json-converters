package com.joel;

import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Joel Gayle on 02 Aug, 2020
 */
public class CsvParser {
        static final private int NUMMARK = 10;
        static final private char COMMA = ',';
        static final private char DQUOTE = '"';
        static final private char CRETURN = '\r';
        static final private char LFEED = '\n';
        static final private char SQUOTE = '\'';
        static final private char COMMENT = '#';

        private char separator = ',';
        private ArrayList<String> fields;
        private boolean eofSeen;
        private Reader in;


        public CsvParser(char separator, Reader input)
        {
            this.separator = separator;
            this.fields = new ArrayList<>();
            this.eofSeen = false;
            this.in = new BufferedReader(input);
        }

        public CsvParser( char separator, InputStream input ) throws java.io.IOException {
            this.separator = separator;
            this.fields = new ArrayList<>();
            this.eofSeen = false;
            this.in = new BufferedReader (new InputStreamReader(input, "UTF-8"));
        }

        public boolean hasNext() throws java.io.IOException {
            if ( eofSeen ) return false;
            fields.clear();
            eofSeen = split( in, fields );
            if ( eofSeen ) return ! fields.isEmpty();
            else return true;
        }

        public List<String> next() {
            return fields;
        }

        // Returns true if EOF seen.
        static private boolean discardLinefeed(Reader in) throws java.io.IOException {
                in.mark(NUMMARK);
                int value = in.read();
                if ( value == -1 ) return true;
                else if ( (char)value != LFEED ) in.reset();
                return false;
        }


        private boolean skipComment(Reader in)
                throws java.io.IOException
        {
            /* Discard line. */
            int value;
            while ( (value = in.read()) != -1 ) {
                char c = (char)value;
                if ( c == CRETURN )
                    return discardLinefeed( in );
            }
            return true;
        }

        // Returns true when EOF has been seen.
        private boolean split(Reader in,ArrayList<String> fields)
                throws java.io.IOException
        {
            StringBuilder sbuf = new StringBuilder();
            int value;
            while ( (value = in.read()) != -1 ) {
                char c = (char)value;
                switch(c) {
                    case CRETURN:
                        if ( sbuf.length() > 0 ) {
                            fields.add( sbuf.toString() );
                            sbuf.delete( 0, sbuf.length() );
                        }
                        return discardLinefeed( in );

                    case LFEED:
                        if ( sbuf.length() > 0 ) {
                            fields.add( sbuf.toString() );
                            sbuf.delete( 0, sbuf.length() );
                        }
                     return false;

                    case DQUOTE:
                    {
                        // Processing double-quoted string ..
                        while ( (value = in.read()) != -1 ) {
                            c = (char)value;
                            if ( c == DQUOTE ) {
                                // Saw another double-quote. Check if
                                // another char can be read.
                                in.mark(NUMMARK);
                                if ( (value = in.read()) == -1 ) {
                                    // Nope, found EOF; means End of
                                    // field, End of record and End of
                                    // File
                                    if ( sbuf.length() > 0 ) {
                                        fields.add( sbuf.toString() );
                                        sbuf.delete( 0, sbuf.length() );
                                    }
                                    return true;
                                } else if ( (c = (char)value) == DQUOTE ) {
                                    // Found a second double-quote
                                    // character. Means the double-quote
                                    // is included.
                                    sbuf.append( DQUOTE );
                                } else if ( c == CRETURN ) {
                                    // Found End of line. Means End of
                                    // field, and End of record.
                                    if ( sbuf.length() > 0 ) {
                                        fields.add( sbuf.toString() );
                                        sbuf.delete( 0, sbuf.length() );
                                    }
                                    // Read and discard a line-feed if we
                                    // can indeed do so.
                                    return discardLinefeed( in );
                                } else if ( c == LFEED ) {
                                    // Found end of line. Means End of
                                    // field, and End of record.
                                    if ( sbuf.length() > 0 ) {
                                        fields.add( sbuf.toString() );
                                        sbuf.delete( 0, sbuf.length() );
                                    }
                                    // No need to check further. At this
                                    // point, we have not yet hit EOF, so
                                    // we return false.
                                    return false;
                                } else {
                                    // Not one of EOF, double-quote,
                                    // newline or line-feed. Means end of
                                    // double-quote processing. Does NOT
                                    // mean end-of-field or end-of-record.
                                    // System.err.println("EOR on '" + c +
                                    // "'");
                                    in.reset();
                                    break;
                                }
                            } else {
                                // Not a double-quote, so no special meaning.
                                sbuf.append( c );
                            }
                        }
                        // Hit EOF, and did not see the terminating double-quote.
                        if ( value == -1 ) {
                            // We ignore this error, and just add whatever
                            // left as the next field.
                            if ( sbuf.length() > 0 ) {
                                fields.add( sbuf.toString() );
                                sbuf.delete( 0, sbuf.length() );
                            }
                            return true;
                        }
                    }
                    break;

                    default:
                        if ( c == separator ) {
                            fields.add( sbuf.toString() );
                            sbuf.delete(0, sbuf.length());
                        } else {
                            /* A comment line is a line starting with '#' with
                             * optional whitespace at the start. */
                            if ( c == COMMENT && fields.isEmpty() &&
                                    sbuf.toString().trim().isEmpty() ) {
                                boolean eof = skipComment(in);
                                if ( eof ) return eof;
                                else sbuf.delete(0, sbuf.length());
                                /* Continue with next line if not eof. */
                            } else sbuf.append(c);
                        }
                }
            }
            if ( sbuf.length() > 0 ) {
                fields.add( sbuf.toString() );
                sbuf.delete( 0, sbuf.length() );
            }
            return true;
        }
    }

