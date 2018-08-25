package STANDARD is

	-- Predefined enumeration types:
	type BOOLEAN is (FALSE, TRUE);

	-- The predefined operations for this type are as follows:

	-- function "and"  (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "or"   (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "nand" (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "nor"  (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "xor"  (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "xnor" (anonymous, anonymous: BOOLEAN) return BOOLEAN;

	-- function "not"  (anonymous: BOOLEAN) return BOOLEAN;

	-- function "="    (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "/="   (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "<"    (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function "<="   (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function ">"    (anonymous, anonymous: BOOLEAN) return BOOLEAN;
	-- function ">="   (anonymous, anonymous: BOOLEAN) return BOOLEAN;

	-- function MINIMUM (L, R: BOOLEAN) return BOOLEAN;
	-- function MAXIMUM (L, R: BOOLEAN) return BOOLEAN;

	-- function RISING_EDGE  (signal S: BOOLEAN) return BOOLEAN;
	-- function FALLING_EDGE (signal S: BOOLEAN) return BOOLEAN;

	type BIT is ('0', '1');

	-- The predefined operations for this type are as follows:

	-- function "and"  (anonymous, anonymous: BIT) return BIT;
	-- function "or"   (anonymous, anonymous: BIT) return BIT;
	-- function "nand" (anonymous, anonymous: BIT) return BIT;
	-- function "nor"  (anonymous, anonymous: BIT) return BIT;
	-- function "xor"  (anonymous, anonymous: BIT) return BIT;
	-- function "xnor" (anonymous, anonymous: BIT) return BIT;

	-- function "not"  (anonymous: BIT) return BIT;

	-- function "="    (anonymous, anonymous: BIT) return BOOLEAN;
	-- function "/="   (anonymous, anonymous: BIT) return BOOLEAN;
	-- function "<"    (anonymous, anonymous: BIT) return BOOLEAN;
	-- function "<="   (anonymous, anonymous: BIT) return BOOLEAN;
	-- function ">"    (anonymous, anonymous: BIT) return BOOLEAN;
	-- function ">="   (anonymous, anonymous: BIT) return BOOLEAN;

	-- function "?="   (anonymous, anonymous: BIT) return BIT;
	-- function "?/="  (anonymous, anonymous: BIT) return BIT;
	-- function "?<"   (anonymous, anonymous: BIT) return BIT;
	-- function "?<="  (anonymous, anonymous: BIT) return BIT;
	-- function "?>"   (anonymous, anonymous: BIT) return BIT;
	-- function "?>="  (anonymous, anonymous: BIT) return BIT;

	-- function MINIMUM (L, R: BIT) return BIT;
	-- function MAXIMUM (L, R: BIT) return BIT;

	-- function "??" (anonymous: BIT) return BOOLEAN;

	-- function RISING_EDGE  (signal S: BIT) return BOOLEAN;
	-- function FALLING_EDGE (signal S: BIT) return BOOLEAN;

	type CHARACTER is (
		NUL,  SOH,  STX,  ETX,  EOT,  ENQ,  ACK,  BEL,
		BS,   HT,   LF,   VT,   FF,   CR,   SO,   SI,
		DLE,  DC1,  DC2,  DC3,  DC4,  NAK,  SYN,  ETB,
		CAN,  EM,   SUB,  ESC,  FSP,  GSP,  RSP,  USP,

		' ',  '!',  '"',  '#',  '$',  '%',  '&',  ''',
		'(',  ')',  '*',  '+',  ',',  '-',  '.',  '/',
		'0',  '1',  '2',  '3',  '4',  '5',  '6',  '7',
		'8',  '9',  ':',  ';',  '<',  '=',  '>',  '?',

		'@',  'A',  'B',  'C',  'D',  'E',  'F',  'G',
		'H',  'I',  'J',  'K',  'L',  'M',  'N',  'O',
		'P',  'Q',  'R',  'S',  'T',  'U',  'V',  'W',
		'X',  'Y',  'Z',  '[',  '\',  ']',  '^',  '_',

		'`',  'a',  'b',  'c',  'd',  'e',  'f',  'g',
		'h',  'i',  'j',  'k',  'l',  'm',  'n',  'o',
		'p',  'q',  'r',  's',  't',  'u',  'v',  'w',
		'x',  'y',  'z',  '{',  '|',  '}',  '~',  DEL,

		C128, C129, C130, C131, C132, C133, C134, C135,
		C136, C137, C138, C139, C140, C141, C142, C143,
		C144, C145, C146, C147, C148, C149, C150, C151,
		C152, C153, C154, C155, C156, C157, C158, C159,

		' ',  '¡',  '¢',  '£',  '¤',  '¥',  '¦',  '§',
		'¨',  '©',  'ª',  '«',  '¬',  '­',  '®',  '¯',
		'°',  '±',  '²',  '³',  '´',  'µ',  '¶',  '·',
		'¸',  '¹',  'º',  '»',  '¼',  '½',  '¾',  '¿',

		'À',  'Á',  'Â',  'Ã',  'Ä',  'Å',  'Æ',  'Ç',
		'È',  'É',  'Ê',  'Ë',  'Ì',  'Í',  'Î',  'Ï',
		'Ð',  'Ñ',  'Ò',  'Ó',  'Ô',  'Õ',  'Ö',  '×',
		'Ø',  'Ù',  'Ú',  'Û',  'Ü',  'Ý',  'Þ',  'ß',

		'à',  'á',  'â',  'ã',  'ä',  'å',  'æ',  'ç',
		'è',  'é',  'ê',  'ë',  'ì',  'í',  'î',  'ï',
		'ð',  'ñ',  'ò',  'ó',  'ô',  'õ',  'ö',  '÷',
		'ø',  'ù',  'ú',  'û',  'ü',  'ý',  'þ',  'ÿ' );

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: CHARACTER)
	--                                      return BOOLEAN;
	-- function "/=" (anonymous, anonymous: CHARACTER)
	--                                      return BOOLEAN;
	-- function "<"  (anonymous, anonymous: CHARACTER)
	--                                      return BOOLEAN;
	-- function "<=" (anonymous, anonymous: CHARACTER)
	--                                      return BOOLEAN;
	-- function ">"  (anonymous, anonymous: CHARACTER)
	--                                      return BOOLEAN;
	-- function ">=" (anonymous, anonymous: CHARACTER)
	--                                      return BOOLEAN;

	-- function MINIMUM (L, R: CHARACTER) return CHARACTER;
	-- function MAXIMUM (L, R: CHARACTER) return CHARACTER;

	type SEVERITY_LEVEL is (NOTE, WARNING, ERROR, FAILURE);

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: SEVERITY_LEVEL)
	--                                      return BOOLEAN;
	-- function "/=" (anonymous, anonymous: SEVERITY_LEVEL)
	--                                      return BOOLEAN;
	-- function "<"  (anonymous, anonymous: SEVERITY_LEVEL)
	--                                      return BOOLEAN;
	-- function "<=" (anonymous, anonymous: SEVERITY_LEVEL)
	--                                      return BOOLEAN;
	-- function ">"  (anonymous, anonymous: SEVERITY_LEVEL)
	--                                      return BOOLEAN;
	-- function ">=" (anonymous, anonymous: SEVERITY_LEVEL)
	--                                      return BOOLEAN;

	-- function MINIMUM (L, R: SEVERITY_LEVEL) return SEVERITY_LEVEL;
	-- function MAXIMUM (L, R: SEVERITY_LEVEL) return SEVERITY_LEVEL;

	-- type universal_integer is range -2147483648 to 2147483647;

	-- The predefined operations for this type are as follows:

	-- function "="   (anonymous, anonymous: universal_integer)
	--                                       return BOOLEAN;
	-- function "/="  (anonymous, anonymous: universal_integer)
	--                                       return BOOLEAN;
	-- function "<"   (anonymous, anonymous: universal_integer)
	--                                       return BOOLEAN;
	-- function "<="  (anonymous, anonymous: universal_integer)
	--                                       return BOOLEAN;
	-- function ">"   (anonymous, anonymous: universal_integer)
	--                                       return BOOLEAN;
	-- function ">="  (anonymous, anonymous: universal_integer)
	--                                       return BOOLEAN;

	-- function "+"   (anonymous: universal_integer)
	--                                       return universal_integer;
	-- function "-"   (anonymous: universal_integer)
	--                                       return universal_integer;
	-- function "abs" (anonymous: universal_integer)
	--                                       return universal_integer;

	-- function "+"   (anonymous, anonymous: universal_integer)
	--                                       return universal_integer;
	-- function "-"   (anonymous, anonymous: universal_integer)
	--                                       return universal_integer;
	-- function "*"   (anonymous, anonymous: universal_integer)
	--                                       return universal_integer;
	-- function "/"   (anonymous, anonymous: universal_integer)
	--                                       return universal_integer;
	-- function "mod" (anonymous, anonymous: universal_integer)
	--                                       return universal_integer;
	-- function "rem" (anonymous, anonymous: universal_integer)
	--                                       return universal_integer;

	-- function MINIMUM (L, R: universal_integer)
	--                                       return universal_integer;
	-- function MAXIMUM (L, R: universal_integer)
	--                                       return universal_integer;

	-- type universal_real is range -1.0e+308 to 1.0e+308;

	-- The predefined operations for this type are as follows:

	-- function "="   (anonymous, anonymous: universal_real)
	--                                       return BOOLEAN;
	-- function "/="  (anonymous, anonymous: universal_real)
	--                                       return BOOLEAN;
	-- function "<"   (anonymous, anonymous: universal_real)
	--                                       return BOOLEAN;
	-- function "<="  (anonymous, anonymous: universal_real)
	--                                       return BOOLEAN;
	-- function ">"   (anonymous, anonymous: universal_real)
	--                                       return BOOLEAN;
	-- function ">="  (anonymous, anonymous: universal_real)
	--                                       return BOOLEAN;

	-- function "+"   (anonymous: universal_real)
	--                                       return universal_real;
	-- function "-"   (anonymous: universal_real)
	--                                       return universal_real;
	-- function "abs" (anonymous: universal_real)
	--                                       return universal_real;

	-- function "+"   (anonymous, anonymous: universal_real)
	--                                       return universal_real;
	-- function "-"   (anonymous, anonymous: universal_real)
	--                                       return universal_real;
	-- function "*"   (anonymous, anonymous: universal_real)
	--                                       return universal_real;
	-- function "/"   (anonymous, anonymous: universal_real)
	--                                       return universal_real;

	-- function "*"   (anonymous: universal_real;
	--                 anonymous: universal_integer)
	--                                       return universal_real;
	-- function "*"   (anonymous: universal_integer;
	--                 anonymous: universal_real)
	--                                       return universal_real;
	-- function "/"   (anonymous: universal_real;
	--                 anonymous: universal_integer)
	--                                       return universal_real;

	-- function MINIMUM (L, R: universal_real) return universal_real;
	-- function MAXIMUM (L, R: universal_real) return universal_real;

	-- Predefined numeric types:

	type INTEGER is range  -2147483648 to 2147483647;

	-- The predefined operations for this type are as follows:

	-- function "**"  (anonymous: universal_integer;
	--                 anonymous: INTEGER) return universal_integer;
	-- function "**"  (anonymous: universal_real;
	--                 anonymous: INTEGER) return universal_real;

	-- function "="   (anonymous, anonymous: INTEGER) return BOOLEAN;
	-- function "/="  (anonymous, anonymous: INTEGER) return BOOLEAN;
	-- function "<"   (anonymous, anonymous: INTEGER) return BOOLEAN;
	-- function "<="  (anonymous, anonymous: INTEGER) return BOOLEAN;
	-- function ">"   (anonymous, anonymous: INTEGER) return BOOLEAN;
	-- function ">="  (anonymous, anonymous: INTEGER) return BOOLEAN;

	-- function "+"   (anonymous: INTEGER) return INTEGER;
	-- function "-"   (anonymous: INTEGER) return INTEGER;
	-- function "abs" (anonymous: INTEGER) return INTEGER;

	-- function "+"   (anonymous, anonymous: INTEGER) return INTEGER;
	-- function "-"   (anonymous, anonymous: INTEGER) return INTEGER;
	-- function "*"   (anonymous, anonymous: INTEGER) return INTEGER;
	-- function "/"   (anonymous, anonymous: INTEGER) return INTEGER;
	-- function "mod" (anonymous, anonymous: INTEGER) return INTEGER;
	-- function "rem" (anonymous, anonymous: INTEGER) return INTEGER;

	-- function "**"  (anonymous: INTEGER; anonymous: INTEGER)
	--                                                return INTEGER;

	-- function MINIMUM (L, R: INTEGER) return INTEGER;
	-- function MAXIMUM (L, R: INTEGER) return INTEGER;

	type REAL is range -1.0e+308 to 1.0e+308;

	-- The predefined operations for this type are as follows:

	-- function "="   (anonymous, anonymous: REAL) return BOOLEAN;
	-- function "/="  (anonymous, anonymous: REAL) return BOOLEAN;
	-- function "<"   (anonymous, anonymous: REAL) return BOOLEAN;
	-- function "<="  (anonymous, anonymous: REAL) return BOOLEAN;
	-- function ">"   (anonymous, anonymous: REAL) return BOOLEAN;
	-- function ">="  (anonymous, anonymous: REAL) return BOOLEAN;

	-- function "+"   (anonymous: REAL) return REAL;
	-- function "-"   (anonymous: REAL) return REAL;
	-- function "abs" (anonymous: REAL) return REAL;

	-- function "+"   (anonymous, anonymous: REAL) return REAL;
	-- function "-"   (anonymous, anonymous: REAL) return REAL;
	-- function "*"   (anonymous, anonymous: REAL) return REAL;
	-- function "/"   (anonymous, anonymous: REAL) return REAL;

	-- function "**"  (anonymous: REAL; anonymous: INTEGER) return REAL;

	-- function MINIMUM (L, R: REAL) return REAL;
	-- function MAXIMUM (L, R: REAL) return REAL;

	-- Predefined type TIME:

	type TIME is range -9223372036854775807 to 9223372036854775807
		units
			fs;            -- femtosecond
			ps = 1000 fs;  -- picosecond
			ns = 1000 ps;  -- nanosecond
			us = 1000 ns;  -- microsecond
			ms = 1000 us;  -- millisecond
			sec = 1000 ms; -- second
			min = 60 sec;  -- minute
			hr = 60 min;   -- hour
		end units;

	-- The predefined operations for this type are as follows:

	-- function "="   (anonymous, anonymous: TIME) return BOOLEAN;
	-- function "/="  (anonymous, anonymous: TIME) return BOOLEAN;
	-- function "<"   (anonymous, anonymous: TIME) return BOOLEAN;
	-- function "<="  (anonymous, anonymous: TIME) return BOOLEAN;
	-- function ">"   (anonymous, anonymous: TIME) return BOOLEAN;
	-- function ">="  (anonymous, anonymous: TIME) return BOOLEAN;

	-- function "+"   (anonymous: TIME) return TIME;
	-- function "-    (anonymous: TIME) return TIME;
	-- function "abs" (anonymous: TIME) return TIME;

	-- function "+"   (anonymous, anonymous: TIME) return TIME;
	-- function "-"   (anonymous, anonymous: TIME) return TIME;

	-- function "*"   (anonymous: TIME;    anonymous: INTEGER)
	--                                                return TIME;
	-- function "*"   (anonymous: TIME;    anonymous: REAL)
	--                                                return TIME;
	-- function "*"   (anonymous: INTEGER; anonymous: TIME)
	--                                                return TIME;
	-- function "*"   (anonymous: REAL;    anonymous: TIME)
	--                                                return TIME;
	-- function "/"   (anonymous: TIME;    anonymous: INTEGER)
	--                                                return TIME;
	-- function "/"   (anonymous: TIME;    anonymous: REAL)
	--                                                return TIME;

	-- function "/"   (anonymous, anonymous: TIME)
	--                                       return universal_integer;

	-- function "mod" (anonymous, anonymous: TIME) return TIME;
	-- function "rem" (anonymous, anonymous: TIME) return TIME;

	-- function MINIMUM (L, R: TIME) return TIME;
	-- function MAXIMUM (L, R: TIME) return TIME;

	subtype DELAY_LENGTH is TIME range 0 fs to TIME'HIGH;

	-- A function that returns the current simulation time, Tc,
	-- (see 12.6.4):

	impure function NOW return DELAY_LENGTH;

	-- Predefined numeric subtypes:

	subtype NATURAL is INTEGER range 0 to INTEGER'HIGH;
	subtype POSITIVE is INTEGER range 1 to INTEGER'HIGH;

	-- Predefined array types:

	type STRING is array (POSITIVE range <>) of CHARACTER;

	-- The predefined operations for these types are as follows:

	-- function "="  (anonymous, anonymous: STRING) return BOOLEAN;
	-- function "/=" (anonymous, anonymous: STRING) return BOOLEAN;
	-- function "<"  (anonymous, anonymous: STRING) return BOOLEAN;
	-- function "<=" (anonymous, anonymous: STRING) return BOOLEAN;
	-- function ">"  (anonymous, anonymous: STRING) return BOOLEAN;
	-- function ">=" (anonymous, anonymous: STRING) return BOOLEAN;

	-- function "&"  (anonymous: STRING;    anonymous: STRING)
	--                                      return STRING;
	-- function "&"  (anonymous: STRING;    anonymous: CHARACTER)
	--                                      return STRING;
	-- function "&"  (anonymous: CHARACTER; anonymous: STRING)
	--                                      return STRING;
	-- function "&"  (anonymous: CHARACTER; anonymous: CHARACTER)
	--                                      return STRING;

	-- function MINIMUM (L, R: STRING) return STRING;
	-- function MAXIMUM (L, R: STRING) return STRING;

	-- function MINIMUM (L: STRING) return CHARACTER;
	-- function MAXIMUM (L: STRING) return CHARACTER;

	type BOOLEAN_VECTOR is array (NATURAL range <>) of BOOLEAN;

	-- The predefined operations for this type are as follows:

	-- function "and"   (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "or"    (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "nand"  (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "nor"   (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "xor"   (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "xnor"  (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;

	-- function "not"   (anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;

	-- function "and"   (anonymous: BOOLEAN_VECTOR; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;
	-- function "and"   (anonymous: BOOLEAN; anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "or"    (anonymous: BOOLEAN_VECTOR; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;
	-- function "or"    (anonymous: BOOLEAN; anonymous : BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "nand"  (anonymous: BOOLEAN_VECTOR; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;
	-- function "nand" (anonymous: BOOLEAN; anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "nor"  (anonymous: BOOLEAN_VECTOR; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;
	-- function "nor"  (anonymous: BOOLEAN; anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "xor"  (anonymous: BOOLEAN_VECTOR; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;
	-- function "xor"  (anonymous: BOOLEAN; anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "xnor" (anonymous: BOOLEAN_VECTOR; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;
	-- function "xnor" (anonymous: BOOLEAN; anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;

	-- function "and"  (anonymous: BOOLEAN_VECTOR) return BOOLEAN;
	-- function "or"   (anonymous: BOOLEAN_VECTOR) return BOOLEAN;
	-- function "nand" (anonymous: BOOLEAN_VECTOR) return BOOLEAN;
	-- function "nor"  (anonymous: BOOLEAN_VECTOR) return BOOLEAN;
	-- function "xor"  (anonymous: BOOLEAN_VECTOR) return BOOLEAN;
	-- function "xnor" (anonymous: BOOLEAN_VECTOR) return BOOLEAN;

	-- function "sll"  (anonymous: BOOLEAN_VECTOR; anonymous: INTEGER)
	--                                         return BOOLEAN_VECTOR;
	-- function "srl"  (anonymous: BOOLEAN_VECTOR; anonymous: INTEGER)
	--                                         return BOOLEAN_VECTOR;
	-- function "sla"  (anonymous: BOOLEAN_VECTOR; anonymous: INTEGER)
	--                                         return BOOLEAN_VECTOR;
	-- function "sra"  (anonymous: BOOLEAN_VECTOR; anonymous: INTEGER)
	--                                         return BOOLEAN_VECTOR;
	-- function "rol"  (anonymous: BOOLEAN_VECTOR; anonymous: INTEGER)
	--                                         return BOOLEAN_VECTOR;
	-- function "ror"  (anonymous: BOOLEAN_VECTOR; anonymous: INTEGER)
	--                                         return BOOLEAN_VECTOR;

	-- function "="    (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN;
	-- function "/="   (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN;
	-- function "<"    (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN;
	-- function "<="   (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN;
	-- function ">"    (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN;
	-- function ">="   (anonymous, anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN;

	-- function "&"    (anonymous: BOOLEAN_VECTOR;
	--                  anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "&"    (anonymous: BOOLEAN_VECTOR; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;
	-- function "&"    (anonymous: BOOLEAN; anonymous: BOOLEAN_VECTOR)
	--                                         return BOOLEAN_VECTOR;
	-- function "&"    (anonymous: BOOLEAN; anonymous: BOOLEAN)
	--                                         return BOOLEAN_VECTOR;

	-- function MINIMUM (L, R: BOOLEAN_VECTOR) return BOOLEAN_VECTOR;
	-- function MAXIMUM (L, R: BOOLEAN_VECTOR) return BOOLEAN_VECTOR;

	-- function MINIMUM (L: BOOLEAN_VECTOR) return BOOLEAN;
	-- function MAXIMUM (L: BOOLEAN_VECTOR) return BOOLEAN;

	type BIT_VECTOR is array (NATURAL range <>) of BIT;

	-- The predefined operations for this type are as follows:

	-- function "and"  (anonymous, anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "or"   (anonymous, anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "nand" (anonymous, anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "nor"  (anonymous, anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "xor"  (anonymous, anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "xnor" (anonymous, anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;

	-- function "not"  (anonymous: BIT_VECTOR) return BIT_VECTOR;

	-- function "and"  (anonymous: BIT_VECTOR; anonymous : BIT)
	--                                        return BIT_VECTOR;
	-- function "and"  (anonymous: BIT; anonymous : BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "or"   (anonymous: BIT_VECTOR; anonymous : BIT)
	--                                        return BIT_VECTOR;
	-- function "or"   (anonymous: BIT; anonymous : BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "nand" (anonymous: BIT_VECTOR; anonymous : BIT)
	--                                        return BIT_VECTOR;
	-- function "nand" (anonymous: BIT; anonymous : BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "nor"  (anonymous: BIT_VECTOR; anonymous : BIT)
	--                                        return BIT_VECTOR;
	-- function "nor"  (anonymous: BIT; anonymous : BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "xor"  (anonymous: BIT_VECTOR; anonymous : BIT)
	--                                        return BIT_VECTOR;
	-- function "xor"  (anonymous: BIT; anonymous : BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "xnor" (anonymous: BIT_VECTOR; anonymous : BIT)
	--                                        return BIT_VECTOR;
	-- function "xnor" (anonymous: BIT; anonymous : BIT_VECTOR)
	--                                        return BIT_VECTOR;

	-- function "and"  (anonymous: BIT_VECTOR) return BIT;
	-- function "or"   (anonymous: BIT_VECTOR) return BIT;
	-- function "nand" (anonymous: BIT_VECTOR) return BIT;
	-- function "nor"  (anonymous: BIT_VECTOR) return BIT;
	-- function "xor"  (anonymous: BIT_VECTOR) return BIT;
	-- function "xnor" (anonymous: BIT_VECTOR) return BIT;

	-- function "sll"  (anonymous: BIT_VECTOR; anonymous: INTEGER)
	--                                        return BIT_VECTOR;
	-- function "srl"  (anonymous: BIT_VECTOR; anonymous: INTEGER)
	--                                        return BIT_VECTOR;
	-- function "sla"  (anonymous: BIT_VECTOR; anonymous: INTEGER)
	--                                        return BIT_VECTOR;
	-- function "sra"  (anonymous: BIT_VECTOR; anonymous: INTEGER)
	--                                        return BIT_VECTOR;
	-- function "rol"  (anonymous: BIT_VECTOR; anonymous: INTEGER)
	--                                        return BIT_VECTOR;
	-- function "ror"  (anonymous: BIT_VECTOR; anonymous: INTEGER)
	--                                        return BIT_VECTOR;

	-- function "="    (anonymous, anonymous: BIT_VECTOR)
	--                                        return BOOLEAN;
	-- function "/="   (anonymous, anonymous: BIT_VECTOR)
	--                                        return BOOLEAN;
	-- function "<"    (anonymous, anonymous: BIT_VECTOR)
	--                                        return BOOLEAN;
	-- function "<="   (anonymous, anonymous: BIT_VECTOR)
	--                                        return BOOLEAN;
	-- function ">"    (anonymous, anonymous: BIT_VECTOR)
	--                                        return BOOLEAN;
	-- function ">="   (anonymous, anonymous: BIT_VECTOR)
	--                                        return BOOLEAN;

	-- function "?="   (anonymous, anonymous: BIT_VECTOR) return BIT;
	-- function "?/="  (anonymous, anonymous: BIT_VECTOR) return BIT;

	-- function "&"    (anonymous: BIT_VECTOR; anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "&"    (anonymous: BIT_VECTOR; anonymous: BIT)
	--                                        return BIT_VECTOR;
	-- function "&"    (anonymous: BIT; anonymous: BIT_VECTOR)
	--                                        return BIT_VECTOR;
	-- function "&"    (anonymous: BIT; anonymous: BIT)
	--                                        return BIT_VECTOR;

	-- function MINIMUM (L, R: BIT_VECTOR) return BIT_VECTOR;
	-- function MAXIMUM (L, R: BIT_VECTOR) return BIT_VECTOR;

	-- function MINIMUM (L: BIT_VECTOR) return BIT;
	-- function MAXIMUM (L: BIT_VECTOR) return BIT;

	-- function TO_STRING (VALUE: BIT_VECTOR) return STRING;

	-- alias    TO_BSTRING       is TO_STRING
	--                              [BIT_VECTOR return STRING];
	-- alias    TO_BINARY_STRING is TO_STRING
	--                              [BIT_VECTOR return STRING];
	-- function TO_OSTRING (VALUE: BIT_VECTOR) return STRING;
	-- alias    TO_OCTAL_STRING  is TO_OSTRING
	--                              [BIT_VECTOR return STRING];
	-- function TO_HSTRING (VALUE: BIT_VECTOR) return STRING;
	-- alias    TO_HEX_STRING    is TO_HSTRING
	--                              [BIT_VECTOR return STRING];

	type INTEGER_VECTOR is array (NATURAL range <>) of INTEGER;

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: INTEGER_VECTOR)
	--                                      return BOOLEAN;
	-- function "/=" (anonymous, anonymous: INTEGER_VECTOR)
	--                                      return BOOLEAN;
	-- function "<"  (anonymous, anonymous: INTEGER_VECTOR)
	--                                      return BOOLEAN;
	-- function "<=" (anonymous, anonymous: INTEGER_VECTOR)
	--                                      return BOOLEAN;
	-- function ">"  (anonymous, anonymous: INTEGER_VECTOR)
	--                                      return BOOLEAN;
	-- function ">=" (anonymous, anonymous: INTEGER_VECTOR)
	--                                      return BOOLEAN;

	-- function "&"  (anonymous: INTEGER_VECTOR;
	--                anonymous: INTEGER_VECTOR) return INTEGER_VECTOR;
	-- function "&"  (anonymous: INTEGER_VECTOR;
	--                anonymous: INTEGER)        return INTEGER_VECTOR;
	-- function "&"  (anonymous: INTEGER;
	--                anonymous: INTEGER_VECTOR) return INTEGER_VECTOR;
	-- function "&"  (anonymous: INTEGER;
	--                anonymous: INTEGER)        return INTEGER_VECTOR;

	-- function MINIMUM (L, R: INTEGER_VECTOR) return INTEGER_VECTOR;
	-- function MAXIMUM (L, R: INTEGER_VECTOR) return INTEGER_VECTOR;

	-- function MINIMUM (L: INTEGER_VECTOR) return INTEGER;
	-- function MAXIMUM (L: INTEGER_VECTOR) return INTEGER;

	type REAL_VECTOR is array (NATURAL range <>) of REAL;

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: REAL_VECTOR)
	--                                      return BOOLEAN;
	-- function "/=" (anonymous, anonymous: REAL_VECTOR)
	--                                      return BOOLEAN;

	-- function "&"  (anonymous: REAL_VECTOR; anonymous: REAL_VECTOR)
	--                                      return REAL_VECTOR;
	-- function "&"  (anonymous: REAL_VECTOR; anonymous: REAL)
	--                                      return REAL_VECTOR;
	-- function "&"  (anonymous: REAL; anonymous: REAL_VECTOR)
	--                                      return REAL_VECTOR;
	-- function "&"  (anonymous: REAL; anonymous: REAL)
	--                                      return REAL_VECTOR;

	-- function MINIMUM (L: REAL_VECTOR) return REAL;
	-- function MAXIMUM (L: REAL_VECTOR) return REAL;

	type TIME_VECTOR is array (NATURAL range <>) of TIME;

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: TIME_VECTOR)
	--                                      return BOOLEAN;
	-- function "/=" (anonymous, anonymous: TIME_VECTOR)
	--                                      return BOOLEAN;

	-- function "&"  (anonymous: TIME_VECTOR; anonymous: TIME_VECTOR)
	--                                      return TIME_VECTOR;
	-- function "&"  (anonymous: TIME_VECTOR; anonymous: TIME)
	--                                      return TIME_VECTOR;
	-- function "&"  (anonymous: TIME; anonymous: TIME_VECTOR)
	--                                      return TIME_VECTOR;
	-- function "&"  (anonymous: TIME; anonymous: TIME)
	--                                      return TIME_VECTOR;

	-- function MINIMUM (L: TIME_VECTOR) return TIME;
	-- function MAXIMUM (L: TIME_VECTOR) return TIME;

	-- The predefined types for opening files:

	type FILE_OPEN_KIND is (
		READ_MODE,          -- Resulting access mode is read-only.
		WRITE_MODE,         -- Resulting access mode is write-only.
		APPEND_MODE);       -- Resulting access mode is write-only;
		                    -- information is appended to the end
		                    -- of the existing file.

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: FILE_OPEN_KIND)
	--                                      return BOOLEAN;
	-- function "/=" (anonymous, anonymous: FILE_OPEN_KIND)
	--                                      return BOOLEAN;
	-- function "<"  (anonymous, anonymous: FILE_OPEN_KIND)
	--                                      return BOOLEAN;
	-- function "<=" (anonymous, anonymous: FILE_OPEN_KIND)
	--                                      return BOOLEAN;
	-- function ">"  (anonymous, anonymous: FILE_OPEN_KIND)
	--                                      return BOOLEAN;
	-- function ">=" (anonymous, anonymous: FILE_OPEN_KIND)
	--                                      return BOOLEAN;

	-- function MINIMUM (L, R: FILE_OPEN_KIND) return FILE_OPEN_KIND;
	-- function MAXIMUM (L, R: FILE_OPEN_KIND) return FILE_OPEN_KIND;

	type FILE_OPEN_STATUS is (
		OPEN_OK,              -- File open was successful.
		STATUS_ERROR,         -- File object was already open.
		NAME_ERROR,           -- External file not found
		                      -- or inaccessible.
		MODE_ERROR);          -- Could not open file with requested
		                      -- access mode.

	-- The predefined operations for this type are as follows:

	-- function "="  (anonymous, anonymous: FILE_OPEN_STATUS)
	--                                      return BOOLEAN;
	-- function "/=" (anonymous, anonymous: FILE_OPEN_STATUS)
	--                                      return BOOLEAN;
	-- function "<"  (anonymous, anonymous: FILE_OPEN_STATUS)
	--                                      return BOOLEAN;
	-- function "<=" (anonymous, anonymous: FILE_OPEN_STATUS)
	--                                      return BOOLEAN;
	-- function ">"  (anonymous, anonymous: FILE_OPEN_STATUS)
	--                                      return BOOLEAN;
	-- function ">=" (anonymous, anonymous: FILE_OPEN_STATUS)
	--                                      return BOOLEAN;

	-- function MINIMUM (L, R: FILE_OPEN_STATUS)
	--                                      return FILE_OPEN_STATUS;
	-- function MAXIMUM (L, R: FILE_OPEN_STATUS)
	--                                      return FILE_OPEN_STATUS;

	-- The 'FOREIGN attribute:
	attribute FOREIGN: STRING;

	-- Predefined TO_STRING operations on scalar types

	-- function TO_STRING (VALUE: BOOLEAN)           return STRING;
	-- function TO_STRING (VALUE: BIT)               return STRING;
	-- function TO_STRING (VALUE: CHARACTER)         return STRING;
	-- function TO_STRING (VALUE: SEVERITY_LEVEL)    return STRING;
	-- function TO_STRING (VALUE: universal_integer) return STRING;
	-- function TO_STRING (VALUE: universal_real)    return STRING;
	-- function TO_STRING (VALUE: INTEGER)           return STRING;
	-- function TO_STRING (VALUE: REAL)              return STRING;
	-- function TO_STRING (VALUE: TIME)              return STRING;
	-- function TO_STRING (VALUE: FILE_OPEN_KIND)    return STRING;
	-- function TO_STRING (VALUE: FILE_OPEN_STATUS)  return STRING;

	-- Predefined overloaded TO_STRING operations

	-- function TO_STRING (VALUE: REAL; DIGITS: NATURAL)
	--                                               return STRING;
	-- function TO_STRING (VALUE: REAL; FORMAT: STRING)
	--                                               return STRING;
	-- function TO_STRING (VALUE: TIME; UNIT: TIME)  return STRING;

end STANDARD;
