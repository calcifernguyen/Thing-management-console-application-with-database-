classDiagram
direction BT
class Application {
  + main(String[]) void
}
class BookOnTape {
  + String description
  + String summary
  + String author
}
class BookOnTapeDAO {
  + insert(BookOnTape) int
  + closeConnection() void
  + selectById(long) BookOnTape
  + selectAll() List~BookOnTape~
  + update(BookOnTape) int
}
class ConnectionUtils {
  + main(String[]) void
  + Connection myConnection
}
class Furniture {
  + String description
  + String material
  + String color
}
class FurnitureDAO {
  + selectById(long) Furniture
  + update(Furniture) int
  + closeConnection() void
  + selectAll() List~Furniture~
  + insert(Furniture) int
}
class H2ConnUtils {
  + getConnection(String, String, String) Connection
  + Connection connection
}
class IDAO~T~ {
  + update(T) int
  + insert(T) int
  + closeConnection() void
  + selectAll() List~T~
  + selectById(long) T
}
class InputForm {
  + case3FollowThingType() void
  + input() Thing
  + case4FollowThingType() void
}
class Lookup {
  + showAllFurniture() void
  + addThing() void
  + addVideo() void
  + addBookOnTape() void
  + start() void
  + showAllVideo() void
  + printMainMenu() void
  + searchThingBySerial() void
  + showAllBookOnTape() void
  + addFurniture() void
}
class NegativeException
class Thing {
  + String name
  + String description
  + int total
  + long serial
  + BigDecimal price
  + int available
}
class ThingDAO {
  + insert(Thing) int
  + update(Thing) int
  + selectAll() List~Thing~
  + closeConnection() void
  + selectById(long) Thing
}
class ThingType {
<<enumeration>>
  + valueOf(String) ThingType
  + values() ThingType[]
}
class ValidateInput {
  + getIntInput(Scanner, boolean) int
  + getLongInput(Scanner, boolean) long
  + getBigDecimalInput(Scanner, boolean) BigDecimal
}
class Video {
  + String length
  + String description
  + String summary
}
class VideoDAO {
  + closeConnection() void
  + update(Video) int
  + selectById(long) Video
  + insert(Video) int
  + selectAll() List~Video~
}

BookOnTape  -->  Thing 
BookOnTapeDAO  -->  IDAO~T~ 
Furniture  -->  Thing 
FurnitureDAO  -->  IDAO~T~ 
ThingDAO  -->  IDAO~T~ 
Video  -->  Thing 
VideoDAO  -->  IDAO~T~ 
