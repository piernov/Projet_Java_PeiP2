/* S t d I n p u t . j a v a

   Author  : Granet Vincent vg@unice.fr

   Creation date   : 02-Oct-1998 17:48 
   Last file update: 18-Sep-2013 11:02 

   Part of this code is an adaptation of methods:
    - parseInt from Integer.java
    - nextToken from StreamToken.java
    - readLine from  DataInputStream.java  
*/

import java.io.*;

/**
 * The StdInput class provides methods to read 
 * basic type data from standard input.
 *
 * @author  V. Granet
 */
public  class StdInput {   
        //
        // P R I V A T E  S T U F F
        //
        private static 
            PushbackReader stdin = new PushbackReader(new InputStreamReader(System.in));

        private static final int LineBufferLength = 10;

        private static long readLong(int radix) 
            throws  IOException
        {
            if (radix < Character.MIN_RADIX) {
                throw new IOException("radix " + radix + 
				      " less than Character.MIN_RADIX");
            }
        
            if (radix > Character.MAX_RADIX) {
                throw new IOException("radix " + radix + 
                                            " greater than Character.MAX_RADIX");
            }
            
            long result = 0;
            boolean negative = false;
            long limit = -Long.MAX_VALUE;
            long multmin;
            int digit, c;
        
            // skip spaces
            while (Character.isSpaceChar((char) (c=stdin.read())));
            // first char of the number to read
            if (c == '-' || c == '+') {
                if (c == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else 
                    limit = -Long.MAX_VALUE;
                c = stdin.read();
            }
            multmin = limit / radix;
            digit = Character.digit((char) c,radix);
            if (digit < 0)
                throw new IOException("Bad integer");
            else 
                result = -digit;

            while (Character.isDigit((char) (c = stdin.read()))) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit((char) c,radix);
                if (digit < 0)
                    throw new IOException("Bad integer");
                if (result < multmin) 
                    throw new IOException("Bad integer");
                result *= radix;
                if (result < limit + digit)
                    throw new IOException("Bad integer");
                result -= digit;
            }
            // push back current character to std input
            stdin.unread(c);
            return negative ? result : -result;
        }

        private static double readNumber()
            throws IOException
        {
            int c, peekc;
            boolean negative = false;
            // skip spaces
            while (Character.isSpaceChar((char) (c=stdin.read())));
            // first char of the number to read
            boolean neg = false;
            if (c == '-' || c == '+') {
                if (c == '-') negative = true;
		c = stdin.read();
            }
            double v = 0;
            int decexp = 0;
            int seendot = 0;
            for (;;) {
                if (c == '.' && seendot == 0)
                    seendot = 1;
                else if ('0' <= c && c <= '9') {
                    v = v * 10 + (c - '0');
                    decexp += seendot;
                } else
                    break;
                c = stdin.read();
            }
            // push back current character to std input
            stdin.unread(c);
            peekc = c;
            if (decexp != 0) {
                double denom = 10;
                decexp--;
                while (decexp > 0) {
                    denom *= 10;
                    decexp--;
                }
                // do one division of a likely-to-be-more-accurate number
                v = v / denom;
            }
            return negative ? -v : v;
        }

        //
        // P U B L I C   M E T H O D S
        //

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>short</code>. Leading spaces are skipped.
	 *
	 * @return a <code>short</code>
	 * @exception  IOException
	*/
        public static short readShort()
            throws IOException
        {
            return (short) StdInput.readLong(10);        
        } 

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>short</code>, and go to the first character of the next line.
	 * Leading spaces are skipped.
	 *
	 * @return a <code>short</code>
	 * @exception  IOException
	*/
        public static short readlnShort()
            throws IOException
        {
	    short s = 0;
	    try {
		s = readShort();
	    }
	    finally {
		readln();
	    }
            return s;
        }

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>int</code>. Leading spaces are skipped.
	 *
	 * @return a <code>int</code>
	 * @exception  IOException
	*/
        public static int readInt()
            throws IOException
        {
            return (int) StdInput.readLong(10);        
        } 

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>int</code>, and go to the first character of the next line.
	 * Leading spaces are skipped.
	 *
	 * @return a <code>int</code>
	 * @exception  IOException
	*/
        public static int readlnInt()
            throws IOException
        {
            int i = 0;
	    try {
		i = readInt();
	    }
	    finally {
		readln();
	    }
            return i;
        }

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>long</code>. Leading spaces are skipped.
	 *
	 * @return a <code>long</code>
	 * @exception  IOException
	*/
        public static long readLong()
            throws IOException
        {
            return StdInput.readLong(10);        
        } 

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>long</code>, and go to the first character of the next line.
	 * Leading spaces are skipped.
	 *
	 * @return a <code>long</code>
	 * @exception  IOException
	*/
        public static long readlnLong()
            throws IOException
        {
            long l = 0;
	    try {
		l = readLong();
	    }
	    finally {
		readln();
	    }
            return l;
        }

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>double</code>. Leading spaces are skipped.
	 *
	 * @return a <code>double</code>
	 * @exception  IOException
	*/
        public static double readDouble() 
	    throws IOException
        {
            return readNumber();
        }

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>double</code>, and go to the first character of the next line.
	 * Leading spaces are skipped.
	 *
	 * @return a <code>double</code>
	 * @exception  IOException
	*/
        public static double readlnDouble()
            throws IOException
        {
            double d = 0;
	    try {
		d = readDouble();
	    }
	    finally {
		readln();
	    }
            return d;
        }

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>float</code>. Leading spaces are skipped.
	 *
	 * @return a <code>float</code>
	 * @exception  IOException
	*/
        public static float readFloat()
            throws IOException
        {
            return (float) readNumber();
        }

        /** 
	 * Reads from standard input characters which represent a 
	 * <code>float</code>, and go to the first character of the next line.
	 * Leading spaces are skipped.
	 *
	 * @return a <code>float</code>
	 * @exception  IOException
	*/
        public static float readlnFloat()
            throws IOException
        {
            float f = 0;
	    try {
		f = readFloat();
	    }
	    finally {
		readln();
	    }
            return f;
        }

        /** 
	 * Reads next character from standard input.
	 * Lève <code>EOFException</code> si fin de fichier
	 *
	 * @return a <code>char</code>
	 * @exception  IOException, EOFException
	 */
	public static char readChar() 
            throws IOException, EOFException
        {
	    int c = stdin.read(); 
	    if (c == -1) 
		// fin de fichier
		throw new EOFException();
            return (char) c;
        } 

        /** 
	 * Reads next character from standard input,
	 * and go to the first character of next line
	 * @return a <code>char</code>
	 * @exception  IOException
	*/
        public static char readlnChar()
            throws IOException
        {
            char c = '\0';
	    try {
		c = readChar();
	    }
	    finally {
		readln();
	    }
            return c;
        } 

	/*
	 * Reads up to n characters from standard input,  
	 * 
	 * @param   n   an <code>int</code>.
	 * @return a <code>String</code>
	 * @exception  IOException
	*/
        public static String readString(int n)
            throws IOException
        {
            char b[] = new char[n];
        
            stdin.read(b,0,n);
            return new String(b);
        }

        /** 
	 * Reads up to the end of line characters from standard input,  
	 * 
	 * @return a <code>String</code>
	 * @exception  IOException
	*/ 
        public static String readLine() throws IOException {

            char buf[], lineBuffer[];
            int room = LineBufferLength;
            int offset = 0;
            int c;

	    buf = lineBuffer = new char[LineBufferLength];

        Loop:
            for (;;) {
                switch (c = stdin.read()) {
                case -1: // EOF
                case '\n': break Loop;
                case '\r':
                    int c2 = stdin.read();
                    if (c2 != '\n') stdin.unread(c2);
                    break Loop;
                default:
                    if (--room < 0) {
                        buf = new char[offset + LineBufferLength];
                        room = buf.length - offset - 1;
			System.arraycopy(lineBuffer, 0, buf, 0, offset);
			lineBuffer = buf;
                    }
                    buf[offset++] = (char) c;
                    break;
                }
            }
            if (c == -1 && offset == 0) return null;
            
            return String.copyValueOf(buf, 0, offset);
        }

        /** 
	 *  Go to next line from standard input,  
	 * 
	 * @exception  IOException
	*/ 
        public static void readln()
            throws IOException
        {
            while ((stdin.read()) != '\n');
        }
    }
