package TEXTIO is

	-- Type definitions for text I/O:

	type LINE is access STRING;    -- A LINE is a pointer
	                               -- to a STRING value.

	-- The predefined operations for this type are as follows:

	-- function"="  (anonymous, anonymous: LINE) return BOOLEAN;
	-- function"/=" (anonymous, anonymous: LINE) return BOOLEAN;

	-- procedure DEALLOCATE (P: inout LINE);

	type TEXT is file of STRING;   -- A file of variable-length
	                               -- ASCII records.

	-- The predefined operations for this type are as follows:

	-- procedure FILE_OPEN  (file F: TEXT; External_Name; in STRING;
	--                       Open_Kind: in FILE_OPEN_KIND := READ_MODE);
	-- procedure FILE_OPEN  (Status: out FILE_OPEN_STATUS; file F: TEXT;
	--                       External_Name: in STRING;
	--                       Open_Kind: in FILE_OPEN_KIND := READ_MODE);
	-- procedure FILE_CLOSE (file F: TEXT);
	-- procedure READ       (file F: TEXT; VALUE: out STRING);
	-- procedure WRITE      (file F: TEXT; VALUE: in STRING);
	-- procedure FLUSH      (file F: TEXT);
	-- function  ENDFILE    (file F: TEXT) return BOOLEAN;

	type SIDE is (RIGHT, LEFT);    -- For justifying output data
	                               -- within fields.

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: SIDE) return BOOLEAN;
	-- function "/=" (anonymous, anonymous: SIDE) return BOOLEAN;
	-- function "<"  (anonymous, anonymous: SIDE) return BOOLEAN;
	-- function "<=" (anonymous, anonymous: SIDE) return BOOLEAN;
	-- function ">"  (anonymous, anonymous: SIDE) return BOOLEAN;
	-- function ">=" (anonymous, anonymous: SIDE) return BOOLEAN;

	-- function MINIMUM (L, R: SIDE) return SIDE;
	-- function MAXIMUM (L, R: SIDE) return SIDE;

	-- function TO_STRING (VALUE: SIDE) return STRING;

	subtype WIDTH is NATURAL;  -- For specifying widths of output fields.

--vhdl_comp_off -2002
	function JUSTIFY (VALUE: STRING;
	                  JUSTIFIED: SIDE := RIGHT;
	                  FIELD: WIDTH := 0 ) return STRING;
--vhdl_comp_on

	-- Standard text files:

	file INPUT: TEXT open READ_MODE is "STD_INPUT";

	file OUTPUT: TEXT open WRITE_MODE is "STD_OUTPUT";

	-- Input routines for standard types:

	procedure READLINE (file F: TEXT; L: inout LINE);

	procedure READ (L: inout LINE; VALUE: out BIT;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out BIT);

	procedure READ (L: inout LINE; VALUE: out BIT_VECTOR;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out BIT_VECTOR);

	procedure READ (L: inout LINE; VALUE: out BOOLEAN;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out BOOLEAN);

	procedure READ (L: inout LINE; VALUE: out CHARACTER;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out CHARACTER);

	procedure READ (L: inout LINE; VALUE: out INTEGER;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out INTEGER);

	procedure READ (L: inout LINE; VALUE: out REAL;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out REAL);

	procedure READ (L: inout LINE; VALUE: out STRING;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out STRING);

	procedure READ (L: inout LINE; VALUE: out TIME;
	                                GOOD: out BOOLEAN);
	procedure READ (L: inout LINE; VALUE: out TIME);

--vhdl_comp_off -2002
	procedure SREAD (L: inout LINE; VALUE: out STRING;
	                               STRLEN: out NATURAL);
	alias STRING_READ is SREAD [LINE, STRING, NATURAL];

	alias BREAD is READ [LINE, BIT_VECTOR, BOOLEAN];
	alias BREAD is READ [LINE, BIT_VECTOR];
	alias BINARY_READ is READ [LINE, BIT_VECTOR, BOOLEAN];
	alias BINARY_READ is READ [LINE, BIT_VECTOR];

	procedure OREAD (L: inout LINE; VALUE: out BIT_VECTOR;
	                                 GOOD: out BOOLEAN);
	procedure OREAD (L: inout LINE; VALUE: out BIT_VECTOR);
	alias OCTAL_READ is OREAD [LINE, BIT_VECTOR, BOOLEAN];
	alias OCTAL_READ is OREAD [LINE, BIT_VECTOR];

	procedure HREAD (L: inout LINE; VALUE: out BIT_VECTOR;
	                                 GOOD: out BOOLEAN);
	procedure HREAD (L: inout LINE; VALUE: out BIT_VECTOR);
	alias HEX_READ is HREAD [LINE, BIT_VECTOR, BOOLEAN];
	alias HEX_READ is HREAD [LINE, BIT_VECTOR];
--vhdl_comp_on

	-- Output routines for standard types:

	procedure WRITELINE (file F: TEXT; L: inout LINE);

--vhdl_comp_off -2002
	procedure TEE (file F: TEXT; L: inout LINE);
--vhdl_comp_on

	procedure WRITE (L: inout LINE; VALUE: in BIT;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

	procedure WRITE (L: inout LINE; VALUE: in BIT_VECTOR;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

	procedure WRITE (L: inout LINE; VALUE: in BOOLEAN;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

	procedure WRITE (L: inout LINE; VALUE: in CHARACTER;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

	procedure WRITE (L: inout LINE; VALUE: in INTEGER;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

	procedure WRITE (L: inout LINE; VALUE: in REAL;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0;
	                 DIGITS: in NATURAL:= 0);

--vhdl_comp_off -2002
	procedure WRITE (L: inout LINE; VALUE: in REAL;
	                 FORMAT: in STRING);
--vhdl_comp_on

	procedure WRITE (L: inout LINE; VALUE: in STRING;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0);

	procedure WRITE (L: inout LINE; VALUE: in TIME;
	                 JUSTIFIED: in SIDE:= RIGHT; FIELD: in WIDTH := 0;
	                 UNIT: in TIME:= ns);

--vhdl_comp_off -2002
	alias SWRITE       is WRITE [LINE, STRING, SIDE, WIDTH];
	alias STRING_WRITE is WRITE [LINE, STRING, SIDE, WIDTH];

	alias BWRITE       is WRITE [LINE, BIT_VECTOR, SIDE, WIDTH];
	alias BINARY_WRITE is WRITE [LINE, BIT_VECTOR, SIDE, WIDTH];

	procedure OWRITE (L: inout LINE; VALUE: in BIT_VECTOR;
	                  JUSTIFIED: in SIDE := RIGHT; FIELD: in WIDTH := 0);
	alias OCTAL_WRITE is OWRITE [LINE, BIT_VECTOR, SIDE, WIDTH];

	procedure HWRITE (L: inout LINE; VALUE: in BIT_VECTOR;
	                  JUSTIFIED: in SIDE := RIGHT; FIELD: in WIDTH := 0);
	alias HEX_WRITE is HWRITE [LINE, BIT_VECTOR, SIDE, WIDTH];
--vhdl_comp_on

end TEXTIO;

--
-- Package std.textio has no body.
--
