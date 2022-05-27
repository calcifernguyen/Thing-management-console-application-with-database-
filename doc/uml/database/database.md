classDiagram
direction BT
class BOOK_ON_TAPE {
   integer SERIAL
   character varying(255) AUTHOR
   character varying(255) SUMMARY
}
class FURNITURE {
   integer SERIAL
   character varying(255) MATERIAL
   character varying(255) COLOR
}
class THING {
   character varying(50) TYPE
   character varying(255) NAME
   numeric(19,2) PRICE
   integer TOTAL
   integer AVAILABLE
   integer SERIAL
}
class VIDEO {
   integer SERIAL
   character varying(15) LENGTH
   character varying(255) SUMMARY
}

BOOK_ON_TAPE  -->  THING : SERIAL
FURNITURE  -->  THING : SERIAL
VIDEO  -->  THING : SERIAL
