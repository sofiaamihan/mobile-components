Create - POST
Read - GET
Update - PUT
Delete - DELETE

# Authentication Use Cases
1. Sign Up - POST
2. Login - POST
3. Update Profile or Password - PUT
4. Delete Profile - DELETE
``` Kotlin
@Dao
interface UserDao {
    @Insert
    suspend fun signUp(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): User?

    @Update
    suspend fun updateProfile(user: User)

    @Delete
    suspend fun deleteProfile(user: User)
}
```
- For `GET` all then you don't actually have to suspend the functions because they don't require inputs

# Common Use Cases
1. Get all items
2. Get item based on id
3. Get item based on one attribute
4. Get item based on id and one attribute
5. Get item if certain attribute contains certain content (Searching, Filtering)
6. Get item from ascending or descending order
7. Add a new item
8. Update an item based on its id
9. Update multiple items based on an attribute
10. Delete an item based on its id
11. Delete multiple items based on a certain attribute
12. Checking if an item already exists  
13. Pagination - Returning only a subset for large datasets - GET `/items?page=2&limit=10`
``` Kotlin
@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Item>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): Item?

    @Query("SELECT * FROM items WHERE attribute = :value")
    suspend fun getItemByAttribute(value: String): List<Item>

    @Query("SELECT * FROM items WHERE id = :id AND attribute = :value")
    suspend fun getItemByIdAndAttribute(id: Int, value: String): Item?

    @Query("SELECT * FROM items WHERE attribute LIKE :searchTerm")
    suspend fun searchItems(searchTerm: String): List<Item>

    @Query("SELECT * FROM items ORDER BY attribute ASC")
    suspend fun getItemsAsc(): List<Item>

    @Query("SELECT * FROM items ORDER BY attribute DESC")
    suspend fun getItemsDesc(): List<Item>

    @Insert
    suspend fun addItem(item: Item)

    @Update
    suspend fun updateItem(item: Item)

    @Query("UPDATE items SET price = :newPrice WHERE category = :category")
    suspend fun updateMultipleItems(newPrice: Double, category: String)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteItemById(id: Int)

    @Query("DELETE FROM items WHERE category = :category")
    suspend fun deleteItemsByCategory(category: String)

    @Query("SELECT COUNT(*) FROM items WHERE name = :name")
    suspend fun checkIfItemExists(name: String): Int

    @Query("SELECT * FROM items LIMIT :limit OFFSET :offset")
    suspend fun getItemsWithPagination(limit: Int, offset: Int): List<Item>
}
```

# Functionality
## API Endpoints
Specific URLs or URIs (Uniform Resource Identifier) within an API that servers as access points to interact with various functionalities or resources the API provides
- Act as the “doors” that the client application can use to send HTTP requests to the API server
## API Requests
The action taken by a client application to initiate communication with the API server.
- Involves sending a HTTP request to a specific API endpoint to perform a specific operation
- `GET` - Read/Retrieve Data
- `POST` - Create New Data
- `PUT` - Update Existing Data
- `DELETE` - Delete Data
## API Response
Data send back by the API server after processing a request
- **Formats**: JSON, XML, HTML, Plaintext

# Status Codes
`200` - OK
`201` - Created
`202` - Accepted
`204` - No Content
`400` - Bad Request
`401` - Unauthorised
`403` - Forbidden
`404` - Not Found
`408` - Request Timeout
`500` - Internal Server error

# SQLite Syntax
https://www.sqlitetutorial.net/
## Simple Query
`SELECT`
## Sorting Rows
`ORDER BY`
## Filtering
`SELECT DISTINCT`
`WHERE`
`OR` 
`AND`
`Limit`
`Between`
`In`
`Like` – query data based on pattern matching using wildcard characters: percent sign (%) and underscore
`ISNULL`